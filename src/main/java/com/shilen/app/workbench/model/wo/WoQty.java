package com.shilen.app.workbench.model.wo;

public class WoQty {
	
	private int id;
	private int woid;
	private int qty;
	private int length;
	private String action = "*";
	
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getWoid() {
		return woid;
	}
	public void setWoid(int woid) {
		this.woid = woid;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	
	

}
