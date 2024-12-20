package com.shilen.app.workbench.model.wo;

public class WoScrap {
	
	private int id;
	private int wo_qty_id;
	private int process_id;
	private int scrap_reason_id;
	private int qty;
	private String note;
	
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	private String process;
	private String reason;
	
	
	

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
	public int getProcess_id() {
		return process_id;
	}
	public void setProcess_id(int process_id) {
		this.process_id = process_id;
	}
	public int getScrap_reason_id() {
		return scrap_reason_id;
	}
	public void setScrap_reason_id(int scrap_reason_id) {
		this.scrap_reason_id = scrap_reason_id;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}

	
	
	
		
}
