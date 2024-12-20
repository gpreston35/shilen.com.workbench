package com.shilen.app.workbench.model.wo;

public class WoQuality {
	
	private int id;
	private int wo_qty_id;
	private int sm_count;
	private int m_count;
	private int length;
	private int qty;
	
	
	private int rejected;
	private int passed;
	
	public int getLength() {
		return length;
	}


	public void setLength(int length) {
		this.length = length;
	}


	public int getPassed() {
		return passed;
	}


	public void setPassed(int passed) {
		this.passed = passed;
	}


	public int getQty() {
		return qty;
	}


	public void setQty(int qty) {
		this.qty = qty;
	}
	
	
	public WoQuality( int id) {
		this.wo_qty_id = id;
	}
	
	
	public WoQuality() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getWo_qty_id() {
		return wo_qty_id;
	}
	public void setWo_qty_id(int wo_qty_id) {
		this.wo_qty_id = wo_qty_id;
	}
	public int getSm_count() {
		return sm_count;
	}
	public void setSm_count(int sm_count) {
		this.sm_count = sm_count;
	}
	public int getM_count() {
		return m_count;
	}
	public void setM_count(int m_count) {
		this.m_count = m_count;
	}


	public int getRejected() {
		return rejected;
	}


	public void setRejected(int rejected) {
		this.rejected = rejected;
	}

}
