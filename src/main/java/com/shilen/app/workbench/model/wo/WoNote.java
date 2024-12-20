package com.shilen.app.workbench.model.wo;

public class WoNote {

	private int id;
	private int woid;
	private String note;
    private java.sql.Date created;
    private String fcreated;
	
	public String getFcreated() {
		return fcreated;
	}
	public void setFcreated(String fcreated) {
		this.fcreated = fcreated;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getWoid() {
		return woid;
	}
	public void setWoid(int woid) {
		this.woid = woid;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public java.sql.Date getCreated() {
		return created;
	}
	public void setCreated(java.sql.Date created) {
		this.created = created;
	}

	
	
}
