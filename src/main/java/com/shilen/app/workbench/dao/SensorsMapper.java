package com.shilen.app.workbench.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shilen.app.workbench.model.ss.Alert;
import com.shilen.app.workbench.model.ss.Cycle;
import com.shilen.app.workbench.model.ss.CycleSensor;
import com.shilen.app.workbench.model.ss.Equipment;
import com.shilen.app.workbench.model.ss.GraphCycle;
import com.shilen.app.workbench.model.ss.AlertType;
import com.shilen.app.workbench.model.ss.Profile;
import com.shilen.app.workbench.model.ss.Sample;
import com.shilen.app.workbench.model.ss.Sensor;


public interface SensorsMapper {
	

	
	@Select ("SELECT e.name equipment, concat('S',s.sensor_id) sensor, SEC_TO_TIME(TIMESTAMPDIFF(SECOND, cs.start_cycle, NOW())) runtime," + 
			"       cs.result, cs.errors, cs.state, cs.times_polled, cs.last_poll, cs.cycle_sensor_id, cs.cycle_id, " + 
			"       CASE WHEN c.profile IS NULL OR TRIM(c.profile) = '' THEN 'N' ELSE 'Y' END AS profile_maintained, " +
			"       CASE WHEN c.number_of_barrels IS NULL OR c.number_of_barrels <= 0 THEN 'N' ELSE 'Y' END AS barrels_maintained " +
			"   FROM sensors.cycle_sensor cs, " + 
			"        sensors.sensor s, " + 
			"        sensors.equipment e, " +
			"        sensors.cycle c " + 
			"where " + 
			"	cs.sensor_id = s.sensor_id " + 
			"    AND s.equipment_id = e.equipment_id " + 
			"    AND cs.cycle_id = c.cycle_id " +
			"	 AND UPPER(cs.state) IN ('RUNNING','ACTIVE')")
	List<CycleSensor> getActive();

	@Select ("SELECT e.name equipment, concat('S',s.sensor_id) sensor, SEC_TO_TIME(TIMESTAMPDIFF(SECOND, cs.start_cycle, IFNULL(cs.end_cycle, NOW()))) runtime," +
			"       cs.result, cs.errors, cs.state, cs.times_polled, cs.last_poll, cs.cycle_sensor_id, cs.cycle_id, " +
			"       CASE WHEN c.profile IS NULL OR TRIM(c.profile) = '' THEN 'N' ELSE 'Y' END AS profile_maintained, " +
			"       CASE WHEN c.number_of_barrels IS NULL OR c.number_of_barrels <= 0 THEN 'N' ELSE 'Y' END AS barrels_maintained " +
			"  FROM sensors.cycle_sensor cs, " +
			"       sensors.sensor s, " +
			"       sensors.equipment e, " +
			"       sensors.cycle c " +
			" WHERE cs.sensor_id = s.sensor_id " +
			"   AND s.equipment_id = e.equipment_id " +
			"   AND cs.cycle_id = c.cycle_id " +
			"   AND UPPER(IFNULL(cs.state,'')) = 'COMPLETE' " +
			"   AND IFNULL(cs.end_cycle, cs.last_poll) >= DATE_SUB(NOW(), INTERVAL 7 DAY) " +
			" ORDER BY IFNULL(cs.end_cycle, cs.last_poll) DESC")
	List<CycleSensor> getCompletedLast7Days();
	
	@Select ("SELECT sample, ts FROM sensors.sample where cycle_sensor_id = #{id} order by ts")
	List<Sample> getSamples(int id);
	
	@Select ("select date_format(s1.timeblock,'%m/%d %k:%i') timeblock,  s1.zone_zero, s2.zone_one, s2.zone_two, s2.zone_three from " + 
			"( select " + 
			"	FROM_UNIXTIME(ceiling(unix_timestamp(ts)/300)*300) as timeblock, avg(s.sample) zone_zero " + 
			"    from sensors.sample s " + 
			"    where s.cycle_sensor_id = #{cycle_sensor_id} " + 
			"    group by FROM_UNIXTIME(ceiling(unix_timestamp(ts)/300)*300) ) s1 " + 
			"    left join ( select " + 
			"	FROM_UNIXTIME(ceiling(unix_timestamp(ts)/300)*300) as timeblock, avg(zone_one_temparture) zone_one, " + 
			"    avg(zone_two_temparture) zone_two ,avg(zone_three_temparture) zone_three " + 
			"    from sensors.bartinst " + 
			"    where ts > #{start_date} and ts < #{end_date} and mac_address = #{mac_address} " + 
			"    group by timeblock ) s2 " + 
			"    on s1.timeblock = s2.timeblock")
	List<GraphCycle> graphCycle( int cycle_sensor_id, String start_date, String end_date, String mac_address );
	
