# Air Gauge — Development Plan

## 1. Overview

Air Gauge is a QA visualization module for rifle barrel manufacturing. During QA, an air
gauge is inserted the length of a barrel and captures the internal diameter at many points
along its length. This tool lets an operator look up a work order or barrel serial number
and see the captured measurements as a line graph, navigating a
**work order → serial number → inspection run → samples** hierarchy.

This is an additional module under the workbench application - called "Air Gauge".  It is a
**read-only visualization layer**: the `inspection` / `inspection_sample` tables are
populated by separate gauge-capture software/hardware. This project never writes to them.


## 2. Stack

- **Backend:** Java, Spring Boot (Spring MVC + Spring Data JPA)
- **View layer:** Thymeleaf (server-rendered HTML, no SPA build step)
- **Database:** MySQL, database name `operations`
- **Charting:** Apache ECharts, loaded via CDN, fed by a small JSON REST endpoint
- **Build:** Maven (or Gradle — pick one; examples below assume Maven)

## 3. Data model (existing, read-only)

Confirmed schema, database `operations`:

```sql
-- operations.inspection
id              int(11)       PK, auto_increment
inspectiontype  varchar(10)
woid            varchar(12)   -- work order id
comment         varchar(255)
created         datetime
custom1         varchar(45)   -- meaning TBD (see Open Questions)
custom2         varchar(45)   -- meaning TBD (see Open Questions)
serialNumber    varchar(5)

-- operations.inspection_sample
id              int(11)       PK, auto_increment
inspection_id   int(11)       FK -> inspection.id
zposvalue       decimal(8,4)  -- position along barrel length
xvalue          decimal(8,4)  -- measured diameter at that position
```

Notes / assumptions to confirm with whoever owns the capture software:

- No explicit FK constraint is assumed to exist in the DB; the app will treat
  `inspection_sample.inspection_id` as a logical reference to `inspection.id`.
- No nominal diameter / tolerance columns exist. If `custom1`/`custom2` hold spec values
  (e.g. nominal diameter, ± tolerance), confirm this so the chart can render a tolerance
  band. Until confirmed, the chart plots raw measurements only.
- A single `serialNumber` + `woid` pair can have more than one `inspection` row
  (re-inspection / multiple passes). The hierarchy accounts for this — see below.
- `serialNumber` is only 5 chars — assume it's unique *within* a work order, not globally.
  Searching by serial number alone may return matches across multiple work orders.
- This module will be available as a menu option under "Applications" labeled "Air Gauge"
- Leverage existing project structure  

## 4. Hierarchy model

```
Work Order (woid)
  └─ Serial Number (serialNumber)
       └─ Inspection run (inspection.id, created, inspectiontype)
            └─ Samples (zposvalue, xvalue) → line graph
```

Derived, not a separate table: work order and serial number are just grouping keys over
`inspection` rows. The app computes the tree via `GROUP BY woid, serialNumber` and lists
individual `inspection` rows as leaves under each serial number.

## 5. Application structure

Align with existing project structure

## 6. Entities & repositories

```java
@Entity
@Table(name = "inspection")
public class Inspection {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String inspectiontype;
    private String woid;
    private String comment;
    private LocalDateTime created;
    private String custom1;
    private String custom2;
    private String serialNumber;
}

@Entity
@Table(name = "inspection_sample")
public class InspectionSample {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "inspection_id")
    private Integer inspectionId;
    private BigDecimal zposvalue;
    private BigDecimal xvalue;
}
```

```java
public interface InspectionRepository extends JpaRepository<Inspection, Integer> {
    List<Inspection> findByWoidOrderByCreatedDesc(String woid);
    List<Inspection> findBySerialNumberOrderByCreatedDesc(String serialNumber);

    @Query("select distinct i.woid, i.serialNumber from Inspection i where i.woid = :woid")
    List<Object[]> findSerialNumbersByWoid(String woid);
}

public interface InspectionSampleRepository extends JpaRepository<InspectionSample, Integer> {
    List<InspectionSample> findByInspectionIdOrderByZposvalueAsc(Integer inspectionId);
}
```

`application.yml` points at `jdbc:mysql://<host>:3306/operations`, read-only credentials
if available (a MySQL user with `SELECT`-only grants on `operations.*` is recommended
since this app never writes).

## 7. Pages & flows

