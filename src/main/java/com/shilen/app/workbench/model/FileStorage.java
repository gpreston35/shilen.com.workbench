package com.shilen.app.workbench.model;

public class FileStorage {
	
	private int id;
	private byte[] file_blob;
	
	private String file_name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public byte[] getFile_blob() {
		return file_blob;
	}
	public void setFile_blob(byte[] file_blob) {
		this.file_blob = file_blob;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}



}