	@Select("select cs.start_cycle, IFNULL(cs.end_cycle, now() ) end_cycle, IFNULL(e.mac_address,'') mac_address, IFNULL(e.data_collection,'N') data_collection " + 
			"   from sensors.cycle_sensor cs, sensors.sensor s, sensors.equipment e " + 
			"   where cs.sensor_id = s.sensor_id " + 
			"     and e.equipment_id = s.equipment_id " + 
			"     and cs.cycle_sensor_id = #{cycle_sensor_id}")
	CycleSensor getDateParameters( int cycle_sensor_id );
	
	@Select("SELECT a.cycle_id, a.type, a.notification_type, a.value, a.notified_date, a.result " +
			"  FROM sensors.alerts a " +
			" WHERE UPPER(IFNULL(a.enabled,'Y')) = 'Y' " +
			"   AND a.notified = 'X' " +
			"   AND EXISTS ( " +
			"       SELECT 1 FROM sensors.cycle_sensor cs " +
			"        WHERE cs.cycle_id = a.cycle_id " +
			"          AND UPPER(IFNULL(cs.state,'')) IN ('RUNNING','ACTIVE') " +
			"   ) " +
			" ORDER BY a.notified_date DESC, a.cycle_id, a.type")
	List<Alert> getAlerts();
	
	@Select("select cs.cycle_sensor_id, cs.state, cs.cycle_id, cs.sensor_id, cs.start_cycle, cs.end_cycle, cs.times_polled, cs.result, timediff(cs.end_cycle, cs.start_cycle) " + 
			"from sensors.cycle_sensor cs, " + 
			"     sensors.cycle c, " + 
			"     sensors.sensor s " + 
			"where cs.sensor_id = s.sensor_id " + 
			"  and cs.cycle_id = c.cycle_id " + 
			"  and cs.cycle_id = #{cycle_id}")
	List<CycleSensor> getCycleSensors( int cycle_id );
	
	@Select("select c.equipment_id, c.profile, c.poll_frequency, e.name as equipment_name, c.cycle_id, c.number_of_barrels, c.updated_dt from " + 
			"		   sensors.cycle c, sensors.equipment e " + 
			"	where " + 
			"       c.equipment_id = e.equipment_id\n" + 
			"       and c.cycle_id = #{cycle_id} ")
    Cycle getCycle( int cycle_id );
	
	@Select("SELECT * FROM sensors.alerts where cycle_id = #{cycle_id}")
	List<Alert> getCycleAlerts( int cycle_id );
	
	@Select("SELECT user_id, recipient, IFNULL(enabled,'Y') enabled FROM sensors.alert_recipients where cycle_id = #{cycle_id}")
	List<Alert> getCycleAlertRecipient( int cycle_id );
	
	@Select("SELECT * from sensors.equipment")
	List<Equipment> getEquipment();
	
	@Select("SELECT * from sensors.equipment where equipment_id = #{id}")
	Equipment getEquipmentByID(int id);
	
	@Insert("INSERT into sensors.equipment ( name, description, data_collection, mac_address, active ) values ( "
			+ "#{name}, #{description}, #{data_collection}, #{mac_address}, #{active}) ")
	void insertEquipment( Equipment equipment );
	
	@Update("Update sensors.equipment set name= #{name}, description = #{description}, data_collection = #{data_collection}, mac_address = #{mac_address}, "
			+ " active = #{active} where equipment_id = #{equipment_id} ")
	void updateEquipment( Equipment equipment );	
	
	@Select("SELECT * FROM sensors.sensor")
	List<Sensor> getSensor();

	@Select("SELECT s.*, " +
			"CASE " +
			"  WHEN EXISTS ( " +
			"    SELECT 1 FROM sensors.cycle_sensor cs " +
			"    WHERE cs.sensor_id = s.sensor_id " +
			"      AND (UPPER(IFNULL(cs.state,'')) IN ('ACTIVE','RUNNING') " +
			"           OR (cs.end_cycle IS NULL AND UPPER(IFNULL(cs.state,'')) <> 'COMPLETE'))" +
			"  ) THEN 'RUNNING' " +
			"  ELSE 'IDLE' " +
			"END AS cycle_state " +
			"FROM sensors.sensor s")
	List<Sensor> getDashboardSensors();
	
	@Insert("INSERT into sensors.sensor ( equipment_id, ip_address, port, check_cmd, adapter, adapter_parameter, threshold_temp, name, external, mac_address, description, active ) "
			+ " value( #{equipment_id}, #{ip_address}, #{port}, #{adapter_parameter}, #{adapter}, #{adapter_parameter}, #{threshold_temp}, #{name}, #{external}, #{mac_address}, #{description}, #{active} )" )
	void insertSensor( Sensor sensor );
	
	@Update("UPDATE sensors.sensor set equipment_id = #{equipment_id}, ip_address = #{ip_address}, port = #{port}, check_cmd = #{adapter_parameter}, adapter = #{adapter}, adapter_parameter = #{adapter_parameter}, threshold_temp = #{threshold_temp}, "
			+ "name = #{name}, external = #{external}, mac_address = #{mac_address}, description = #{description}, active = #{active} where sensor_id = #{sensor_id}")
	void updateSensor( Sensor sensor );
	
