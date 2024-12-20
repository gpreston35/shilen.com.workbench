package com.shilen.app.workbench.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.shilen.app.workbench.model.Note;

public interface NoteStorageMapper {
	
	 @Select( "SELECT * from operations.notes where module_id = #{module_id} and record_id = #{record_id} ORDER BY created DESC")
	 List<Note> List(Integer record_id, int module_id );
		 
	 @Insert( "INSERT into operations.notes ( module_id, note, created, created_by, record_id ) "
	 		+ " VALUES ( #{module_id}, #{note}, now(), #{created_by}, #{record_id} )")
	 void Insert( Note note );
	 

}
