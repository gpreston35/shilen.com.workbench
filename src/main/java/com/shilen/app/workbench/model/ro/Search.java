package com.shilen.app.workbench.model.ro;

public class Search {
	
	
	private int woid;
	private String fromDateInput;
	private String toDateInput;
	private int caliber_id;
	private int spindle_id;
	private int operator_id;
	private int steel_id;
	private String run_date;
	private String view = "data";
	private String pivot_field;
	
	
	
	public int getWoid() {
		return woid;
	}
	public void setWoid(int woid) {
		this.woid = woid;
	}
	public String getFromDateInput() {
		return fromDateInput;
	}
	public void setFromDateInput(String fromDateInput) {
		this.fromDateInput = fromDateInput;
	}
	public String getToDateInput() {
		return toDateInput;
	}
	public void setToDateInput(String toDateInput) {
		this.toDateInput = toDateInput;
	}
	
	
	public int getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(int operator_id) {
		this.operator_id = operator_id;
	}
	public int getSteel_id() {
		return steel_id;
	}
	public void setSteel_id(int steel_id) {
		this.steel_id = steel_id;
	}


	
	public int getCaliber_id() {
		return caliber_id;
	}
	public void setCaliber_id(int caliber_id) {
		this.caliber_id = caliber_id;
	}
	public int getSpindle_id() {
		return spindle_id;
	}
	public void setSpindle_id(int spindle_id) {
		this.spindle_id = spindle_id;
	}
	public String getRun_date() {
		return run_date;
	}
	public void setRun_date(String run_date) {
		this.run_date = run_date;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public String getPivot_field() {
		return pivot_field;
	}
	public void setPivot_field(String pivot_field) {
		this.pivot_field = pivot_field;
	}
	

}
