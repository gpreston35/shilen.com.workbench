package com.shilen.app.workbench;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shilen.app.workbench.dao.LookupMapper;
import com.shilen.app.workbench.dao.SensorsMapper;
import com.shilen.app.workbench.helper.AjaxResponseBody;
import com.shilen.app.workbench.model.ss.Alert;
import com.shilen.app.workbench.model.ss.Cycle;
import com.shilen.app.workbench.model.ss.CycleSensor;
import com.shilen.app.workbench.model.ss.Equipment;
import com.shilen.app.workbench.model.ss.GraphCycle;
import com.shilen.app.workbench.model.ss.Sensor;


@Controller
public class SensorsController {
	
	
	@PostMapping("/ss/equipment/update")
	 public ResponseEntity<?> equipment_update(@ModelAttribute Equipment equipment,  Model model) {

		  AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		  
		  ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
		  ctx.refresh();
		  AjaxResponseBody result = new AjaxResponseBody();
	 	   
		  SensorsMapper mapper = ctx.getBean( SensorsMapper.class);
	 	  
		  if ( equipment.getEquipment_id() == -1 )
			  mapper.insertEquipment(equipment);
		  else
			  mapper.updateEquipment(equipment);
		  
	 	  
	 	  ctx.close();
	 	    
	 	  result.setMsg("success");
	 	  return ResponseEntity.ok(result);
	 	  
	 }
	
	
	@PostMapping("/ss/sensor/update")
	 public ResponseEntity<?> sensor_update(@ModelAttribute Sensor sensor,  Model model) {

		  AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		  
		  ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
		  ctx.refresh();
		  AjaxResponseBody result = new AjaxResponseBody();
	 	   
		  SensorsMapper mapper = ctx.getBean( SensorsMapper.class);
		  
		  if ( sensor.getSensor_id() == -1 )
			  mapper.insertSensor(sensor);
		  else
			  mapper.updateSensor(sensor);
		  
	 	  ctx.close();
	 	    
	 	  result.setMsg("success");
	 	  return ResponseEntity.ok(result);
	 	  
	 }
	
	
	@PostMapping("/ss/cycle/insert")
	 public String cycle_insert(@ModelAttribute Cycle cycle, Errors errors, Model model) {

		  AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		  
		  ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
		  ctx.refresh();
		  	  
		  SensorsMapper mapper = ctx.getBean( SensorsMapper.class);
		  
		  mapper.insertCycle(cycle);
		  
		  for ( Alert alert : cycle.getAlerts() ) {
			//  if ( alert.getChecked() == 1 ) {
			//	  alert.setCycle_id( cycle.getCycle_id() );
			//	  mapper.insertAlert(alert);
			//  }
			  
		  }
		    
	 	  model.addAttribute("FORM", cycle);
		  
	 	  ctx.close();
	 	    
	 	 return "ss/cycle";
	 	  
	 }
	
	
	
	
	
	@RequestMapping(value= "/ss/equipment/json", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE )
	public @ResponseBody List<Equipment> getgetEquipment()  {
		
    	AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
 	   	ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
 	   	ctx.refresh();
 	   
 	   	SensorsMapper mapper = ctx.getBean( SensorsMapper.class);
		
		List<Equipment> equipment = mapper.getEquipment();

		
		ctx.close();
		
		return equipment;
	} 
	
	@RequestMapping(value= "/ss/sensor/json", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE )
	public @ResponseBody List<Sensor> getSensor()  {
		
    	AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
 	   	ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
 	   	ctx.refresh();
 	   
 	   	SensorsMapper mapper = ctx.getBean( SensorsMapper.class);
		
		List<Sensor> equipment = mapper.getSensor();

		
		ctx.close();
		
		return equipment;
	} 
	
	
	
	@RequestMapping(value= "/ss/equipment/json/{id}", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE )
	public @ResponseBody Equipment getEquipmentByID(@PathVariable("id") int id)  {
			
	    	AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
	 	   	ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
	 	   	ctx.refresh();
	 	   
	 	   	SensorsMapper mapper = ctx.getBean( SensorsMapper.class);
	 	   
	 	   	Equipment equipment = mapper.getEquipmentByID( id );
	 	   	
 	   		ctx.close();

	 	   	return equipment;
	
	}
	
	
	@RequestMapping(value= "/ss/sensor/json/{id}", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE )
	public @ResponseBody Sensor getSensorByID(@PathVariable("id") int id)  {
			
	    	AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
	 	   	ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
	 	   	ctx.refresh();
	 	   
	 	   	SensorsMapper mapper = ctx.getBean( SensorsMapper.class);
	 	   
	 	   	Sensor sensor = mapper.getSensorById( id );
	 	   	
 	   		ctx.close();

	 	   	return sensor;
	
	}
	
	
	@GetMapping("/ss/cycle/add")   
	public String cycle_add(Model model) {
		
    	AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
 	   	ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
 	   	ctx.refresh();
		
		Cycle cycle = new Cycle();
		
		cycle.getAlerts().add(  new Alert( "ALERT_MAX_ERRORS", "E", "Error threshold", 10) );
		cycle.getAlerts().add(  new Alert( "ALERT_TEMP_ASC", "W", "Temp threshold (ascending)", 675) );
		cycle.getAlerts().add(  new Alert( "ALERT_TEMP_DESC", "W", "Temp threshold (descending)", 300) );
		cycle.getAlerts().add(  new Alert( "ALERT_MAX_TEMP", "W", "Temp threshold (max)", 1200) );
		cycle.getAlerts().add(  new Alert( "ALERT_MAX_RUNTIME", "W", "Cycle run time (hours)",24) );
		
		LookupMapper mapper = ctx.getBean(LookupMapper.class);
		
		cycle.setUsers( mapper.getSensorUsers() );
		
		model.addAttribute("FORM", cycle );
		
		ctx.close();
		
		return "ss/cycle";

	} 	
	
	
	@GetMapping("/ss/sensor")   
	public String sensor_home(Model model) {
		
		return "ss/sensor";
	} 	
	
	
	@GetMapping("/ss/equipment")   
	public String equipment_home(Model model) {
		
		return "ss/equipment";
	} 	
	
	@GetMapping("/ss/home")   
	public String home(Model model) {
		
    	AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
 	   	ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
 	   	ctx.refresh();
 	   
 	   	SensorsMapper mapper = ctx.getBean( SensorsMapper.class);
		
		List<CycleSensor> active = mapper.getActive();
		List<Alert> alerts = mapper.getAlerts();
		
		model.addAttribute("ACTIVE_JOBS",active);
		model.addAttribute("ALERTS", alerts);
		
		ctx.close();
		
		return "ss/home";
	} 
	
	@RequestMapping(value= "/ss/samples/json/{id}", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE )
	public @ResponseBody List<GraphCycle> getSamplesIDjson(@PathVariable("id") String id)  {
			
	    	AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
	 	   	ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
	 	   	ctx.refresh();
	 	   
	 	   SensorsMapper mapper = ctx.getBean( SensorsMapper.class);
	 	   	
	 	   	// get dates
	 	   	CycleSensor cyclesensor = mapper.getDateParameters( Integer.parseInt(id) );
	 	   	List<GraphCycle> _data = mapper.graphCycle( Integer.parseInt(id), cyclesensor.getStart_cycle(), 
	 	   			 cyclesensor.getEnd_cycle(), cyclesensor.getMac_address() );

	 	   	ctx.close();

	 	   	return _data;
	
	}

}
