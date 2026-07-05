package com.shilen.app.workbench;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableConfigurationProperties(GuestLoginProperties.class)
public class WorkbenchApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WorkbenchApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(WorkbenchApplication.class);
		
	}
	
	
}
