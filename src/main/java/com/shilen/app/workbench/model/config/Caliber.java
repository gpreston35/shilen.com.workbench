package com.shilen.app.workbench.model.config;

import java.math.BigDecimal;
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
	
	
	private BigDecimal bore_reamer_high;
	private BigDecimal bore_reamer_low;
	private BigDecimal button_low;
	private BigDecimal button_high;
	private BigDecimal drill_low;
	private BigDecimal drill_high;
	
	private BigDecimal grv_nominal;
	private BigDecimal grv_utol;
	private BigDecimal grv_ltol;
	private BigDecimal grv_target;
	
	private BigDecimal bor_nominal;
	private BigDecimal bor_utol;
	private BigDecimal bor_ltol;
	private BigDecimal bor_target;
		
	
	private String status_text;
	
	private String note;
	private List<Note> notes = new ArrayList<Note>(); 
	private List<FileMeta> files = new ArrayList<FileMeta>();
	
	
	public int getId() {
		return id;
	}
	public int getStatus_id() {
		return status_id;
	}
	public String getCaliber() {
		return caliber;
	}
	public String getDescription() {
		return description;
	}
	public String getUpdated_by() {
		return updated_by;
	}
	public String getCreated_by() {
		return created_by;
	}
	public String getCreated() {
		return created;
	}
	public String getUpdated() {
		return updated;
	}
	public BigDecimal getBore_reamer_high() {
		return bore_reamer_high;
	}
	public BigDecimal getBore_reamer_low() {
		return bore_reamer_low;
	}
	public BigDecimal getButton_low() {
		return button_low;
	}
	public BigDecimal getButton_high() {
		return button_high;
	}
	public BigDecimal getDrill_low() {
		return drill_low;
	}
	public BigDecimal getDrill_high() {
		return drill_high;
	}
	public BigDecimal getGrv_nominal() {
		return grv_nominal;
	}
	public BigDecimal getGrv_utol() {
		return grv_utol;
	}
	public BigDecimal getGrv_ltol() {
		return grv_ltol;
	}
	public BigDecimal getGrv_target() {
		return grv_target;
	}
	public BigDecimal getBor_nominal() {
		return bor_nominal;
	}
	public BigDecimal getBor_utol() {
		return bor_utol;
	}
	public BigDecimal getBor_ltol() {
		return bor_ltol;
	}
	public BigDecimal getBor_target() {
		return bor_target;
	}
	public String getStatus_text() {
		return status_text;
	}
	public String getNote() {
		return note;
	}
	public List<Note> getNotes() {
		return notes;
	}
	public List<FileMeta> getFiles() {
		return files;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}
	public void setCaliber(String caliber) {
		this.caliber = caliber;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public void setBore_reamer_high(BigDecimal bore_reamer_high) {
		this.bore_reamer_high = bore_reamer_high;
	}
	public void setBore_reamer_low(BigDecimal bore_reamer_low) {
		this.bore_reamer_low = bore_reamer_low;
	}
	public void setButton_low(BigDecimal button_low) {
		this.button_low = button_low;
	}
	public void setButton_high(BigDecimal button_high) {
		this.button_high = button_high;
	}
	public void setDrill_low(BigDecimal drill_low) {
		this.drill_low = drill_low;
	}
	public void setDrill_high(BigDecimal drill_high) {
		this.drill_high = drill_high;
	}
	public void setGrv_nominal(BigDecimal grv_nominal) {
		this.grv_nominal = grv_nominal;
	}
	public void setGrv_utol(BigDecimal grv_utol) {
		this.grv_utol = grv_utol;
	}
	public void setGrv_ltol(BigDecimal grv_ltol) {
		this.grv_ltol = grv_ltol;
	}
	public void setGrv_target(BigDecimal grv_target) {
		this.grv_target = grv_target;
	}
	public void setBor_nominal(BigDecimal bor_nominal) {
		this.bor_nominal = bor_nominal;
	}
	public void setBor_utol(BigDecimal bor_utol) {
		this.bor_utol = bor_utol;
	}
	public void setBor_ltol(BigDecimal bor_ltol) {
		this.bor_ltol = bor_ltol;
	}
	public void setBor_target(BigDecimal bor_target) {
		this.bor_target = bor_target;
	}
	public void setStatus_text(String status_text) {
		this.status_text = status_text;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
	public void setFiles(List<FileMeta> files) {
		this.files = files;
	}
	
	
	
}
	
	