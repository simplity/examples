package org.simplity.examples.auth;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

public class BasicSecurityContext implements SecurityContext {

	String[] creds;
	
	public BasicSecurityContext(String auth) {
		creds = auth.split(":");
	}

	@Override
	public String getAuthenticationScheme() {
		return "basic";
	}

	@Override
	public Principal getUserPrincipal() {		
		return new BasicPrincipal(creds[0]);
	}


	@Override
	public boolean isSecure() {
		return false;
	}

	@Override
	public boolean isUserInRole(String role) {
		if(role.equals("basic"))
			return true;
		return false;
	}

}
