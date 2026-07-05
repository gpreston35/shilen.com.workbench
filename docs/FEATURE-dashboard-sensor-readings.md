# FEATURE: dashboard sensor readings

## Goal
Add a visually focused right-side dashboard widget that shows current readings for each active sensor.

## Scope (phase 1)
- Show one vertical card list on `home` with active sensors only.
- Display `sensor name`, `last_read_value`, `last_read_status` (`ONLINE`, `OFFLINE`, `UNKNOWN`), and cycle activity (`Active Cycle` / `No Active Cycle`).
- Keep the center-left area open for future metrics widgets.

## Data source
- `SensorsMapper.getSensor()` from `sensors.sensor`.
- Uses `SensorsMapper.getDashboardSensors()` and a `cycle_sensor` lookup for running state.
- Uses these fields on `Sensor`: `name`, `active`, `last_read_value`, `last_read_status`, `offline_streak`, `cycle_state`.

## UX notes
- Online/offline status is color-coded for quick scanning.
- Null readings display as `No reading`.
- Widget is stacked vertically on the right side and remains scrollable.
- Widget auto-refreshes every 60 seconds without a full page reload.

## Future follow-ups
- Add `last_read_ts` freshness indicator per sensor.
- Add offline streak badge and error count.
- Add auto-refresh and mini trend sparkline per sensor.
