package com.shilen.app.workbench.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shilen.app.workbench.model.PickList;
import com.shilen.app.workbench.model.tool.ChamberReamer;

public interface ToolCRMapper {
	
	
	@Select("SELECT r.id, r.tool_identifier, r.chamber_name, r.dos as dos_str, r.eos as eos_str, s.status from "
			+ "   operations.tool_chamber_reamer r,"
			+ "   operations.lk_tool_status s"
			+ "   where r.status_id = s.id and ( upper(r.tool_identifier) like '%' #{search_term} '%' "
			+ "   or upper(r.chamber_name) like '%' #{search_term} '%')" )
	List<ChamberReamer> Search( String search_term );
	
	
	@Insert( "INSERT into operations.tool_chamber_reamer "
				+ "( tool_identifier, manufacturer, dom, "
				+ " mfg_print_id, mfg_serial_number, material, "
				+ "   dos, eos, "
				+ "	eos_reason, storage_location, created, updated, updated_by, "
				+ " created_by, drawing_number, chamber_name, flute_count, pilot_type, status_id, sharpen_count )"
			    + " values ("
				+ " #{tool_identifier}, #{manufacturer}, "
				+ " #{dom}, "
				+ " #{mfg_print_id}, #{mfg_serial_number}, #{material}, "
				+ " #{dos}, "
				+ " #{eos},"
				+ "	#{eos_reason}, #{storage_location},  now(), now(), #{updated_by}, "
				+ " #{created_by}, #{drawing_number}, #{chamber_name}, #{flute_count}, #{pilot_type}, #{status_id}, #{sharpen_count} )" )
	@Options(useGeneratedKeys = true, keyProperty="id", keyColumn="id") 
	void Insert( ChamberReamer reamer );		
			
			
	@Update( "UPDATE operations.tool_chamber_reamer "
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
			+ "      chamber_name = #{chamber_name}, "
			+ "      flute_count = #{flute_count},"
			+ "      pilot_type = #{pilot_type}, "
			+ "      status_id = #{status_id},"
			+ "      sharpen_count = #{sharpen_count}"
			+ "   where id = #{id}")
	 void Update( ChamberReamer reamer );
	
	
	 @Select( "select id,tool_identifier,manufacturer, status_id, sharpen_count, "
	 		+ "  dom as dom_str, "
	 		+ "  mfg_print_id, mfg_serial_number, material, "
	 		+ "  dos as dos_str, "
	 		+ "  eos as eos_str, "
	 		+ "  storage_location, created, updated, drawing_number, chamber_name, "
	 		+ "  flute_count, pilot_type, resharp_count, created_by, updated_by "
	 		+ "  from operations.tool_chamber_reamer "
	 		+ "  where id = #{id}")
	 ChamberReamer Read( Integer id );
	 
	 
	 @Select ("SELECT id, status_text text from operations.lk_status where dset = #{dset} and active = 1 "
	 		+ "ORDER BY id")
	 List<PickList> GetStatusList( int dset );
	 
	 
}


