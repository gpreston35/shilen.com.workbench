package com.shilen.app.workbench;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GuestLoginPropertiesTest {

	@Test
	void isConfiguredRequiresFeatureFlagAndCredentials() {
		GuestLoginProperties properties = new GuestLoginProperties();

		assertFalse(properties.isConfigured());

		properties.setEnabled(true);
		assertFalse(properties.isConfigured());

		properties.setUsername("guest");
		assertFalse(properties.isConfigured());

		properties.setPassword("secret");
		assertTrue(properties.isConfigured());
	}
}
