package com.shilen.app.workbench.model.ss;

public class Sensor {
	
	private int sensor_id;
	private int equipment_id;
	private String ip_address;
	private int port;
	private String check_cmd;
	private String admin_url;
	private String name;
	private String equipment;
	private String external;
	private String mac_address;
	private String description;
	private String active;
	
	
	public String getExternal() {
		return external;
	}
	public void setExternal(String external) {
		this.external = external;
	}
	public String getEquipment() {
		return equipment;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	public int getSensor_id() {
		return sensor_id;
	}
	public void setSensor_id(int sensor_id) {
		this.sensor_id = sensor_id;
	}
	public int getEquipment_id() {
		return equipment_id;
	}
	public void setEquipment_id(int equipment_id) {
		this.equipment_id = equipment_id;
	}
	public String getIp_address() {
		return ip_address;
	}
	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getCheck_cmd() {
		return check_cmd;
	}
	public void setCheck_cmd(String check_cmd) {
		this.check_cmd = check_cmd;
	}
	public String getAdmin_url() {
		return admin_url;
	}
	public void setAdmin_url(String admin_url) {
		this.admin_url = admin_url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMac_address() {
		return mac_address;
	}
	public void setMac_address(String mac_address) {
		this.mac_address = mac_address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
	
	

}
