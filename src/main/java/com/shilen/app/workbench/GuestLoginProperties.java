package com.shilen.app.workbench;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

@ConfigurationProperties(prefix = "app.security.guest-login")
public class GuestLoginProperties {

	private boolean enabled;
	private String username;
	private String password;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isConfigured() {
		return enabled
				&& StringUtils.hasText(username)
				&& StringUtils.hasText(password);
	}
}
