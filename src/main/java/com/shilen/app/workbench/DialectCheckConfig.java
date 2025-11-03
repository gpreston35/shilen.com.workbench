package com.shilen.app.workbench;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

import java.util.Map;

@Configuration
public class DialectCheckConfig {

    private final Map<String, IDialect> dialects;

    // Inject all available IDialect beans
    public DialectCheckConfig(Map<String, IDialect> dialects) {
        this.dialects = dialects;
    }

    @Bean
    public CommandLineRunner checkSecurityDialect() {
        return args -> {
            System.out.println("--- Registered Thymeleaf Dialects ---");
            dialects.forEach((beanName, dialect) -> {
                System.out.println("Bean Name: " + beanName + " | Dialect Name: " + dialect.getName());
            });

            boolean securityDialectPresent = dialects.values().stream()
                    .anyMatch(d -> d instanceof SpringSecurityDialect);

            if (securityDialectPresent) {
                System.out.println("\nSUCCESS: SpringSecurityDialect is registered.");
            } else {
                System.out.println("\nFAILURE: SpringSecurityDialect is NOT registered.");
            }
            System.out.println("-------------------------------------");
        };
    }
}
