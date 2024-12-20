package com.shilen.app.workbench.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shilen.app.workbench.model.config.Caliber;

public interface ConfigCaliberMapper {
	
	@Select("SELECT c.id, c.caliber, c.description, case when c.status_id = 1 then \"Active\" else \"Not Active\" end status_text from "
			+ "   operations.caliber c "
			+ "   where upper(c.caliber) like '%' #{search_term} '%' ")
	List<Caliber> Search( String search_term );
	
	
	@Insert( "INSERT into operations.caliber "
				+ "( caliber, description, status_id, created, updated, created_by, updated_by, bore_reamer_high, bore_reamer_low, button_low, button_high, drill_high, drill_low,"
				+ " grv_nominal, grv_utol, grv_ltol, grv_target, bor_nominal, bor_utol, bor_ltol, bor_target  ) "
			    + " values ("
				+ " #{caliber}, #{description}, "
				+ " #{tool_range_low}, "
				+ " #{tool_range_high}, #{status_id}, "
				+ " now(), now(), #{created_by}, #{updated_by}, #{bore_reamer_high}, #{bore_reamer_low}, #{button_low}, #{button_high}, #{drill_high}, #{drill_low},"
				+ " #{grv_nominal}, #{grv_utol}, #{grv_ltol}, #{grv_target}, #{bor_nominal}, #{bor_utol}, #{bor_ltol}, #{bor_target}  )" )
			
	@Options(useGeneratedKeys = true, keyProperty="id", keyColumn="id") 
	void Insert( Caliber caliber );		
			
			
	@Update( "UPDATE operations.caliber "
			+ "  set description = #{description},"
			+ "      bore_reamer_low = #{bore_reamer_low},"
			+ "      bore_reamer_high = #{bore_reamer_high},"
			+ "      button_low = #{button_low},"
			+ "      button_high = #{button_high},"
			+ "      drill_low = #{drill_low},"
			+ "      drill_high = #{drill_high},"
			+ "      status_id = #{status_id},"
			+ "      updated = now(),"
			+ "      updated_by = #{updated_by},"
			+ "      grv_nominal = #{grv_nominal},"
			+ "	     grv_utol = #{grv_utol},"
			+ "      grv_ltol = #{grv_ltol},"
			+ "      grv_target = #{grv_target},"
			+ "      bor_nominal = #{bor_nominal},"
			+ "      bor_utol = #{bor_utol},"
			+ "      bor_ltol = #{bor_ltol},"
			+ "      bor_target = #{bor_target}"
			+ "   where id = #{id}")
	 void Update( Caliber caliber );
	
	
	 @Select( "select *"
	 		+ "  from operations.caliber "
	 		+ "  where id = #{id}")
	 Caliber Read( Integer id );
	
	 

}
