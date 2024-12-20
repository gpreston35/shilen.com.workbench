package com.shilen.app.workbench.model;

public class User {
	
	private int id;
	private String first_name;
	private String last_name;
	private String sms_email;
	
	private int checked = 1;
	
	
	
	public String getFull_name() {
		return this.first_name + ' ' + this.last_name;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getSms_email() {
		return sms_email;
	}
	public void setSms_email(String sms_email) {
		this.sms_email = sms_email;
	}
	public int getChecked() {
		return checked;
	}
	public void setChecked(int checked) {
		this.checked = checked;
	}
	
	

}
