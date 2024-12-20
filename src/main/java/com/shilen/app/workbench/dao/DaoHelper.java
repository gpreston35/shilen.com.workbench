package com.shilen.app.workbench.dao;

public class DaoHelper {
	
	
	public String runoutpivot() {
		
		/*
		StringBuffer sb = new StringBuffer( String pivotField );
		
		sb.append( "<script>SELECT ");
		sb.append( pivotField );
		sb.append("  max(muzzle_tir) \"muzzle_max\", format(avg(muzzle_tir),5) \"muzzle_avg\", format(std(muzzle_tir),5) \"muzzle_std\", ");
        sb.append("  max(chamber_tir),5) \"chamber_max\", format(avg(chamber_tir),5) \"chamber_avg\", format(std(chamber_tir),5) \"chamber_std\" , ");
        
        count(*) "Samples", count(if(redrill = 'y',1,null)) "Redrill", 
        count(*) - count(if(redrill = 'y',1,null)) "# Bars",
        count(if(scrap = 'y',1,null)) "Scrap",
        format(  count(if(scrap = 'y',1,null)) / count(*) * 100,2) "Scrap %"
        from runout
group by 1
		
		
		"<script>SELECT r.id, r.run_date, c.caliber, s.spindle_num spindle, o.operator, r.muzzle_tir, r.chamber_tir, st.heat_name, "
		+ "l.length, r.redrill, r.scrap, r.woid, r.scrap_reason "
		+ "from runout r,"
		+ "     caliber c,"
		+ "     spindle s,"
		+ "     operators o,"
		+ "     steel st,"
		+ "     lk_length l"
		+ " where r.caliber_id = c.id"
		+ "   and r.spindle_id = s.id"
		+ "   and r.operator_id = o.id"
		+ "   and r.length_id = l.id "
		+ "   and r.steel_id = st.id "
		+ "   <if test=\"spindle_id != -1\">"
		+ "    and r.spindle_id = #{spindle_id}"
		+ "  </if>"
		+ "   <if test=\"caliber_id != -1\">"
		+ "    and r.caliber_id = #{caliber_id}"
		+ "  </if>"
		+ "   <if test=\"operator_id != -1\">"
		+ "    and r.operator_id = #{operator_id}"
		+ "  </if>"
		+ "   <if test=\"steel_id != -1\">"
		+ "    and r.steel_id = #{steel_id}"
		+ "  </if>"
		+ "   <if test=\"woid != ''\">"
		+ "    and r.woid = #{woid}"
		+ "  </if>"
		+ "   <if test=\"fromDateInput != ''\">"
		+ "    and r.run_date &gt; #{fromDateInput}"
		+ "  </if>"
		+ "   <if test=\"toDateInput != ''\">"
		+ "    and r.run_date &lt; #{toDateInput}"
		+ "  </if>"
		+ "  limit 22</script>"
		
		
		*/
		return "";
		
	}

}
