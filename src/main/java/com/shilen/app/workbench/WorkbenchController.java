package com.shilen.app.workbench;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WorkbenchController {

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

	     return "login";
	  }
	  

	  
	  
	  
	  
}
