package com.shilen.app.workbench.model;

public class FileMeta {
	
	private int id;
	private int module_id;
	private String file_name;
	private int file_size;
	private String file_size_str;
	
	private String uploaded_by;
	private java.sql.Date uploaded;
	private int module_record_id;
	private int file_storage_id;
	private String uploaded_str;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getModule_id() {
		return module_id;
	}
	public void setModule_id(int module_id) {
		this.module_id = module_id;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public int getFile_size() {
		return file_size;
	}
	public void setFile_size(int file_size) {
		this.file_size = file_size;
	}
	public String getUploaded_by() {
		return uploaded_by;
	}
	public void setUploaded_by(String uploaded_by) {
		this.uploaded_by = uploaded_by;
	}
	public java.sql.Date getUploaded() {
		return uploaded;
	}
	public void setUploaded(java.sql.Date uploaded) {
		this.uploaded = uploaded;
	}
	public int getModule_record_id() {
		return module_record_id;
	}
	public void setModule_record_id(int module_record_id) {
		this.module_record_id = module_record_id;
	}
	public int getFile_storage_id() {
		return file_storage_id;
	}
	public void setFile_storage_id(int file_storage_id) {
		this.file_storage_id = file_storage_id;
	}
	public String getFile_size_str() {
		return file_size_str;
	}
	public void setFile_size_str(String file_size_str) {
		this.file_size_str = file_size_str;
	}
	public String getUploaded_str() {
		return uploaded_str;
	}
	public void setUploaded_str(String uploaded_str) {
		this.uploaded_str = uploaded_str;
	}

}
