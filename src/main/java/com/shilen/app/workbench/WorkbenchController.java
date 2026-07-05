package com.shilen.app.workbench;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shilen.app.workbench.dao.LookupMapper;
import com.shilen.app.workbench.dao.SensorsMapper;
import com.shilen.app.workbench.model.DashboardLink;
import com.shilen.app.workbench.model.ss.Sensor;

@Controller
public class WorkbenchController {

	private final AuthenticationManager authenticationManager;
	private final GuestLoginProperties guestLoginProperties;
	private final SensorsMapper sensorsMapper;
	private final LookupMapper lookupMapper;

	public WorkbenchController(
			AuthenticationManager authenticationManager,
			GuestLoginProperties guestLoginProperties,
			SensorsMapper sensorsMapper,
			LookupMapper lookupMapper) {
		this.authenticationManager = authenticationManager;
		this.guestLoginProperties = guestLoginProperties;
		this.sensorsMapper = sensorsMapper;
		this.lookupMapper = lookupMapper;
	}

	@GetMapping("/")
	public String wo_short(Model model) {
		List<Sensor> dashboardSensors = loadDashboardSensors();
		List<DashboardLink> dashboardLinks = loadDashboardLinks();
		model.addAttribute("dashboardSensors", dashboardSensors);
		model.addAttribute("dashboardSensorCount", dashboardSensors.size());
		model.addAttribute("dashboardLinks", dashboardLinks);
		model.addAttribute("dashboardLinkCount", dashboardLinks.size());

		return "home";
	}

	@GetMapping("/api/dashboard/sensors")
	@ResponseBody
	public List<DashboardSensorReading> dashboardSensors() {
		return loadDashboardSensors().stream()
				.map(sensor -> new DashboardSensorReading(
						sensor.getName(),
						sensor.getLast_read_value(),
						sensor.getLast_read_status(),
						sensor.getCycle_state()))
				.collect(Collectors.toList());
	}
	
	// Login form
	 @RequestMapping(value = "/login", method = RequestMethod.GET)
	 public String login(Model model, String error, String logout,RedirectAttributes redirAttrs) {
		 if (error != null)
		 
			 model.addAttribute(Tokens.ERROR, "Your username and/or password are invalid.");

	        if (logout != null)
	            model.addAttribute(Tokens.SUCCESS, "You have been logged out successfully.");

	        model.addAttribute("guestLoginEnabled", guestLoginProperties.isConfigured());

	     return "login";
	  }

	@PostMapping("/guest-login")
	public String guestLogin(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirAttrs) {
		if (!guestLoginProperties.isConfigured()) {
			redirAttrs.addFlashAttribute(Tokens.WARNING, "Guest login is not available.");
			return "redirect:/login";
		}

		try {
			Authentication authentication = authenticationManager.authenticate(
					UsernamePasswordAuthenticationToken.unauthenticated(
							guestLoginProperties.getUsername(),
							guestLoginProperties.getPassword()));

			SecurityContext context = SecurityContextHolder.createEmptyContext();
			context.setAuthentication(authentication);
			SecurityContextHolder.setContext(context);
			new HttpSessionSecurityContextRepository().saveContext(context, request, response);
			return "redirect:/";
		} catch (BadCredentialsException ex) {
			SecurityContextHolder.clearContext();
			redirAttrs.addFlashAttribute(Tokens.ERROR, "Guest login is not available.");
			return "redirect:/login";
		}
	}

	private List<Sensor> loadDashboardSensors() {
		return sensorsMapper.getDashboardSensors().stream()
				.filter(sensor -> sensor.getActive() != null && "Y".equalsIgnoreCase(sensor.getActive()))
				.sorted(Comparator.comparing(Sensor::getName, Comparator.nullsLast(String::compareToIgnoreCase)))
				.collect(Collectors.toList());
	}

	private List<DashboardLink> loadDashboardLinks() {
		return lookupMapper.getDashboardLinks().stream()
				.filter(link -> link.getLabel() != null && !link.getLabel().isBlank())
				.filter(link -> link.getUrl() != null && !link.getUrl().isBlank())
				.collect(Collectors.toList());
	}

	public record DashboardSensorReading(
			String name,
			Double lastReadValue,
			String lastReadStatus,
			String cycleState) {
	}
	  
}
