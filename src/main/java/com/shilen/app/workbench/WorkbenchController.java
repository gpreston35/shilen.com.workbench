package com.shilen.app.workbench;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WorkbenchController {

	private final AuthenticationManager authenticationManager;
	private final GuestLoginProperties guestLoginProperties;

	public WorkbenchController(AuthenticationManager authenticationManager, GuestLoginProperties guestLoginProperties) {
		this.authenticationManager = authenticationManager;
		this.guestLoginProperties = guestLoginProperties;
	}

	@GetMapping("/")
	public String wo_short(Model model) {


		return "home";
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
	  
}
