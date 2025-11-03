package com.shilen.app.workbench;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;


	
	@Configuration
    @EnableWebSecurity
	public class WebSecurityConfig {
		
	    @Autowired
	    private DataSource dataSource;
	    
	    @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	    	
	    	
	        auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
	            .dataSource(dataSource)
	            .passwordEncoder( new BCryptPasswordEncoder() )
	            .usersByUsernameQuery("select username, password, enabled from user where username=?")
	            .authoritiesByUsernameQuery("select m.username, r.role from"
							            		+ "   user_role_mapping m,"
							            		+ "   user_role r"
							            		+ " where m.user_role_id = r.id"
							            		+ " and m.username = ?")
	        ;
	    }
	 
	   /* 
	    @Bean
	    SecurityFilterChain web(HttpSecurity http) throws Exception {
	    	http
	    		// ...
	    		.authorizeHttpRequests(authorize -> authorize                                  
	    			.requestMatchers("/assets/**").permitAll()
	    			.anyRequest().authenticated()

                                            
	    		);

	    	return http.build();
	    }
	    */
	    /*
	     * close

	    @Bean
	    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http.authorizeHttpRequests( 
	                auth -> auth.anyRequest().authenticated())
	            .formLogin(login -> login.permitAll())
	            .logout(logout -> logout.permitAll())
	            .authorizeHttpRequests( authz -> authz
	            		.requestMatchers("/assets/**").permitAll())
	    
	            .formLogin( formLogin ->
	        			formLogin
	        				.loginPage("/login"));
	         
	      
	       

	         
	        return http.build();
	    }

	    */

	    @Bean
	    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
	        return new MvcRequestMatcher.Builder(introspector);
	    }
	    
	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
	    	/*
		
	    	http.authorizeHttpRequests( auth -> auth.anyRequest().authenticated()
	    			                                .requestMatchers("/assets/**").permitAll() )
		            .formLogin(login -> login.permitAll())
		           

		            
		            .logout(logout -> logout.permitAll() );
	    	
	    	*/
	    	
	    	http

	    	.authorizeHttpRequests((authorizeHttpRequests) ->
				authorizeHttpRequests
					.requestMatchers("/assets/**").permitAll()
					.anyRequest().authenticated()
			)
	    	
	    	.formLogin(login -> login
	                .loginPage("/login").permitAll() 
	          
	                )
	    	// sample logout customization
 			.logout((logout) ->
 				logout.deleteCookies("remove")
 					.invalidateHttpSession(false)
 				//	.logoutUrl("/login")
 					.logoutSuccessUrl("/login"));

	    	
	    	http.csrf().disable();
	    	http.headers().frameOptions().disable();
	    	
		            
	    	/*
	    	http.authorizeRequests(requests -> requests
            .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
            .requestMatchers(antMatcher("/static/**")).permitAll()
            .anyRequest().authenticated()
    );
    http.formLogin(login -> login
            .loginPage("/login").permitAll()
            .loginProcessingUrl("/userAuth")
            .permitAll()
    );
    http.csrf().disable();
	    	*/
	    
	        return http.build();
	    }  
	    
	    
	    /*
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.authorizeRequests()
	    //    	.antMatchers("/").permitAll()
	        	.antMatchers("/assets/**").permitAll()
	            .anyRequest().authenticated()
	            .and()
	            .formLogin().permitAll()
	            .loginPage("/login")
	           // .failureUrl("/login-error")
	            .and()
	            .logout().permitAll();
	       //     .logoutUrl("/login")
	      //      .invalidateHttpSession(true)
	       //     .deleteCookies("JSESSIONID")
	      //      .logoutSuccessUrl("/login");
	        
	       http.csrf().disable();
	            
	       http.headers().frameOptions().sameOrigin();
	  
	        
	        
	}
	
	*/
}
