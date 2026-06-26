package com.shilen.app.workbench;

import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.transaction.annotation.Transactional;

import com.shilen.app.workbench.dao.LookupMapper;
import com.shilen.app.workbench.dao.SensorsMapper;
import com.shilen.app.workbench.helper.AjaxResponseBody;
import com.shilen.app.workbench.model.User;
import com.shilen.app.workbench.model.ss.Alert;
import com.shilen.app.workbench.model.ss.Cycle;
import com.shilen.app.workbench.model.ss.CycleSensor;
import com.shilen.app.workbench.model.ss.Equipment;
import com.shilen.app.workbench.model.ss.GraphCycle;
import com.shilen.app.workbench.model.ss.Profile;
import com.shilen.app.workbench.model.ss.Sensor;


@Controller
public class SensorsController {
	
	private final SensorsMapper sensorsMapper;
	private final LookupMapper lookupMapper;
	
	public SensorsController(SensorsMapper sensorsMapper, LookupMapper lookupMapper) {
		this.sensorsMapper = sensorsMapper;
		this.lookupMapper = lookupMapper;
	}
	
	
	@PostMapping("/ss/equipment/update")
	 public ResponseEntity<?> equipment_update(@ModelAttribute Equipment equipment,  Model model) {

		  AjaxResponseBody result = new AjaxResponseBody();
	 	  
		  if ( equipment.getEquipment_id() == -1 )
			  sensorsMapper.insertEquipment(equipment);
		  else
			  sensorsMapper.updateEquipment(equipment);
		  
	 	  result.setMsg("success");
	 	  return ResponseEntity.ok(result);
	 	  
	 }
	
	
	@PostMapping("/ss/sensor/update")
	 public ResponseEntity<?> sensor_update(@ModelAttribute Sensor sensor,  Model model) {

		  AjaxResponseBody result = new AjaxResponseBody();
		  
		  if ( sensor.getSensor_id() == -1 )
			  sensorsMapper.insertSensor(sensor);
		  else
			  sensorsMapper.updateSensor(sensor);
		  
	 	  result.setMsg("success");
	 	  return ResponseEntity.ok(result);
	 	  
	 }

	@PostMapping("/ss/profile/update")
	public ResponseEntity<?> profile_update(@ModelAttribute Profile profile, Model model) {

		AjaxResponseBody result = new AjaxResponseBody();

		if (profile.getProfile_id() == -1) {
			sensorsMapper.insertProfile(profile);
		} else {
			sensorsMapper.updateProfile(profile);
		}

		result.setMsg("success");
		return ResponseEntity.ok(result);
	}

