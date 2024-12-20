package com.shilen.app.workbench.model.ss;

public class Alert {
	
	private int cycle_id = 0;
	private String type;
	private String notification_type;
	private String notified_date;

	private long value;
	private int alert_id;
	private String notified;
	private String description;
	private int selected = 1;

	
	private String recipients;
	private String recipient;
	
	public Alert() {}
	
	public Alert( String type, String notification_type, String description, long value ) {
		
		this.type = type;
		this.notification_type = notification_type;
		this.description = description;
		this.value = value;
	}
	
	
	public int getAlert_id() {
		return alert_id;
	}
	public void setAlert_id(int alert_id) {
		this.alert_id = alert_id;
	}
	public String getNotified() {
		return notified;
	}
	public void setNotified(String notified) {
		this.notified = notified;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public int getCycle_id() {
		return cycle_id;
	}
	public void setCycle_id(int cycle_id) {
		this.cycle_id = cycle_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNotification_type() {
		return notification_type;
	}
	public void setNotification_type(String notification_type) {
		this.notification_type = notification_type;
	}

	public long getValue() {
		return value;
	}
	public void setValue(long value) {
		this.value = value;
	}
	public String getRecipients() {
		return recipients;
	}
	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}



	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public String getNotified_date() {
		return notified_date;
	}


	public void setNotified_date(String notified_date) {
		this.notified_date = notified_date;
	}

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}


	
}
