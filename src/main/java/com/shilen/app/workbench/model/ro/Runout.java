package com.shilen.app.workbench.model.ro;

public class Runout {
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRun_date() {
		return run_date;
	}
	public void setRun_date(String run_date) {
		this.run_date = run_date;
	}
	public String getCaliber() {
		return caliber;
	}
	public void setCaliber(String caliber) {
		this.caliber = caliber;
	}

	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public float getMuzzle_tir() {
		return muzzle_tir;
	}
	public void setMuzzle_tir(float muzzle_tir) {
		this.muzzle_tir = muzzle_tir;
	}
	public float getChamber_tir() {
		return chamber_tir;
	}
	public void setChamber_tir(float chamber_tir) {
		this.chamber_tir = chamber_tir;
	}
	public String getHeat_name() {
		return heat_name;
	}
	public void setHeat_name(String heat_name) {
		this.heat_name = heat_name;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getRedrill() {
		return redrill;
	}
	public void setRedrill(String redrill) {
		this.redrill = redrill;
	}
	public String getScrap() {
		return scrap;
	}
	public void setScrap(String scrap) {
		this.scrap = scrap;
	}
	public String getWoid() {
		return woid;
	}
	public void setWoid(String woid) {
		this.woid = woid;
	}
	public String getScrap_reason() {
		return scrap_reason;
	}
	public void setScrap_reason(String scrap_reason) {
		this.scrap_reason = scrap_reason;
	}
	public int getCaliber_id() {
		return caliber_id;
	}
	public void setCaliber_id(int caliber_id) {
		this.caliber_id = caliber_id;
	}
	public int getSpindle_id() {
		return spindle_id;
	}
	public void setSpindle_id(int spindle_id) {
		this.spindle_id = spindle_id;
	}
	public int getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(int operator_id) {
		this.operator_id = operator_id;
	}
	public int getSteel_id() {
		return steel_id;
	}
	public void setSteel_id(int steel_id) {
		this.steel_id = steel_id;
	}
	
	public String getSpindle() {
		return spindle;
	}
	public void setSpindle(String spindle) {
		this.spindle = spindle;
	}

	public int getScrapreason_id() {
		return scrapreason_id;
	}
	public void setScrapreason_id(int scrapreason_id) {
		this.scrapreason_id = scrapreason_id;
	}

	public int getLength_id() {
		return length_id;
	}
	public void setLength_id(int length_id) {
		this.length_id = length_id;
	}

	public int getScrap_id() {
		return scrap_id;
	}
	public void setScrap_id(int scrap_id) {
		this.scrap_id = scrap_id;
	}

	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}

	private int id;
	private String run_date;
    private String caliber;
    private String operator;
	private float muzzle_tir;
	private float chamber_tir;
	private String heat_name;
	private int length;
	private String redrill;
	private String scrap;
	private String woid;
	private String scrap_reason;
	private String spindle;
	
	private int scrapreason_id;
	
	private int caliber_id;
	private int spindle_id;
	private int operator_id;
	private int steel_id;
	private int length_id;
	private int scrap_id;
	
	private String updated;
	private String update_dt;
	
	public String getDhtool() {
		return dhtool;
	}
	public String getBrtool() {
		return brtool;
	}
	public void setDhtool(String dhtool) {
		this.dhtool = dhtool;
	}
	public void setBrtool(String brtool) {
		this.brtool = brtool;
	}

	public String getUpdate_dt() {
		return update_dt;
	}
	public void setUpdate_dt(String update_dt) {
		this.update_dt = update_dt;
	}

	private String dhtool;
	private String brtool;
	

}
