package com.fun.learning.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import lombok.Getter;

// This class is required only if any additional details is required in authentication like pin in our case
// This is not required if it is only username and password authentication. 
//UsernamePasswordAuthenticationFilter already placed Credetails in UsernamePasswordAuthenticationToken however gives a chance to place other details also
// like remoteip(webautheticationDetails) and any additional details that user might want to keep in future

public class AdditionalAuthenticationDetails extends WebAuthenticationDetails {

	@Getter
	private String securityPin;

	public AdditionalAuthenticationDetails(HttpServletRequest request) {
		super(request);
		securityPin = request.getParameter("pin") != null ? request.getParameter("pin") : null;
	}

}
