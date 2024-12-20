package com.shilen.app.workbench.model.tool;

import java.util.ArrayList;
import java.util.List;

//import javax.validation.constraints.Max;
//import javax.validation.constraints.Min;

import com.shilen.app.workbench.model.FileMeta;
import com.shilen.app.workbench.model.Note;




public class ChamberReamer {
	
	private int id;
	// @NotEmpty(message="Tool Identifier is a mandatory field")
	private String tool_identifier;
	private int status_id;	
	private String manufacturer;
	private java.sql.Date dom;
	private java.sql.Date dos;
	private java.sql.Date eos;
	private String eos_str;
	private String dos_str;
	private String dom_str;
	private String mfg_print_id;
	private String mfg_serial_number;
	private String material;
	private String eos_reason;
	private String storage_location;
	private String created;
	private String updated;
	private String updated_by;
	private String created_by;
	private String drawing_number;
	private String chamber_name;
	private String note;
	private int sharpen_count;
	private List<Note> notes = new ArrayList<Note>(); 
	private List<FileMeta> files = new ArrayList<FileMeta>();
	
	
	// values 
	private String status;
	
	//@Min(2)
	//@Max(8)
	private int flute_count;

	private String pilot_type;
	private int resharp_count;
	private java.sql.Date resharp_date;
	private int chambers_cut;
	
	public String getEos_str() {
		return eos_str;
	}
	public void setEos_str(String eos_str) {
		this.eos_str = eos_str;
	}
	public String getDos_str() {
		return dos_str;
	}
	public void setDos_str(String dos_str) {
		this.dos_str = dos_str;
	}
	public String getDom_str() {
		return dom_str;
	}
	public void setDom_str(String dom_str) {
		this.dom_str = dom_str;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTool_identifier() {
		return tool_identifier;
	}
	public void setTool_identifier(String tool_identifier) {
		this.tool_identifier = tool_identifier;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public java.sql.Date getDom() {
		return dom;
	}
	public void setDom(java.sql.Date dom) {
		this.dom = dom;
	}
	public String getMfg_print_id() {
		return mfg_print_id;
	}
	public void setMfg_print_id(String mfg_print_id) {
		this.mfg_print_id = mfg_print_id;
	}
	public String getMfg_serial_number() {
		return mfg_serial_number;
	}
	public void setMfg_serial_number(String mfg_serial_number) {
		this.mfg_serial_number = mfg_serial_number;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public java.sql.Date getDos() {
		return dos;
	}
	public void setDos(java.sql.Date dos) {
		this.dos = dos;
	}
	public java.sql.Date getEos() {
		return eos;
	}
	public void setEos(java.sql.Date eos) {
		this.eos = eos;
	}
	public String getEos_reason() {
		return eos_reason;
	}
	public void setEos_reason(String eos_reason) {
		this.eos_reason = eos_reason;
	}
	public String getStorage_location() {
		return storage_location;
	}
	public void setStorage_location(String storage_location) {
		this.storage_location = storage_location;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public String getUpdated_by() {
		return updated_by;
	}
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public String getDrawing_number() {
		return drawing_number;
	}
	public void setDrawing_number(String drawing_number) {
		this.drawing_number = drawing_number;
	}
	public String getChamber_name() {
		return chamber_name;
	}
	public void setChamber_name(String chamber_name) {
		this.chamber_name = chamber_name;
	}
	public String getPilot_type() {
		return pilot_type;
	}
	public void setPilot_type(String pilot_type) {
		this.pilot_type = pilot_type;
	}
	public int getResharp_count() {
		return resharp_count;
	}
	public void setResharp_count(int resharp_count) {
		this.resharp_count = resharp_count;
	}
	public java.sql.Date getResharp_date() {
		return resharp_date;
	}
	public void setResharp_date(java.sql.Date resharp_date) {
		this.resharp_date = resharp_date;
	}
	public int getChambers_cut() {
		return chambers_cut;
	}
	public void setChambers_cut(int chambers_cut) {
		this.chambers_cut = chambers_cut;
	}
	public int getFlute_count() {
		return flute_count;
	}
	public void setFlute_count(int flute_count) {
		this.flute_count = flute_count;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note ) {
		this.note = note;
	}

	public int getStatus_id() {
		return status_id;
	}
	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getSharpen_count() {
		return sharpen_count;
	}
	public void setSharpen_count(int sharpen_count) {
		this.sharpen_count = sharpen_count;
	}
	public List<FileMeta> getFiles() {
		return files;
	}
	public void setFiles(List<FileMeta> files) {
		this.files = files;
	}
	public List<Note> getNotes() {
		return notes;
	}
	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}	
	
	
	
	

}
