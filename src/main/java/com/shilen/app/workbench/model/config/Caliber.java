package com.shilen.app.workbench.model.config;

import java.util.ArrayList;
import java.util.List;

import com.shilen.app.workbench.model.FileMeta;
import com.shilen.app.workbench.model.Note;

public class Caliber {
	
	private int id;
	private int status_id;	
	private String caliber;
	private String description;
	private String updated_by;
	private String created_by;
	private String created;
	private String updated;
	
	
	private float bore_reamer_high;
	private float bore_reamer_low;
	private float button_low;
	private float button_high;
	private float drill_low;
	private float drill_high;
	
	private float grv_nominal;
	private float grv_utol;
	private float grv_ltol;
	private float grv_target;
	
	private float bor_nominal;
	private float bor_utol;
	private float bor_ltol;
	private float bor_target;
		
	
	private String status_text;
	
	public float getGrv_nominal() {
		return grv_nominal;
	}
	public float getGrv_utol() {
		return grv_utol;
	}
	public float getGrv_ltol() {
		return grv_ltol;
	}
	public float getGrv_target() {
		return grv_target;
	}
	public float getBor_nominal() {
		return bor_nominal;
	}
	public float getBor_utol() {
		return bor_utol;
	}
	public float getBor_ltol() {
		return bor_ltol;
	}
	public float getBor_target() {
		return bor_target;
	}
	public void setGrv_nominal(float grv_nominal) {
		this.grv_nominal = grv_nominal;
	}
	public void setGrv_utol(float grv_utol) {
		this.grv_utol = grv_utol;
	}
	public void setGrv_ltol(float grv_ltol) {
		this.grv_ltol = grv_ltol;
	}
	public void setGrv_target(float grv_target) {
		this.grv_target = grv_target;
	}
	public void setBor_nominal(float bor_nominal) {
		this.bor_nominal = bor_nominal;
	}
	public void setBor_utol(float bor_utol) {
		this.bor_utol = bor_utol;
	}
	public void setBor_ltol(float bor_ltol) {
		this.bor_ltol = bor_ltol;
	}
	public void setBor_target(float bor_target) {
		this.bor_target = bor_target;
	}

	
	public float getBore_reamer_high() {
		return bore_reamer_high;
	}
	public float getBore_reamer_low() {
		return bore_reamer_low;
	}
	public float getButton_low() {
		return button_low;
	}
	public float getButton_high() {
		return button_high;
	}
	public float getDrill_low() {
		return drill_low;
	}
	public float getDrill_high() {
		return drill_high;
	}
	public void setBore_reamer_high(float bore_reamer_high) {
		this.bore_reamer_high = bore_reamer_high;
	}
	public void setBore_reamer_low(float bore_reamer_low) {
		this.bore_reamer_low = bore_reamer_low;
	}
	public void setButton_low(float button_low) {
		this.button_low = button_low;
	}
	public void setButton_high(float button_high) {
		this.button_high = button_high;
	}
	public void setDrill_low(float drill_low) {
		this.drill_low = drill_low;
	}
	public void setDrill_high(float drill_high) {
		this.drill_high = drill_high;
	}

	
//	private String tool_range_low_str;
//	private String tool_range_high_str;
	
	
	
	private String note;
	private List<Note> notes = new ArrayList<Note>(); 
	private List<FileMeta> files = new ArrayList<FileMeta>();
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCaliber() {
		return caliber;
	}
	public void setCaliber(String caliber) {
		this.caliber = caliber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus_id() {
		return status_id;
	}
	public void setStatus_id(int status_id) {
		this.status_id = status_id;
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public List<Note> getNotes() {
		return notes;
	}
	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
	public List<FileMeta> getFiles() {
		return files;
	}
	public void setFiles(List<FileMeta> files) {
		this.files = files;
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
	public String getStatus_text() {
		return status_text;
	}
	public void setStatus_text(String status_text) {
		this.status_text = status_text;
	}
	

}
