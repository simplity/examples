package org.simplity.examples.auth;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

public class OAuth2SecurityContext implements SecurityContext {

	private String username;

	public OAuth2SecurityContext(String auth) {
		// lookup auth and get username
		this.username = "oauth2";
	}

	@Override
	public String getAuthenticationScheme() {
		return "oauth2";
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
		if (role.equals("api_key"))
			return true;
		return false;
	}

}