	@PostMapping("/ss/profile/delete")
	public ResponseEntity<?> profile_delete(@ModelAttribute Profile profile, Model model) {

		AjaxResponseBody result = new AjaxResponseBody();
		sensorsMapper.deleteProfile(profile.getProfile_id());
		result.setMsg("success");
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/ss/cycle/insert")
	@Transactional
	public String cycle_insert(@ModelAttribute("FORM") Cycle cycle, Model model) {
		cycle.setPoll_frequency(defaultInt(cycle.getPoll_frequency(), 1));
		cycle.setNumber_of_barrels(defaultInt(cycle.getNumber_of_barrels(), 0));
		prepareCycleCreateSubmission(cycle);

		Integer equipmentId = cycle.getEquipment_id();
		if (equipmentId == null) {
			model.addAttribute("FORM", cycle);
			model.addAttribute("PROFILES", sensorsMapper.getProfile());
			model.addAttribute("EDIT_MODE", false);
			model.addAttribute("CYCLE_ERROR", "Unable to start cycle: equipment is required.");
			return "ss/cycle";
		}

		Integer primarySensorId = sensorsMapper.findPrimaryActiveSensorIdForEquipment(equipmentId);
		if (primarySensorId == null) {
			model.addAttribute("FORM", cycle);
			model.addAttribute("PROFILES", sensorsMapper.getProfile());
			model.addAttribute("EDIT_MODE", false);
			model.addAttribute("CYCLE_ERROR", "Unable to start cycle: no active sensor is configured for this equipment.");
			return "ss/cycle";
		}

		sensorsMapper.insertCycle(cycle);
		sensorsMapper.insertCycleSensor(cycle.getCycle_id(), primarySensorId);
		persistCycleAlerts(cycle.getCycle_id(), cycle.getAlerts());
		persistCycleRecipients(cycle.getCycle_id(), cycle.getUsers());
		return renderCyclePage(cycle.getCycle_id(), "Cycle successfully added", model);
	}
	
	
	@PostMapping("/ss/cycle/maintain")
	@Transactional
	public String cycle_maintain(@ModelAttribute("FORM") Cycle cycle, Model model) {
		if (cycle.getCycle_id() == null) {
			Cycle emptyCycle = new Cycle();
			prepareCycleForm(emptyCycle, false);
			model.addAttribute("FORM", emptyCycle);
			model.addAttribute("PROFILES", sensorsMapper.getProfile());
			model.addAttribute("EDIT_MODE", true);
			model.addAttribute("CYCLE_ERROR", "Unable to save cycle maintenance. Please review the form values.");
			return "ss/cycle";
		}

		int cycleId = cycle.getCycle_id();
		cycle.setNumber_of_barrels(defaultInt(cycle.getNumber_of_barrels(), 0));
		sensorsMapper.updateCycleMaintenance(cycle);

		persistCycleAlerts(cycleId, cycle.getAlerts());
		persistCycleRecipients(cycleId, cycle.getUsers());
		return renderCyclePage(cycleId, "Cycle successfully updated", model);
	}
	
	
	
	
	
	@RequestMapping(value= "/ss/equipment/json", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE )
	public @ResponseBody List<Equipment> getgetEquipment()  {
		return sensorsMapper.getEquipment();
	} 
	
	@RequestMapping(value= "/ss/sensor/json", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE )
	public @ResponseBody List<Sensor> getSensor()  {
		return sensorsMapper.getSensor();
	} 

	@RequestMapping(value= "/ss/profile/json", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE )
	public @ResponseBody List<Profile> getProfile()  {
		return sensorsMapper.getProfile();
	}
	
	
	
	@RequestMapping(value= "/ss/equipment/json/{id}", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE )
	public @ResponseBody Equipment getEquipmentByID(@PathVariable("id") int id)  {
		return sensorsMapper.getEquipmentByID( id );
	
	}
	
	
	@RequestMapping(value= "/ss/sensor/json/{id}", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE )
	public @ResponseBody Sensor getSensorByID(@PathVariable("id") int id)  {
		return sensorsMapper.getSensorById( id );

	}

	@RequestMapping(value= "/ss/profile/json/{id}", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE )
	public @ResponseBody Profile getProfileByID(@PathVariable("id") int id)  {
		return sensorsMapper.getProfileById( id );

	}


	@GetMapping("/ss/cycle/add")
	public String cycle_add(Model model) {
		Cycle cycle = new Cycle();
		prepareCycleForm(cycle, false);
		model.addAttribute("FORM", cycle);
		model.addAttribute("PROFILES", sensorsMapper.getProfile());
		model.addAttribute("EDIT_MODE", false);
		return "ss/cycle";
	}

	@GetMapping("/ss/cycle/edit/{id}")
	public String cycle_edit(@PathVariable("id") int id, Model model) {
		Cycle cycle = sensorsMapper.getCycle(id);
		prepareCycleForm(cycle, true);
		model.addAttribute("FORM", cycle);
		model.addAttribute("PROFILES", sensorsMapper.getProfile());
		model.addAttribute("EDIT_MODE", true);
		return "ss/cycle";
	}

	private String renderCyclePage(int cycleId, String successMessage, Model model) {
		Cycle savedCycle = sensorsMapper.getCycle(cycleId);
		if (savedCycle == null) {
			Cycle emptyCycle = new Cycle();
			prepareCycleForm(emptyCycle, false);
			model.addAttribute("FORM", emptyCycle);
			model.addAttribute("PROFILES", sensorsMapper.getProfile());
			model.addAttribute("EDIT_MODE", false);
			model.addAttribute("CYCLE_ERROR", "Unable to load cycle after save.");
			return "ss/cycle";
		}
		prepareCycleForm(savedCycle, true);
		model.addAttribute("FORM", savedCycle);
		model.addAttribute("PROFILES", sensorsMapper.getProfile());
		model.addAttribute("EDIT_MODE", true);
		model.addAttribute("CYCLE_SUCCESS", successMessage);
		return "ss/cycle";
	}

	private void prepareCycleForm(Cycle cycle, boolean isEdit) {
		List<Alert> persistedAlerts = List.of();
		Map<Integer, String> persistedRecipientsByUserId = Map.of();
		Map<String, String> persistedRecipientsByAddress = Map.of();
		boolean hasPersistedConfig = false;
		if (isEdit && cycle.getCycle_id() != null) {
			persistedAlerts = sensorsMapper.getCycleAlerts(cycle.getCycle_id());
			List<Alert> persistedRecipients = sensorsMapper.getCycleAlertRecipient(cycle.getCycle_id());
			persistedRecipientsByUserId = persistedRecipients.stream()
					.filter(alert -> alert.getUser_id() != null)
					.collect(Collectors.toMap(
							Alert::getUser_id,
							alert -> StringUtils.hasText(alert.getEnabled()) ? alert.getEnabled().trim() : "Y",
							(first, second) -> second));
			persistedRecipientsByAddress = persistedRecipients.stream()
					.filter(alert -> StringUtils.hasText(alert.getRecipient()))
					.collect(Collectors.toMap(
							alert -> alert.getRecipient().trim(),
							alert -> StringUtils.hasText(alert.getEnabled()) ? alert.getEnabled().trim() : "Y",
							(first, second) -> second));
			hasPersistedConfig = !persistedAlerts.isEmpty()
					|| !persistedRecipients.isEmpty()
					|| StringUtils.hasText(cycle.getUpdated_dt());
		}

		if (cycle.getAlerts() == null || cycle.getAlerts().isEmpty()) {
			cycle.getAlerts().add(new Alert("ALERT_MAX_ERRORS", "E", "Error threshold", 10));
			cycle.getAlerts().add(new Alert("ALERT_TEMP_ASC", "W", "Temp threshold (ascending)", 675));
			cycle.getAlerts().add(new Alert("ALERT_TEMP_DESC", "W", "Temp threshold (descending)", 300));
			cycle.getAlerts().add(new Alert("ALERT_MAX_TEMP", "W", "Temp threshold (max)", 1200));
			cycle.getAlerts().add(new Alert("ALERT_MAX_RUNTIME", "W", "Cycle run time (hours)", 24));
		}

		if (isEdit && cycle.getCycle_id() != null) {
			if (!hasPersistedConfig) {
				for (Alert alert : cycle.getAlerts()) {
					alert.setSelected(2);
				}
			} else {
				Map<String, Alert> persistedByType = persistedAlerts.stream()
						.collect(Collectors.toMap(
								alert -> normalizeAlertType(alert.getType()),
								Function.identity(),
								(first, second) -> second));
				Set<String> enabledTypes = persistedAlerts.stream()
						.filter(alert -> "Y".equalsIgnoreCase(alert.getEnabled()))
						.map(Alert::getType)
						.map(this::normalizeAlertType)
						.collect(Collectors.toSet());
				for (Alert alert : cycle.getAlerts()) {
					String normalizedType = normalizeAlertType(alert.getType());
					alert.setSelected(enabledTypes.contains(normalizedType) ? 2 : 1);
					Alert persisted = persistedByType.get(normalizedType);
					if (persisted != null) {
						alert.setNotification_type(persisted.getNotification_type());
						alert.setValue(persisted.getValue());
					}
				}
			}
		} else {
			for (Alert alert : cycle.getAlerts()) {
				alert.setSelected(2);
			}
		}

		cycle.setUsers(lookupMapper.getSensorUsers());
		if (isEdit && cycle.getCycle_id() != null) {
			if (!hasPersistedConfig) {
				for (User user : cycle.getUsers()) {
					user.setChecked(1);
				}
			} else {
				for (User user : cycle.getUsers()) {
					String enabled = persistedRecipientsByUserId.get(user.getId());
					if (!StringUtils.hasText(enabled) && StringUtils.hasText(user.getSms_email())) {
						enabled = persistedRecipientsByAddress.get(user.getSms_email().trim());
					}
					user.setChecked("Y".equalsIgnoreCase(enabled) ? 1 : 0);
				}
			}
		} else {
			for (User user : cycle.getUsers()) {
				user.setChecked(1);
			}
		}
	}

	private int defaultInt(Integer value, int fallback) {
		return value == null ? fallback : value;
	}

	private void prepareCycleCreateSubmission(Cycle cycle) {
		if (cycle.getAlerts() == null || cycle.getAlerts().isEmpty()) {
			cycle.getAlerts().add(new Alert("ALERT_MAX_ERRORS", "E", "Error threshold", 10));
			cycle.getAlerts().add(new Alert("ALERT_TEMP_ASC", "W", "Temp threshold (ascending)", 675));
			cycle.getAlerts().add(new Alert("ALERT_TEMP_DESC", "W", "Temp threshold (descending)", 300));
			cycle.getAlerts().add(new Alert("ALERT_MAX_TEMP", "W", "Temp threshold (max)", 1200));
			cycle.getAlerts().add(new Alert("ALERT_MAX_RUNTIME", "W", "Cycle run time (hours)", 24));
			for (Alert alert : cycle.getAlerts()) {
				alert.setSelected(2);
			}
		}

		if (cycle.getUsers() == null || cycle.getUsers().isEmpty()) {
			cycle.setUsers(lookupMapper.getSensorUsers());
			for (User user : cycle.getUsers()) {
				user.setChecked(1);
			}
		}
	}

	private void persistCycleAlerts(Integer cycleId, List<Alert> alerts) {
		if (cycleId == null) {
			return;
		}
		sensorsMapper.deleteCycleAlerts(cycleId);
		if (alerts == null) {
			return;
		}
		for (Alert alert : alerts) {
			if (alert == null || !StringUtils.hasText(alert.getType())) {
				continue;
			}
			alert.setCycle_id(cycleId);
			alert.setEnabled(alert.getSelected() == 2 ? "Y" : "N");
			sensorsMapper.insertAlert(alert);
		}
	}

	private void persistCycleRecipients(Integer cycleId, List<User> users) {
		if (cycleId == null) {
			return;
		}
		sensorsMapper.deleteCycleAlertRecipients(cycleId);
		if (users == null) {
			return;
		}
		for (User user : users) {
			if (user == null || !StringUtils.hasText(user.getSms_email())) {
				continue;
			}
			sensorsMapper.insertAlertRecipient(
					cycleId,
					user.getId(),
					user.getSms_email().trim(),
					user.getChecked() == 1 ? "Y" : "N");
		}
	}

	private String normalizeAlertType(String type) {
		if (!StringUtils.hasText(type)) {
			return "";
		}
		if ("ALERT_MAX_RUN_TIME".equalsIgnoreCase(type)) {
			return "ALERT_MAX_RUNTIME";
		}
		return type.toUpperCase();
	}
	
	
	@GetMapping("/ss/sensor")   
	public String sensor_home(Model model) {
		
		return "ss/sensor";
	} 	
	
	
	@GetMapping("/ss/equipment")   
	public String equipment_home(Model model) {
		
		return "ss/equipment";
	} 	

	@GetMapping("/ss/profile")
	public String profile_home(Model model) {

		return "ss/profile";
	}
	
	@GetMapping("/ss/home")   
	public String home(Model model) {
		List<CycleSensor> active = sensorsMapper.getActive();
		List<CycleSensor> completed = sensorsMapper.getCompletedLast7Days();
		List<Alert> alerts = sensorsMapper.getAlerts();
		
		model.addAttribute("ACTIVE_JOBS",active);
		model.addAttribute("COMPLETED_JOBS", completed);
		model.addAttribute("ALERTS", alerts);

		return "ss/home";
	} 
	
	@RequestMapping(value= "/ss/samples/json/{id}", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE )
	public @ResponseBody List<GraphCycle> getSamplesIDjson(@PathVariable("id") String id)  {
		// get dates
		CycleSensor cyclesensor = sensorsMapper.getDateParameters( Integer.parseInt(id) );
		List<GraphCycle> _data = sensorsMapper.graphCycle( Integer.parseInt(id), cyclesensor.getStart_cycle(), 
				 cyclesensor.getEnd_cycle(), cyclesensor.getMac_address() );

		return _data;
	
	}

}
