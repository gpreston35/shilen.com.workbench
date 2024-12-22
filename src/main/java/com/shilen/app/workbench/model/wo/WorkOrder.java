package com.shilen.app.workbench.model.wo;

import java.util.ArrayList;
import java.util.List;

public class WorkOrder {
	
    private int id = 0;
    private String woid;
    
    List<WoQty> woqty = new ArrayList<WoQty>();
    List<WoNote> wonote = new ArrayList<WoNote>();
    List<WoToolHistory> wotoolhistory = new ArrayList<WoToolHistory>();
    
	public List<WoToolHistory> getWotoolhistory() {
		return wotoolhistory;
	}
	public void setWotoolhistory(List<WoToolHistory> wotoolhistory) {
		this.wotoolhistory = wotoolhistory;
	}
	
	private String steeltype_id;
    private int caliber_id;
    private String rifling_id;
    private int groove_id;
    private int twist_id;
    private int steel_id;
    private java.sql.Date created;

	private java.sql.Date prod_date;
    private int status_id;  
    
	private String caliber;
    private String twist;
    private String rifling;
    private String groove;
    private String screated;
    private String heat_name;
    
    private String bntool;
    private String brtool;
    private String dhtool;
    
    private String current_bntool;
    private String current_brtool;
    private String current_dhtool;
        
    private String note;
    
    public List<WoQty> getWoqty() {
		return woqty;
	}
	public void setWoqty(List<WoQty> woqty) {
		this.woqty = woqty;
	}
       
    public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
    public java.sql.Date getProd_date() {
		return prod_date;
	}
	public void setProd_date(java.sql.Date prod_date) {
		this.prod_date = prod_date;
	}
    public String getHeat_name() {
		return heat_name;
	}
	public void setHeat_name(String heat_name) {
		this.heat_name = heat_name;
	}
    public String getCaliber() {
		return caliber;
	}
	public void setCaliber(String caliber) {
		this.caliber = caliber;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWoid() {
		return woid;
	}
	public void setWoid(String woid) {
		this.woid = woid;
	}

	public int getCaliber_id() {
		return caliber_id;
	}
	public void setCaliber_id(int caliber_id) {
		this.caliber_id = caliber_id;
	}
	public String getRifling_id() {
		return rifling_id;
	}
	public void setRifling_id(String rifling_id) {
		this.rifling_id = rifling_id;
	}
	public int getGroove_id() {
		return groove_id;
	}
	public void setGroove_id(int groove_id) {
		this.groove_id = groove_id;
	}
	public int getTwist_id() {
		return twist_id;
	}
	public void setTwist_id(int twist_id) {
		this.twist_id = twist_id;
	}
	public int getSteel_id() {
		return steel_id;
	}
	public void setSteel_id(int steel_id) {
		this.steel_id = steel_id;
	}
	public java.sql.Date getCreated() {
		return created;
	}
	public void setCreated(java.sql.Date created) {
		this.created = created;
	}
	public int getStatus_id() {
		return status_id;
	}
	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}
	public String getTwist() {
		return twist;
	}
	public void setTwist(String twist) {
		this.twist = twist;
	}
	public String getRifling() {
		return rifling;
	}
	public void setRifling(String rifling) {
		this.rifling = rifling;
	}
	public String getGroove() {
		return groove;
	}
	public void setGroove(String groove) {
		this.groove = groove;
	}
	public String getScreated() {
		return screated;
	}
	public void setScreated(String screated) {
		this.screated = screated;
	}
	public String getSteeltype_id() {
		return steeltype_id;
	}
	public void setSteeltype_id(String steeltype_id) {
		this.steeltype_id = steeltype_id;
	}

    public List<WoNote> getWonote() {
		return wonote;
	}
	public void setWonote(List<WoNote> wonote) {
		this.wonote = wonote;
	}
	public String getBntool() {
		return bntool;
	}
	public String getBrtool() {
		return brtool;
	}
	public String getDhtool() {
		return dhtool;
	}
	public String getCurrent_bntool() {
		return current_bntool;
	}
	public String getCurrent_brtool() {
		return current_brtool;
	}
	public String getCurrent_dhtool() {
		return current_dhtool;
	}
	public void setBntool(String bntool) {
		this.bntool = bntool;
	}
	public void setBrtool(String brtool) {
		this.brtool = brtool;
	}
	public void setDhtool(String dhtool) {
		this.dhtool = dhtool;
	}
	public void setCurrent_bntool(String current_bntool) {
		this.current_bntool = current_bntool;
	}
	public void setCurrent_brtool(String current_brtool) {
		this.current_brtool = current_brtool;
	}
	public void setCurrent_dhtool(String current_dhtool) {
		this.current_dhtool = current_dhtool;
	}

}
