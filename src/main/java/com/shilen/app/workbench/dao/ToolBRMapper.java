package com.shilen.app.workbench.dao;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shilen.app.workbench.model.config.Caliber;
import com.shilen.app.workbench.model.tool.BoreReamer;
import com.shilen.app.workbench.model.tool.ToolSearchForm;

public interface ToolBRMapper {
	
	
	@Select("<script>"
			+ "SELECT r.id, r.tool_identifier, r.style, r.flute_count, r.dia_am, "
			+ "       r.dos as dos_str, r.eos as eos_str, "
			+ "       if(r.status_id=1,'Active','Not Active') as status "
			+ "  FROM operations.tool_bore_reamer r "
			+ " WHERE 1 = 1 "
			+ "   <if test=\"search_term != null and search_term != ''\">"
			+ "   AND upper(r.tool_identifier) like concat('%', upper(#{search_term}), '%')"
			+ "   </if>"
			+ "   <if test=\"style != null and style != ''\">"
			+ "   AND upper(ifnull(r.style, '')) like concat('%', upper(#{style}), '%')"
			+ "   </if>"
			+ "   <if test=\"flute_count != null\">"
			+ "   AND r.flute_count = #{flute_count}"
			+ "   </if>"
			+ "   <if test=\"diameter_from != null\">"
			+ "   AND r.dia_am &gt;= #{diameter_from}"
			+ "   </if>"
			+ "   <if test=\"diameter_to != null\">"
			+ "   AND r.dia_am &lt;= #{diameter_to}"
			+ "   </if>"
			+ "   <if test=\"active != null\">"
			+ "   AND r.status_id = #{active}"
			+ "   </if>"
			+ " ORDER BY r.tool_identifier"
			+ "</script>")
	List<BoreReamer> Search(ToolSearchForm form);

	@Select("SELECT id, caliber, bore_reamer_low, bore_reamer_high "
			+ "FROM operations.caliber "
			+ "WHERE IFNULL(bore_reamer_low, 0) <> 0 "
			+ "  AND IFNULL(bore_reamer_high, 0) <> 0 "
			+ "ORDER BY caliber")
	List<Caliber> GetBoreReamerPresets();
	
	
	@Insert( "INSERT into operations.tool_bore_reamer "
				+ "( tool_identifier, manufacturer, dom, "
				+ " mfg_print_id, mfg_serial_number, material, "
				+ "   dos, eos, "
				+ "	eos_reason, storage_location, created, updated, updated_by, "
				+ " created_by, drawing_number, flute_count, status_id, style, dia_ao, dia_am, dia_hs, shaft_dia )"
			    + " values ("
				+ " #{tool_identifier}, #{manufacturer}, "
				+ " #{dom}, "
				+ " #{mfg_print_id}, #{mfg_serial_number}, #{material}, "
				+ " #{dos}, "
				+ " #{eos},"
				+ "	#{eos_reason}, #{storage_location},  now(), now(), #{updated_by}, "
				+ " #{created_by}, #{drawing_number}, #{flute_count}, #{status_id}, #{style}, #{dia_ao}, #{dia_am}, #{dia_hs}, #{shaft_dia} )" )
	@Options(useGeneratedKeys = true, keyProperty="id", keyColumn="id") 
	void Insert( BoreReamer reamer );		
			
	@Update( "UPDATE operations.tool_bore_reamer set tool_identifier = #{identifier} where id = #{id}" )
	void updateToolIdentifier( String identifier, int id );
			
	@Update( "UPDATE operations.tool_bore_reamer "
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
			+ "      flute_count = #{flute_count},"
			+ "      style = #{style}, "
			+ "      dia_ao = #{dia_ao}, "
			+ "      dia_am = #{dia_am}, "
			+ "      dia_hs = #{dia_hs}, "
			+ "      shaft_dia = #{shaft_dia}, "
			+ "      status_id = #{status_id}"
			+ "   where id = #{id}")
	 void Update( BoreReamer reamer );
	
	
	 @Select( "select id,tool_identifier,manufacturer, status_id,  "
	 		+ "  dom as dom_str, "
	 		+ "  mfg_print_id, mfg_serial_number, material, "
	 		+ "  dos as dos_str, "
	 		+ "  eos as eos_str, "
	 		+ "  storage_location, created, updated, drawing_number, eos_reason, "
	 		+ "  flute_count, style, created_by, updated_by, dia_ao, dia_am, dia_hs, shaft_dia "
	 		+ "  from operations.tool_bore_reamer "
	 		+ "  where id = #{id}")
	 BoreReamer Read( Integer id );
	
	 
 
//	 @Select ("SELECT id, status_text text from operations.lk_status where active = 1 "
//		 		+ "ORDER BY id")
//		 List<PickList> GetStatusList( int dset );
		 
	  
}
