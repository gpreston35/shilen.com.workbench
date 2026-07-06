package com.shilen.app.workbench.model.ag;

public class AirGaugeInspection {

	private Integer id;
	private String inspectiontype;
	private String woid;
	private String comment;
	private String created;
	private String custom1;
	private String custom2;
	private String serialNumber;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInspectiontype() {
		return inspectiontype;
	}

	public void setInspectiontype(String inspectiontype) {
		this.inspectiontype = inspectiontype;
	}

	public String getWoid() {
		return woid;
	}

	public void setWoid(String woid) {
		this.woid = woid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getCustom1() {
		return custom1;
	}

	public void setCustom1(String custom1) {
		this.custom1 = custom1;
	}

	public String getCustom2() {
		return custom2;
	}

	public void setCustom2(String custom2) {
		this.custom2 = custom2;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
}
