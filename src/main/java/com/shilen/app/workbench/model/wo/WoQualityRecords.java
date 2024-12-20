package com.shilen.app.workbench.model.wo;

import java.util.ArrayList;
import java.util.List;

public class WoQualityRecords {
	
	
	public WoQualityRecords( List<WoQuality> records) {
		this.quality_records = records;
	}
	
	
	private List<WoQuality> quality_records = new ArrayList<WoQuality>();

	public List<WoQuality> getQuality_records() {
		return quality_records;
	}

	public void setQuality_records(List<WoQuality> quality_records) {
		this.quality_records = quality_records;
	}
	

}
