package com.shilen.app.workbench.model.tool;

import java.math.BigDecimal;

public class ToolSearchForm {
	

	private String search_term;
	private String style;
	private Integer flute_count;
	private Integer rifling_count;
	private String rifling_type;
	private BigDecimal diameter_from;
	private BigDecimal diameter_to;

	public String getSearch_term() {
		return search_term;
	}

	public void setSearch_term(String search_term) {
		this.search_term = search_term;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Integer getFlute_count() {
		return flute_count;
	}

	public void setFlute_count(Integer flute_count) {
		this.flute_count = flute_count;
	}

	public Integer getRifling_count() {
		return rifling_count;
	}

	public void setRifling_count(Integer rifling_count) {
		this.rifling_count = rifling_count;
	}

	public String getRifling_type() {
		return rifling_type;
	}

	public void setRifling_type(String rifling_type) {
		this.rifling_type = rifling_type;
	}

	public BigDecimal getDiameter_from() {
		return diameter_from;
	}

	public void setDiameter_from(BigDecimal diameter_from) {
		this.diameter_from = diameter_from;
	}

	public BigDecimal getDiameter_to() {
		return diameter_to;
	}

	public void setDiameter_to(BigDecimal diameter_to) {
		this.diameter_to = diameter_to;
	}
}
