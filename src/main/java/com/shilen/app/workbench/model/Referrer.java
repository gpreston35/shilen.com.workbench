package com.shilen.app.workbench.model;

public class Referrer {

	private String referrer_url;
	private int referrer_module_record_id;
	private int referring_module_id;
	
	public Referrer() {};
	
	public Referrer( int referrer_module_record_id,  String referrer_url, int referring_module_id ) {
		
		this.referrer_module_record_id = referrer_module_record_id;
		this.referrer_url = referrer_url;
		this.referring_module_id = referring_module_id;
		
	}
	

	public String getReferrer_url() {
		return referrer_url;
	}
	
	public void setReferrer_url(String referrer_url) {
		this.referrer_url = referrer_url;
	}

	public int getReferring_module_id() {
		return referring_module_id;
	}

	public void setReferring_module_id(int referring_module_id) {
		this.referring_module_id = referring_module_id;
	}

	public int getReferrer_module_record_id() {
		return referrer_module_record_id;
	}

	public void setReferrer_module_record_id(int referrer_module_record_id) {
		this.referrer_module_record_id = referrer_module_record_id;
	}
	
	

}
