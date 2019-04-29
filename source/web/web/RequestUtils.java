package edu.gatech.cs6400.team81.web;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {

	public static final int getIntValue(HttpServletRequest request, String parameterName){
		int intVal = 0;
		String parameter = request.getParameter(parameterName);
		
		if(parameter != null){
			try{
				intVal = Integer.parseInt(parameter);
			} catch (NumberFormatException nfe){
				System.err.println("Error parsing request parameter['" + parameterName + "'] = " + parameter);
			}
		}
		
		return intVal;
	}
}
