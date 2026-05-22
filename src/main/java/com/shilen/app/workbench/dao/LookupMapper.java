package com.shilen.app.workbench.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.shilen.app.workbench.model.PickList;
import com.shilen.app.workbench.model.User;

public interface LookupMapper {
	
	@Select("SELECT id, process value FROM operations.lk_process")
	List<PickList> getLkProcess();
	
//	@Select("SELECT id, reason value FROM operations.scrap_reasons")
//	List<PickList> getLkScrapReasons();
	
	@Select("SELECT id, length value FROM operations.lk_barrel_length order by length")
	List<PickList> getLkBarrelLength();
	
	@Select("SELECT equipment_id id, name value FROM sensors.equipment")
	List<PickList> getEquipment();
	
	@Select("SELECT first_name, last_name, sms_email FROM operations.user")
	List<User> getSensorUsers();
	
	@Select("SELECT id, spindle value FROM operations.lk_spindle order by spindle")
	List<PickList> getSpindles();
	
	@Select("SELECT id, heat_name value FROM operations.steel order by heat_name")
	List<PickList> getSteel();
	
	@Select("SELECT id, operator value FROM lk_operators order by operator")
	List<PickList> getOperators();
	
	@Select("SELECT id, caliber value FROM operations.caliber order by caliber")
	List<PickList> getCalibers();
	
	@Select("SELECT id, length value FROM lk_length order by length")
	List<PickList> getLengths();
	
	@Select("SELECT id, reason value FROM lk_scrapreason order by 2")
	List<PickList> getLkScrapReasons();
	
	
//	@Select ("SELECT status_value value, status_text text from operations.lk_status where dset = #{dset} and active = 1 "
//		 		+ "ORDER BY status_text")
	@Select ("SELECT id, status value from operations.lk_status order by 2")
	List<PickList> getStatuses();
	 
	
}
