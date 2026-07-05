package com.shilen.app.workbench.model.admin;

import java.util.ArrayList;
import java.util.List;

public class AdminUser {

	private int id;
	private String username;
	private String password;
	private String confirmPassword;
	private int enabled = 1;
	private String first_name;
	private String last_name;
	private String sms_email;
	private List<Integer> selectedRoleIds = new ArrayList<Integer>();
	private String roles;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getSms_email() {
		return sms_email;
	}

	public void setSms_email(String sms_email) {
		this.sms_email = sms_email;
	}

	public List<Integer> getSelectedRoleIds() {
		return selectedRoleIds;
	}

	public void setSelectedRoleIds(List<Integer> selectedRoleIds) {
		this.selectedRoleIds = selectedRoleIds;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
}
