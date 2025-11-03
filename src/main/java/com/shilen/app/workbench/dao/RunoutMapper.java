package com.shilen.app.workbench.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shilen.app.workbench.model.ro.Pivot;
import com.shilen.app.workbench.model.ro.Runout;
import com.shilen.app.workbench.model.ro.Search;

public interface RunoutMapper {
	
	@Select("<script>SELECT r.id, r.run_date, c.caliber, s.spindle spindle, o.operator, r.muzzle_tir, r.chamber_tir, st.heat_name, "
			+ "l.length, r.redrill, r.scrap, r.woid, r.update_dt, r.scrapreason_id "
			+ "from runout r,"
			+ "     caliber c,"
			+ "     lk_spindle s,"
			+ "     lk_operators o,"
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
			+ "   <if test=\"woid != 0\">"
			+ "    and r.woid = #{woid}"
			+ "  </if>"
			+ "   <if test=\"fromDateInput != ''\">"
			+ "    and r.run_date &gt; #{fromDateInput}"
			+ "  </if>"
			+ "   <if test=\"toDateInput != ''\">"
			+ "    and r.run_date &lt; #{toDateInput}"
			+ "  </if>"
			+ "  limit 500</script>")
	List<Runout> getSearchRunout( Search search );
	
	
	
	@Select("<script>select ${pivot_field} pivot_field, max(muzzle_tir) muzzle_max, format(avg(muzzle_tir),5) muzzle_avg, format(std(muzzle_tir),5) muzzle_std, "
			+ "               format(max(chamber_tir),5) chamber_max, format(avg(chamber_tir),5) chamber_avg, format(std(chamber_tir),5) chamber_std, "
			+ "               count(*) samples, count(if(redrill = 'y',1,null)) redrill, "
			+ "               count(*) - count(if(redrill = 'y',1,null)) num_bars, "
			+ "               count(if(scrap = 'y',1,null)) scrap , "
			+ "               format(  count(if(scrap = 'y',1,null)) / count(*) * 100,2) scrap_p"
			+ " from runout r,"
			+ "     caliber c,"
			+ "     lk_spindle s,"
			+ "     lk_operators o,"
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
			+ "  group by 1</script>")
	List<Pivot> pivotSearch( Search search );
	
	
	@Select("<script>select max(muzzle_tir) muzzle_max, format(avg(muzzle_tir),5) muzzle_avg, format(std(muzzle_tir),5) muzzle_std, "
			+ "               format(max(chamber_tir),5) chamber_max, format(avg(chamber_tir),5) chamber_avg, format(std(chamber_tir),5) chamber_std, "
			+ "               count(*) samples, count(if(redrill = 'y',1,null)) redrill, "
			+ "               count(*) - count(if(redrill = 'y',1,null)) num_bars, "
			+ "               count(if(scrap = 'y',1,null)) scrap , "
			+ "               format(  count(if(scrap = 'y',1,null)) / count(*) * 100,2) scrap_p"
			+ " from runout r,"
			+ "     caliber c,"
			+ "     lk_spindle s,"
			+ "     lk_operators o,"
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
			+ "  </if></script>")

	Pivot pivotSearchTotals( Search search );
	
	
	@Select("select * from runout where id = #{id}")
	Runout getRunout(int id);
	
	@Insert("insert into runout (caliber_id, spindle_id, operator_id, muzzle_tir, chamber_tir, steel_id, length_id, "
			+ "redrill, scrap, woid, scrapreason_id, update_dt, brtool, dhtool, run_date ) "
			+ "VALUES (  #{caliber_id}, #{spindle_id}, #{operator_id}, #{muzzle_tir}, #{chamber_tir}, #{steel_id}, #{length_id},"
			+ " #{redrill}, #{scrap}, #{woid}, #{scrapreason_id}, now(), #{brtool}, #{dhtool}, #{run_date} )")
	@Options(useGeneratedKeys = true, keyProperty="id", keyColumn="id") 
	void insert( Runout ro );
	
	@Update("update runout set caliber_id = #{caliber_id}, spindle_id = #{spindle_id}, operator_id = #{operator_id}, steel_id = #{steel_id},"
			+ "length_id = #{length_id}, redrill = #{redrill}, scrap = #{scrap} ,woid = #{woid},"
			+ " brtool = #{brtool}, dhtool = #{dhtool}, run_date = #{run_date}, "
			+ "chamber_tir = #{chamber_tir}, muzzle_tir = #{muzzle_tir}, scrapreason_id = #{scrapreason_id}, update_dt = now() where id = #{id}")
	void update( Runout ro);
		
	
}
