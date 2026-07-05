package com.shilen.app.workbench;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
		Object statusValue = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (statusValue != null) {
			int statusCode = Integer.parseInt(statusValue.toString());
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return "error-404";
			}
			if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return "error-500";
			}
		}
		return "error";
	}

}