### 7.1 Landing page (`GET /`)
- One prominent search box + a toggle: **Work Order** / **Serial Number**.
- Enter-to-submit (barcode scanners send input + Enter — no separate "scan" mode needed).
- Optional: "recent searches" list (session-based, no persistence needed for v1).

### 7.2 Search results (`GET /search?type={wo|serial}&value={q}`)
- `type=wo`: look up all `serialNumber`s for that `woid`, render as an expandable tree
  (Work Order → Serial Numbers → Inspection runs, with `created` + `inspectiontype` shown
  per run). Clicking a run navigates to the detail page.
- `type=serial`: look up all `inspection` rows for that `serialNumber` (may span multiple
  work orders — group by `woid` in the results if so).
- No matches → friendly empty state, not an error page.

### 7.3 Inspection detail (`GET /inspections/{id}`)
- Metadata panel: work order, serial number, inspection type, created date/time, comment.
- Line chart: x = `zposvalue`, y = `xvalue`, fetched via `GET /api/inspections/{id}/samples`.
- Breadcrumb back to the work order / serial number tree.
- If `custom1`/`custom2` turn out to be spec/tolerance values (see Open Questions), render
  a shaded tolerance band and a computed pass/fail badge.

### 7.4 REST endpoint
```
GET /api/inspections/{id}/samples
  -> [{ "zposvalue": 0.0000, "xvalue": 0.3005 }, ...]  ordered by zposvalue
```
Kept separate from the page so the same data can later feed CSV export or multi-inspection
overlay comparisons without re-plumbing.

## 8. Non-functional considerations

- **Read-only DB access.** Use a MySQL account scoped to `SELECT` only if your DBA can
  provision one.
- **Sample density.** A full-length barrel scan may have hundreds to low-thousands of
  samples. Chart.js handles that fine; if a barrel ever has tens of thousands, consider
  downsampling before sending JSON (not needed for v1 unless observed in practice).
- **No auth in v1.** Assumed to run on an internal shop-floor LAN. Note this explicitly as
  a decision, not an oversight — revisit if this needs to be internet-facing.
- **Indexes.** Confirm indexes exist (or request them) on `inspection.woid`,
  `inspection.serialNumber`, and `inspection_sample.inspection_id` — searches and chart
  loads depend on these.

## 9. Open questions (confirm before/while building)

1. What do `custom1` / `custom2` actually hold? (nominal diameter + tolerance? something
   else? unused?) This determines whether tolerance-band rendering is possible.
   - custom1 and custom2 can contain any values
2. What are the valid values of `inspectiontype`? (affects labeling / filtering in the UI)
   - need to filter on 'AG'
3. Is `serialNumber` guaranteed unique within a `woid`, or can duplicates occur?
   - duplicates can occur 
4. Does anything else write to these two tables besides the gauge-capture system — i.e.
   is "read-only" a safe assumption for this app's DB user?
   - yes
5. Any existing internal auth/SSO this should sit behind eventually, or truly none for v1?
   - is protected againsted credentials stored in the database.

## 10. Build phases (for an AI coding agent to work through in order)

1. **Project scaffold** — Spring Boot app (Web, Thymeleaf, Data JPA, MySQL driver, Validation),
   `application.yml` with datasource config, entities + repositories wired to real schema,
   one smoke-test page confirming DB connectivity.
2. **Landing + search** — search form, `SearchController`, work-order and serial-number
   lookup queries, results page rendering the hierarchy (tree or nested list).
3. **Inspection detail + chart** — detail page, metadata panel, `InspectionApiController`
   JSON endpoint, Chart.js line graph wired to it.
4. **Polish** — empty/error states, breadcrumbs, loading states for large sample sets,
   basic responsive styling.
5. **Stretch (only after confirming Open Questions #1)** — tolerance band + pass/fail badge.
6. **Stretch** — CSV export of samples; overlay comparison of multiple inspections on one
   chart (e.g. compare re-inspections of the same barrel).
7. **Testing** — repository slice tests against a real/local MySQL or Testcontainers MySQL
   instance seeded with representative rows; MockMvc tests for controllers.
8. **Deployment** — package as an executable jar; decide target (systemd service + reverse
   proxy, or a container) once environment is known.

## 11. Explicit non-goals (v1)

- No editing/creating/deleting inspection or sample data — this app never writes.
- No app-launching or equipment-control features — pure data visualization.
- No multi-tenant/company support — single MySQL `operations` database.
