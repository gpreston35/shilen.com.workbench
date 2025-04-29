package com.shilen.app.workbench.model.wo;

public class ToolAnalysis {
	
	private String tool_identifier;
	private String dos;
	private String storage_location;
	private String assigned;
	private int num_barrels;
	private int num_scrap;
	private String scrap_percentage;
	private String diameter;
	
	
	public String getTool_identifier() {
		return tool_identifier;
	}
	public String getDos() {
		return dos;
	}
	public String getStorage_location() {
		return storage_location;
	}
	public String getAssigned() {
		return assigned;
	}
	public int getNum_barrels() {
		return num_barrels;
	}
	public int getNum_scrap() {
		return num_scrap;
	}
	public String getScrap_percentage() {
		return scrap_percentage;
	}
	public void setTool_identifier(String tool_identifier) {
		this.tool_identifier = tool_identifier;
	}
	public void setDos(String dos) {
		this.dos = dos;
	}
	public void setStorage_location(String storage_location) {
		this.storage_location = storage_location;
	}
	public void setAssigned(String assigned) {
		this.assigned = assigned;
	}
	public void setNum_barrels(int num_barrels) {
		this.num_barrels = num_barrels;
	}
	public void setNum_scrap(int num_scrap) {
		this.num_scrap = num_scrap;
	}
	public void setScrap_percentage(String scrap_percentage) {
		this.scrap_percentage = scrap_percentage;
	}
	public String getDiameter() {
		return diameter;
	}
	public void setDiameter(String diameter) {
		this.diameter = diameter;
	}
	
	
	
	

}
