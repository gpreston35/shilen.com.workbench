package com.shilen.app.workbench.dao;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shilen.app.workbench.model.PickList;
import com.shilen.app.workbench.model.tool.DeepHole;

public interface ToolDHMapper {
	
	
	@Select("SELECT d.id, d.tool_identifier, d.dos as dos_str, d.eos as eos_str, if(d.status_id=1,'Active','Not Active') as status from "
			+ "   operations.tool_deephole d"
			+ "   where upper(d.tool_identifier) like '%' #{search_term} '%' ")
	List<DeepHole> Search( String search_term );
	
	
	@Insert( "INSERT into operations.tool_deephole "
				+ "( tool_identifier, manufacturer, dom, "
				+ " mfg_print_id, mfg_serial_number, material, "
				+ "   dos, eos, "
				+ "	eos_reason, storage_location, created, updated, updated_by, "
				+ " created_by, drawing_number, status_id, dia_ao, length, style, retip_count, retip_date, hole_type)"
			    + " values ("
				+ " #{tool_identifier}, #{manufacturer}, "
				+ " #{dom}, "
				+ " #{mfg_print_id}, #{mfg_serial_number}, #{material}, "
				+ " #{dos}, "
				+ " #{eos},"
				+ "	#{eos_reason}, #{storage_location},  now(), now(), #{updated_by}, "
				+ " #{created_by}, #{drawing_number}, #{status_id}, #{dia_ao}, #{length}, #{style}, #{retip_count}, #{retip_date}, #{hole_type} )" )
	@Options(useGeneratedKeys = true, keyProperty="id", keyColumn="id") 
	void Insert( DeepHole deephole );		
			
	@Update( "UPDATE operations.tool_deephole set tool_identifier = #{identifier} where id = #{id}" )
	void updateToolIdentifier( String identifier, int id );
			
	@Update( "UPDATE operations.tool_deephole "
			+ "  set tool_identifier = #{tool_identifier},"
			+ "      manufacturer = #{manufacturer},"
			+ "      dom = #{dom},"
			+ "      mfg_print_id = #{mfg_print_id},"
			+ "      mfg_serial_number = #{mfg_serial_number},"
			+ "      dos = #{dos},"
			+ "      eos = #{eos},"
			+ "      material = #{material},"
			+ "      eos_reason = #{eos_reason},"
			+ "      storage_location = #{storage_location},"
			+ "      updated = now(),"
			+ "      updated_by = #{updated_by},"
			+ "      drawing_number = #{drawing_number},"
			+ "      dia_ao = #{dia_ao},"
			+ "      length = #{length}, "
			+ "      style = #{style}, "
			+ "      retip_count = #{retip_count}, "
			+ "      retip_date = #{retip_date}, "
			+ "      hole_type = #{hole_type}, "
			+ "      status_id = #{status_id}"
			+ "   where id = #{id}")
	 void Update( DeepHole deephole  );
	
	
	 @Select( "select id,tool_identifier,manufacturer, status_id,  "
	 		+ "  dom as dom_str, "
	 		+ "  mfg_print_id, mfg_serial_number, material, "
	 		+ "  dos as dos_str, "
	 		+ "  eos as eos_str, "
	 		+ "  storage_location, created, updated, drawing_number, eos_reason, "
	 		+ "  created_by, updated_by, dia_ao, length, style, retip_count, retip_date as retip_date_str, hole_type "
	 		+ "  from operations.tool_deephole "
	 		+ "  where id = #{id}")
	 DeepHole Read( Integer id );
	
	 
//	 @Select ("SELECT id, status_text text from operations.lk_status where active = 1 "
//		 		+ "ORDER BY id")
//		 List<PickList> GetStatusList( int dset );
		 
	 
	 
}


