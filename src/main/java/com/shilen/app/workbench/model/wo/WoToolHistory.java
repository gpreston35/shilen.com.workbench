package com.shilen.app.workbench.model.wo;

public class WoToolHistory {
	
	private int id;
	private int woid;
	private String tool_identifier;
	private String tool_type;
	private String updated;
	private String updated_by;
	
	
	public WoToolHistory ( int woid, String tool_identifier, String tool_type) {
		
		this.woid = woid;
		this.tool_identifier = tool_identifier;
		this.tool_type = tool_type;
		
	}
	
	public int getId() {
		return id;
	}

	public int getWoid() {
		return woid;
	}

	public void setWoid(int woid) {
		this.woid = woid;
	}

	public String getTool_identifier() {
		return tool_identifier;
	}
	public String getTool_type() {
		return tool_type;
	}
	public String getUpdated() {
		return updated;
	}
	public String getUpdated_by() {
		return updated_by;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setTool_identifier(String tool_identifier) {
		this.tool_identifier = tool_identifier;
	}
	public void setTool_type(String tool_type) {
		this.tool_type = tool_type;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	
	

}
