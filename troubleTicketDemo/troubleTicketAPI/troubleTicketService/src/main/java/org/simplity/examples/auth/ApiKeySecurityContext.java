package org.simplity.examples.auth;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

public class ApiKeySecurityContext implements SecurityContext {

	private String username;

	public ApiKeySecurityContext(String auth) {
		//lookup auth and get username
		this.username = "api_key";
	}

	@Override
	public String getAuthenticationScheme() {
		return "api_key";
	}

	@Override
	public Principal getUserPrincipal() {
		return new BasicPrincipal(username);
	}

	@Override
	public boolean isSecure() {
		return false;
	}

	@Override
	public boolean isUserInRole(String role) {
		if(role.equals("api_key"))
			return true;		
		return false;
	}

}
