package org.simplity.examples.auth;

import java.security.Principal;

public class BasicPrincipal implements Principal {
	private String name;
	
	public BasicPrincipal(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
