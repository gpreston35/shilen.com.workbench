package com.shilen.app.workbench.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shilen.app.workbench.model.PickList;
import com.shilen.app.workbench.model.wo.Search;
import com.shilen.app.workbench.model.wo.SearchResults;
import com.shilen.app.workbench.model.wo.WoNote;
import com.shilen.app.workbench.model.wo.WoQty;
import com.shilen.app.workbench.model.wo.WoQuality;
import com.shilen.app.workbench.model.wo.WoScrap;
import com.shilen.app.workbench.model.wo.WoToolHistory;
import com.shilen.app.workbench.model.wo.WorkOrder;



public interface WorkOrderMapper {
		
	// For migration
	@Select("select * from operations.wo_qty order by woid")
	List<WoQty> getWoqty();
	
	@Select("SELECT date_format(created, '%m/%d/%Y %h:%i %p') from operations.wo where id = #{id}")
	String getWorkorderDate(int id);
	
	@Select("SELECT id, status_text value FROM operations.lk_status")
	List<PickList> getStatusList();
	
	@Select("SELECT sid sid, rifling value FROM operations.rifling")
	List<PickList> getRiflingList();
	
	@Select("SELECT id, heat_name value FROM operations.steel")
	List<PickList> getSteelList();
	
	@Select("SELECT id, twist value FROM operations.twist order by lpad(lower(twist),10,0) asc")
	List<PickList> getTwistList();
	
	@Select("SELECT id, groove value FROM operations.lk_grooves order by lpad(lower(groove),10,0) asc")
	List<PickList> getGrooveList();
	
	@Select("SELECT sid, steeltype value FROM operations.steel_type")
	List<PickList> getSteelTypeList();
	
	@Insert("insert into operations.wo (caliber_id, steeltype_id, rifling_id, groove_id, twist_id, created, status_id, steel_id, prod_date, updated, dhtool, brtool, bntool ) " + 
			"values ( #{caliber_id}, #{steeltype_id}, #{rifling_id}, #{groove_id}, #{twist_id}, now(), #{status_id}, #{steel_id},  #{prod_date}, now(), #{dhtool}, #{brtool}, #{bntool} )")
	@Options(useGeneratedKeys = true, keyProperty="id", keyColumn="id") 
	void insertWo( WorkOrder wo);
	
	@Update("update operations.wo set caliber_id = #{caliber_id}, steeltype_id = #{steeltype_id}, rifling_id = #{rifling_id}, " +
			"groove_id = #{groove_id}, twist_id = #{twist_id}, status_id = #{status_id}, steel_id = #{steel_id}, " +
			"prod_date = #{prod_date}, note = #{note}, updated = now(), dhtool = #{dhtool}, brtool = #{brtool}, bntool = #{bntool} where id = #{id}")
	void updateWo( WorkOrder wo);
	
	@Insert("insert into operations.wo_tool_history ( woid, tool_identifier, tool_type, updated, updated_by )"
			+ "VALUES ( #{woid}, #{tool_identifier}, #{tool_type}, now(), #{updated_by} )")
	void insertWoToolHistory( WoToolHistory wth );
	
	@Select("Select * from operations.wo_tool_history where woid = #{woid} order by updated desc")
	List<WoToolHistory> getWoToolHistory( int  woid );
	
	
	@Select("select id woid, qal_select_match, qal_match, note from operations.wo where id = #{id} ")
	WoQuality getWorkorderQuality( int id );
	
	
	@Select("select * from operations.wo where id = #{id}")
	WorkOrder getWorkorder(int id);
	
	
	@Select("<script>SELECT w.id woid, w.created, s.status_text, c.caliber, st.steeltype, r.rifling " + 
			"FROM operations.wo w, " + 
			"     operations.lk_status s, " + 
			"     operations.caliber c, " + 
			"     operations.steel_type st, " + 
			"     operations.rifling r " + 
			"where w.status_id = s.id " + 
			"  and w.caliber_id = c.id " + 
			"  and w.steeltype_id = st.sid " + 
			"  and w.rifling_id = r.sid " + 
            " <if test=\"caliber_id != -1\" > " +
            "   and w.caliber_id = #{caliber_id} " +
            "</if> " +
			"<if test=\"woid != 0\"> " + 
			"   and w.id = #{woid} " + 
			"</if> " +
            "<if test=\"status_id != -1\"> " +
            " and w.status_id = #{status_id}  " +           
            "</if> " +
			"<if test=\"considerDate == 1\"> " +
            "<![CDATA[  and ( w.created >= #{fromDate} and w.created <= #{toDate}   ) ]]> " +
            "</if>" +
			"</script>")
	List<SearchResults> search( Search form );
	
	@Select ("SELECT id, caliber value FROM operations.caliber order by caliber")
	List<PickList> getCaliberList();
	
	
	@Select("SELECT w.id, LPAD(w.id, 8, '0') woid, c.caliber, " + 
			"  w.steeltype_id, s.heat_name, IF(w.rifling_id='S','',rifling_id) rifling, t.twist, " + 
			"  g.groove, DATE_FORMAT(w.created, \"%m-%d-%Y\") screated  " + 
			"from wo w, " + 
			"     twist t, " + 
			"     caliber c, " + 
			"     lk_grooves g, " + 
			"     steel s " + 
			"where w.caliber_id = c.id " + 
			"     and w.twist_id = t.id " + 
			"     and w.groove_id = g.id " + 
			"     and w.steel_id = s.id " + 
			"     and w.id = #{id}")
	WorkOrder getWorkorderPdf(int id);
	
	
	@Select("SELECT id, reason value FROM operations.scrap_reasons where process = 'QA'")
	List<PickList>getScrapReasons();
	
