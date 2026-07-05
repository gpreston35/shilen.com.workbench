package com.shilen.app.workbench.model.ss;

public class AlertType {

	private int alert_type_id;
	private String alert_type;
	private String alert_description;
	private double alert_default_value;
	private String notification_type;
	private String default_enabled;
	private String unit;
	private int sort_order;

	public int getAlert_type_id() {
		return alert_type_id;
	}

	public void setAlert_type_id(int alert_type_id) {
		this.alert_type_id = alert_type_id;
	}

	public String getAlert_type() {
		return alert_type;
	}

	public void setAlert_type(String alert_type) {
		this.alert_type = alert_type;
	}

	public String getAlert_description() {
		return alert_description;
	}

	public void setAlert_description(String alert_description) {
		this.alert_description = alert_description;
	}

	public double getAlert_default_value() {
		return alert_default_value;
	}

	public void setAlert_default_value(double alert_default_value) {
		this.alert_default_value = alert_default_value;
	}

	public String getNotification_type() {
		return notification_type;
	}

	public void setNotification_type(String notification_type) {
		this.notification_type = notification_type;
	}

	public String getDefault_enabled() {
		return default_enabled;
	}

	public void setDefault_enabled(String default_enabled) {
		this.default_enabled = default_enabled;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getSort_order() {
		return sort_order;
	}

	public void setSort_order(int sort_order) {
		this.sort_order = sort_order;
	}
}
