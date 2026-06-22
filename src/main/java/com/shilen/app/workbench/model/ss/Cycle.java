package com.shilen.app.workbench.model.ss;

import java.util.ArrayList;
import java.util.List;

import com.shilen.app.workbench.model.User;

public class Cycle {
	
	private Integer equipment_id;
	private Integer poll_frequency;
	private Integer number_of_barrels;
	private Integer cycle_id;
	private String profile;
	private String equipment_name;
	private String updated_dt;

	private List<Alert> alerts = new ArrayList<Alert>();
	private List<User> users = new ArrayList<User>();
	
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public Integer getEquipment_id() {
		return equipment_id;
	}
	public void setEquipment_id(Integer equipment_id) {
		this.equipment_id = equipment_id;
	}
	public List<Alert> getAlerts() {
		return alerts;
	}
	public void setAlerts(List<Alert> alerts) {
		this.alerts = alerts;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getEquipment_name() {
		return equipment_name;
	}
	public void setEquipment_name(String equipment_name) {
		this.equipment_name = equipment_name;
	}
	public Integer getNumber_of_barrels() {
		return number_of_barrels;
	}
	public void setNumber_of_barrels(Integer number_of_barrels) {
		this.number_of_barrels = number_of_barrels;
	}
	public Integer getCycle_id() {
		return cycle_id;
	}
	public void setCycle_id(Integer cycle_id) {
		this.cycle_id = cycle_id;
	}
	public Integer getPoll_frequency() {
		return poll_frequency;
	}
	public void setPoll_frequency(Integer poll_frequency) {
		this.poll_frequency = poll_frequency;
	}
	public String getUpdated_dt() {
		return updated_dt;
	}
	public void setUpdated_dt(String updated_dt) {
		this.updated_dt = updated_dt;
	}

}