	@Select("SELECT * FROM operations.wo_qty where woid = #{id}")
	List<WoQty> getWorkorderQty( int id);
	
	@Update("UPDATE operations.wo_qty set qty = #{qty}, length = #{length} where id = #{id}")
	void updateWoQty( WoQty woqty );
	
	@Insert("INSERT into operations.wo_qty (woid, qty, length) VALUES (#{woid}, #{qty}, #{length})")
	@Options(useGeneratedKeys = true, keyProperty="id", keyColumn="id") 
	void insertWoQty( WoQty woqty );
	
	@Delete("DELETE from operations.wo_qty where id = #{id}")
	void deleteWoQty( WoQty woQty);
	
	@Select("select w.qty, w.length, wq.* from operations.wo_qty w, operations.wo_quality wq "
			+"where w.woid = #{id} and w.id = wq.wo_qty_id    ")
	List<WoQuality> getWoQualityByWoId(int id);
	
	@Insert("INSERT into operations.wo_quality ( wo_qty_id, sm_count, m_count, updated ) values( #{wo_qty_id}"
			+ ", #{sm_count}, #{m_count}, now())")
	@Options(useGeneratedKeys = true, keyProperty="id", keyColumn="id") 
	void insertWoQuality( WoQuality wq);
	
	@Update("Update operations.wo_quality set sm_count = #{sm_count}, m_count = #{m_count}, updated = now() where wo_qty_id = #{wo_qty_id}")
	void updateWoQuality( WoQuality wq);
	
	@Delete("Delete from operations.wo_quality where wo_qty_id = #{id}")
	void deleteWoQuality(int id);
	
	@Select("select sum(c.qty) rejected, (select sum(sm_count+m_count) from wo_quality where wo_qty_id = #{id}) as passed from ( " + 
			"		select ws.qty " + 
			"		from operations.wo_scrap ws " + 
			"		where ws.wo_qty_id = #{id}" + 
			"		union all" + 
			"		select 1 from operations.runout" + 
			"		where woid = #{woid} and length = #{length} and scrap = 'y' ) as c")
	WoQuality getQualityCounts(WoQty woqty);
		
	@Insert("Insert into operations.wo_scrap ( wo_qty_id, process_id, scrap_reason_id, qty, created, note )"
			+" values(#{wo_qty_id}, #{process_id}, #{scrap_reason_id}, #{qty}, now(), #{note} )")
	void insertWoScrap( WoScrap woscrap );
	
	/*
	@Select("select ws.id, ws.wo_qty_id, lp.process, lksr.reason, ws.qty " + 
			"from operations.wo_scrap ws, operations.lk_process lp, operations.scrap_reasons lksr " + 
			"where ws.process_id = lp.id and ws.scrap_reason_id = lksr.id and ws.wo_qty_id = #{id} "
			+ "union all " + 
			"select -1 id, -1, 'Runout', scrap_reason reason, 1 from operations.runout " + 
			"where woid = #{woid} and length = #{length} and scrap = 'y'")
	List<WoScrap> getByWoScrapId(WoQty woqty); 
	*/
	
	@Select("select ws.id, ws.wo_qty_id, ws.qty, lksr.reason, lp.process "
			+ "			from operations.wo_scrap ws, "
			+ "				operations.lk_scrapreason lksr, "
			+ "                operations.lk_process lp "
			+ "           where "
			+ "               ws.scrap_reason_id = lksr.id"
			+ "               and ws.process_id = lp.id"
			+ "               and ws.wo_qty_id = #{id}")
	List<WoScrap> getByWoScrapId(Integer id);
	
	@Select("select id, woid, length from operations.wo_qty where id = #{id}")
	WoQty getWoQty( int id);
	
	@Select("select id, woid, length, qty from operations.wo_qty where woid = #{id}")
	List<WoQty> getWoQtyByWoid( int id);
	
	@Delete("delete from operations.wo_scrap where id = #{id}")
	void deleteWoScrap(int id);
	
	@Insert("insert into operations.wo_notes ( woid, note, created ) values ( #{id}, #{note}, now() )")
	void insertWoNote( WorkOrder wo);
	
	@Select("select replace(note,'\\r\\n','<br>') note, date_format(created, '%Y-%m-%d %H:%i') fcreated from operations.wo_notes where woid = #{id}")
	List<WoNote> getWoNotes(int id);
	
	@Select("SELECT w.id woid, w.created, s.status_text, c.caliber, st.steeltype, r.rifling " + 
			"FROM operations.wo w, " + 
			"     operations.lk_status s, " + 
			"     operations.caliber c, " + 
			"     operations.steel_type st, " + 
			"     operations.rifling r " + 
			"where w.status_id = s.id " + 
			"  and w.caliber_id = c.id " + 
			"  and w.steeltype_id = st.sid " + 
			"  and w.rifling_id = r.sid " + 
            "   and w.id = #{id}")
	SearchResults getShortDetail( int id );
	

	
}
