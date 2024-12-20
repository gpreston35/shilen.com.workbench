package com.shilen.app.workbench.model.tool;

public class ToolNote {
	
	
	private int id;
	private String tool_type;
	private String note;
	private String created;
	private String created_by;
	private int tool_id;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public int getTool_id() {
		return tool_id;
	}
	public void setTool_id(int tool_id) {
		this.tool_id = tool_id;
	}
	public String getTool_type() {
		return tool_type;
	}
	public void setTool_type(String tool_type) {
		this.tool_type = tool_type;
	}



}
