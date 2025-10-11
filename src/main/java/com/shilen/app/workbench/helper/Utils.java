package com.shilen.app.workbench.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class Utils {
	
	public static String ifNull ( String s ) {
		
		if ( s == null )
			s = "";
		
		return s;
		
	}
	
	public static String getDateBasedOnCurrent( int numberOfDays  ) {
		
		LocalDateTime now = LocalDateTime.now();
        
        if ( numberOfDays > 0 )
        	now = now.plusDays(numberOfDays);
        else if ( numberOfDays < 0 )
        	now = now.minusDays(numberOfDays);

        String pattern = "yyyy-MM-dd"; 
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		
        return now.format(formatter);
		
	}

}
