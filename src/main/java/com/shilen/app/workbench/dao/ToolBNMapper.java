package com.shilen.app.workbench.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shilen.app.workbench.model.PickList;
import com.shilen.app.workbench.model.tool.Button;

public interface ToolBNMapper {
	
	
	@Select("SELECT b.id, b.tool_identifier, b.dos, b.eos, if(b.status_id = 1,'Active','Not Active') as status from "
			+ "   operations.tool_button b"
			+ "   where upper(b.tool_identifier) like '%' #{search_term} '%' ")
	List<Button> Search( String search_term );
	
	
	@Insert( "INSERT into operations.tool_button "
				+ "( manufacturer, dom, "
				+ " mfg_print_id, mfg_serial_number, material, "
				+ "   dos, eos, "
				+ "	eos_reason, storage_location, created, updated, updated_by, "
				+ " created_by, drawing_number, status_id, rifling_count, major_dia, minor_dia, rifling_type, rifling_width, shaft_size, button_shank_size )"
			    + " values ("
				+ " #{manufacturer}, "
				+ " #{dom}, "
				+ " #{mfg_print_id}, #{mfg_serial_number}, #{material}, "
				+ " #{dos}, "
				+ " #{eos},"
				+ "	#{eos_reason}, #{storage_location},  now(), now(), #{updated_by}, "
				+ " #{created_by}, #{drawing_number}, #{status_id}, #{rifling_count}, #{major_dia}, #{minor_dia}, #{rifling_type}, #{rifling_width}, #{shaft_size}, #{button_shank_size} )" )
	@Options(useGeneratedKeys = true, keyProperty="id", keyColumn="id") 
	void Insert( Button button );		
			
	@Update( "UPDATE operations.tool_button set tool_identifier = #{identifier} where id = #{id}" )
	void updateToolIdentifier( String identifier, int id );
	
	@Update( "UPDATE operations.tool_button "
			+ "  set manufacturer = #{manufacturer},"
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
			+ "      rifling_count = #{rifling_count},"
			+ "      major_dia = #{major_dia}, "
			+ "      minor_dia = #{minor_dia}, "
			+ "      rifling_type = #{rifling_type}, "
			+ "      rifling_width = #{rifling_width}, "
			+ "      shaft_size = #{shaft_size}, "
			+ "      button_shank_size = #{button_shank_size}, "
			+ "      status_id = #{status_id}"
			+ "   where id = #{id}")
	 void Update( Button button );
	
	
	 @Select( "select id,tool_identifier,manufacturer, status_id,  "
	 		+ "  dom as dom_str, "
	 		+ "  mfg_print_id, mfg_serial_number, material, "
	 		+ "  dos as dos_str, "
	 		+ "  eos as eos_str, "
	 		+ "  storage_location, created, updated, drawing_number, eos_reason, "
	 		+ "  created_by, updated_by, rifling_count, major_dia, minor_dia, rifling_type, rifling_width, shaft_size, button_shank_size "
	 		+ "  from operations.tool_button "
	 		+ "  where id = #{id}")
	 Button Read( Integer id );
		 
 
//	 @Select ("SELECT id, status_text text from operations.lk_status where active = 1 "
//		 		+ "ORDER BY id")
//		 List<PickList> GetStatusList( int dset );
		 
	 
	 
}


