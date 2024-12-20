package com.shilen.app.workbench.model.ss;

public class CycleSensor {
	
	private int cycle_sensor_id;
	private String equipment;
	private String sensor;
	private String runtime;
	private String result;
	private int errors;
	private String state;
	private String times_polled;
	private String last_poll;
	private String end_cycle;
	private String start_cycle;
	
	private String mac_address;
	private String data_collection;
	
	public String getMac_address() {
		return mac_address;
	}
	public void setMac_address(String mac_address) {
		this.mac_address = mac_address;
	}
	public String getData_collection() {
		return data_collection;
	}
	public void setData_collection(String data_collection) {
		this.data_collection = data_collection;
	}

	
	public String getEnd_cycle() {
		return end_cycle;
	}
	public void setEnd_cycle(String end_cycle) {
		this.end_cycle = end_cycle;
	}
	public String getStart_cycle() {
		return start_cycle;
	}
	public void setStart_cycle(String start_cycle) {
		this.start_cycle = start_cycle;
	}
	public String getEquipment() {
		return equipment;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	public String getSensor() {
		return sensor;
	}
	public void setSensor(String sensor) {
		this.sensor = sensor;
	}
	public String getRuntime() {
		return runtime;
	}
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getErrors() {
		return errors;
	}
	public void setErrors(int errors) {
		this.errors = errors;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTimes_polled() {
		return times_polled;
	}
	public void setTimes_polled(String times_polled) {
		this.times_polled = times_polled;
	}
	public String getLast_poll() {
		return last_poll;
	}
	public void setLast_poll(String last_poll) {
		this.last_poll = last_poll;
	}
	public int getCycle_sensor_id() {
		return cycle_sensor_id;
	}
	public void setCycle_sensor_id(int cycle_sensor_id) {
		this.cycle_sensor_id = cycle_sensor_id;
	}
	
	

}