	@Select("SELECT * from sensors.sensor where sensor_id = #{id}")
	Sensor getSensorById(int id);

	@Select("SELECT * FROM sensors.profile")
	List<Profile> getProfile();

	@Select("SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'sensors' AND table_name = 'alert_type'")
	int alertTypeTableExists();

	@Select("SELECT * FROM sensors.alert_type ORDER BY sort_order, alert_type")
	List<AlertType> getAlertType();

	@Select("SELECT * FROM sensors.alert_type WHERE alert_type_id = #{id}")
	AlertType getAlertTypeById(int id);

	@Insert("INSERT INTO sensors.alert_type (alert_type, alert_description, alert_default_value, notification_type, default_enabled, unit, sort_order) "
			+ "VALUES (#{alert_type}, #{alert_description}, #{alert_default_value}, #{notification_type}, #{default_enabled}, #{unit}, #{sort_order})")
	void insertAlertType(AlertType alertType);

	@Update("UPDATE sensors.alert_type SET alert_type = #{alert_type}, alert_description = #{alert_description}, "
			+ "alert_default_value = #{alert_default_value}, notification_type = #{notification_type}, default_enabled = #{default_enabled}, "
			+ "unit = #{unit}, sort_order = #{sort_order} WHERE alert_type_id = #{alert_type_id}")
	void updateAlertType(AlertType alertType);

	@Delete("DELETE FROM sensors.alert_type WHERE alert_type_id = #{id}")
	void deleteAlertType(int id);

	@Select("SELECT "
			+ " alert_type AS type, "
			+ " notification_type, "
			+ " alert_description AS description, "
			+ " alert_default_value AS value, "
			+ " default_enabled AS enabled "
			+ "FROM sensors.alert_type "
			+ "ORDER BY sort_order, alert_type")
	List<Alert> getAlertTypeDefaults();

	@Select("SELECT * FROM sensors.profile WHERE profile_id = #{id}")
	Profile getProfileById(int id);

	@Insert("INSERT INTO sensors.profile (name, description) VALUES (#{name}, #{description})")
	void insertProfile(Profile profile);

	@Update("UPDATE sensors.profile SET name = #{name}, description = #{description} WHERE profile_id = #{profile_id}")
	void updateProfile(Profile profile);

	@Update("DELETE FROM sensors.profile WHERE profile_id = #{id}")
	void deleteProfile(int id);
	
	
	@Insert("INSERT into sensors.cycle ( equipment_id, profile, poll_frequency, number_of_barrels, created ) values ( "
			+ " #{equipment_id}, #{profile}, #{poll_frequency}, #{number_of_barrels}, NOW() )")
	@Options(useGeneratedKeys = true, keyProperty="cycle_id", keyColumn="cycle_id") 
	void insertCycle( Cycle cycle);

	@Select("SELECT sensor_id FROM sensors.sensor " +
			"WHERE equipment_id = #{equipmentId} " +
			"  AND UPPER(IFNULL(active,'Y')) = 'Y' " +
			"ORDER BY sensor_id " +
			"LIMIT 1")
	Integer findPrimaryActiveSensorIdForEquipment(@Param("equipmentId") int equipmentId);

	@Insert("INSERT INTO sensors.cycle_sensor (cycle_id, sensor_id, start_cycle, last_poll, times_polled, errors, state) " +
			"VALUES (#{cycleId}, #{sensorId}, NOW(), NOW(), 0, 0, 'Active')")
	void insertCycleSensor(@Param("cycleId") int cycleId, @Param("sensorId") int sensorId);

	@Update("UPDATE sensors.cycle SET profile = #{profile}, number_of_barrels = #{number_of_barrels}, updated_dt = NOW() WHERE cycle_id = #{cycle_id}")
	void updateCycleMaintenance(Cycle cycle);
	
	@Insert("INSERT into sensors.alerts ( cycle_id, type, notification_type, enabled, value, created ) values ( #{cycle_id}, #{type}, #{notification_type}, #{enabled}, #{value}, NOW() )")
	void insertAlert( Alert alert );

	@Delete("DELETE FROM sensors.alerts WHERE cycle_id = #{cycleId}")
	void deleteCycleAlerts(@Param("cycleId") int cycleId);

	@Delete("DELETE FROM sensors.alert_recipients WHERE cycle_id = #{cycleId}")
	void deleteCycleAlertRecipients(@Param("cycleId") int cycleId);

	@Insert("INSERT INTO sensors.alert_recipients (cycle_id, user_id, recipient, enabled) VALUES (#{cycleId}, #{userId}, #{recipient}, #{enabled})")
	void insertAlertRecipient(@Param("cycleId") int cycleId, @Param("userId") Integer userId, @Param("recipient") String recipient, @Param("enabled") String enabled);
	

}
