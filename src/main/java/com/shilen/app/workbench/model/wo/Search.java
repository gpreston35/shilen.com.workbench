package com.shilen.app.workbench.model.wo;

import java.sql.Timestamp;


public class Search {
	
    private String woid;
    private int caliber_id;
    private int status_id;
	private Timestamp toDate;
    private Timestamp fromDate;
	private String toDateInput;
    private String fromDateInput;
    private int considerDate = 0;
    
    public Timestamp getToDate() {
		return toDate;
	}
	public void setToDate(Timestamp toDate) {
		this.toDate = toDate;
	}
	public Timestamp getFromDate() {
		return fromDate;
	}
	public void setFromDate(Timestamp fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDateInput() {
		return toDateInput;
	}
	public void setToDateInput(String toDateInput) {
		this.toDateInput = toDateInput;
	}
	public String getFromDateInput() {
		return fromDateInput;
	}
	public void setFromDateInput(String fromDateInput) {
		this.fromDateInput = fromDateInput;
	}

	public int getCaliber_id() {
		return caliber_id;
	}
	public void setCaliber_id(int caliber_id) {
		this.caliber_id = caliber_id;
	}
	public int getStatus_id() {
		return status_id;
	}
	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}
	public int getConsiderDate() {
		return considerDate;
	}
	public void setConsiderDate(int considerDate) {
		this.considerDate = considerDate;
	}
	public String getWoid() {
		return woid;
	}
	public void setWoid(String woid) {
		this.woid = woid;
	}

}
