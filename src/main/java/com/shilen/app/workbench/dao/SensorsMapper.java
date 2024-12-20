package com.shilen.app.workbench.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;

import com.shilen.app.workbench.model.ss.Alert;
import com.shilen.app.workbench.model.ss.Cycle;
import com.shilen.app.workbench.model.ss.CycleSensor;
import com.shilen.app.workbench.model.ss.Equipment;
import com.shilen.app.workbench.model.ss.GraphCycle;
import com.shilen.app.workbench.model.ss.Sample;
import com.shilen.app.workbench.model.ss.Sensor;


public interface SensorsMapper {
	

	
	@Select ("SELECT e.name equipment, concat('S',s.sensor_id) sensor, SEC_TO_TIME(TIMESTAMPDIFF(SECOND,start_cycle, NOW())) runtime," + 
			"       result, errors, state, times_polled, last_poll, cs.cycle_sensor_id " + 
			"   FROM sensors.cycle_sensor cs, " + 
			"        sensors.sensor s, " + 
			"        sensors.equipment e " + 
			"where " + 
			"	cs.sensor_id = s.sensor_id " + 
			"    AND s.equipment_id = e.equipment_id " + 
			"	 AND state = 'RUNNING'")
	List<CycleSensor> getActive();
	
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
	
	@Select("select a.cycle_id, a.type, a.notification_type, a.value, group_concat( ar.recipient separator '; ') recipients from " + 
			"         sensors.alerts a " + 
			"         inner join " + 
			"         sensors.alert_recipients ar on a.cycle_id = ar.cycle_id " + 
			"         and a.cycle_id in ( select cycle_id from sensors.cycle_sensor where state = 'RUNNING') " + 
			"         and a.notified = 'X' " + 
			"         group by a.cycle_id, a.type, a.notification_type, a.notified_date, a.value")
	@Results(value = {
            @Result(property = "notified_date", column = "notified_date", typeHandler = LocalDateTimeTypeHandler.class, jdbcType = JdbcType.DATE),

    })
	List<Alert> getAlerts();
	
	@Select("select cs.cycle_sensor_id, cs.state, cs.cycle_id, cs.sensor_id, cs.start_cycle, cs.end_cycle, cs.times_polled, cs.result, timediff(cs.end_cycle, cs.start_cycle) " + 
			"from sensors.cycle_sensor cs, " + 
			"     sensors.cycle c, " + 
			"     sensors.sensor s " + 
			"where cs.sensor_id = s.sensor_id " + 
			"  and cs.cycle_id = c.cycle_id " + 
			"  and cs.cycle_id = #{cycle_id}")
	List<CycleSensor> getCycleSensors( int cycle_id );
	
	@Select("select c.profile, e.name, c.cycle_id, c.number_of_barrels from " + 
			"		   sensors.cycle c, sensors.equipment e " + 
			"	where " + 
			"       c.equipment_id = e.equipment_id\n" + 
			"       and c.cycle_id = #{cycle_id} ")
    Cycle getCycle( int cycle_id );
	
	@Select("SELECT * FROM sensors.alerts where cycle_id = #{cycle_id}")
	List<Alert> getCycleAlerts( int cycle_id );
	
	@Select("SELECT recipient FROM sensors.alert_recipients where cycle_id = #{cycle_id}")
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
	
	@Insert("INSERT into sensors.sensor ( equipment_id, ip_address, port, check_cmd, name, external, mac_address, description, active ) "
			+ " value( #{equipment_id}, #{ip_address}, #{port}, #{check_cmd}, #{name}, #{external}, #{mac_address}, #{description}, #{active} )" )
	void insertSensor( Sensor sensor );
	
	@Update("UPDATE sensors.sensor set equipment_id = #{equipment_id}, ip_address = #{ip_address}, port = #{port}, check_cmd = #{check_cmd}, "
			+ "name = #{name}, external = #{external}, mac_address = #{mac_address}, description = #{description}, active = #{active} where sensor_id = #{sensor_id}")
	void updateSensor( Sensor sensor );
	
	@Select("SELECT * from sensors.sensor where sensor_id = #{id}")
	Sensor getSensorById(int id);
	
	
	@Insert("INSERT into sensors.cycle ( equipment_id, profile, poll_frequency, number_of_barrels, created ) values ( "
			+ " #{equipment_id}, #{profile}, #{poll_frequency}, #{number_of_barrels}, NOW() )")
	@Options(useGeneratedKeys = true, keyProperty="cycle_id", keyColumn="cycle_id") 
	void insertCycle( Cycle cycle);
	
	@Insert("INSERT into sensors.alerts ( cycle_id, type, notification_type, value, created ) values ( #{cycle_id}, #{type}, #{notification_type}, #{value}, NOW() )")
	void insertAlert( Alert alert );
	

}
