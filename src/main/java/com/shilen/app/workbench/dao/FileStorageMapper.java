package com.shilen.app.workbench.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.shilen.app.workbench.model.FileMeta;
import com.shilen.app.workbench.model.FileStorage;

public interface FileStorageMapper {
	
	@Delete( "DELETE from operations.file_storage where id = #{id};"
			+ "DELETE from operations.file_meta where file_storage_id = #{id}")
	void delete( int id );
		
	@Insert( "INSERT into operations.file_storage ( file_blob ) values ( #{file_blob} )" )
	@Options(useGeneratedKeys = true, keyProperty="id", keyColumn="id") 
	void store( FileStorage fileStorage );

	@Select( "SELECT s.file_blob, m.file_name from operations.file_meta m, operations.file_storage s where "
			+ "m.file_storage_id = s.id and m.file_storage_id = #{id}")
	FileStorage getFile( int id );
	
	@Insert( "INSERT into operations.file_meta ( module_id, file_name, file_size, uploaded, uploaded_by, module_record_id, file_storage_id )"
			+ " VALUES ( #{module_id}, #{file_name}, #{file_size}, now() , #{uploaded_by}, #{module_record_id}, #{file_storage_id} ) ")
	void storeFileMeta( FileMeta fileMeta );
	
	@Select( "SELECT m.file_name, substring_index(m.file_name,\".\" ,-1) file_extension, format(m.file_size,0) file_size_str, m.uploaded, date_format( m.uploaded, '%Y-%m-%d %h:%i %p') uploaded_str, m.uploaded_by, "
			+ "file_storage_id from operations.file_meta m where m.module_id = #{module_id} and module_record_id = #{module_record_id}" )
	List<FileMeta> getList( int module_id, int module_record_id );
	
}
