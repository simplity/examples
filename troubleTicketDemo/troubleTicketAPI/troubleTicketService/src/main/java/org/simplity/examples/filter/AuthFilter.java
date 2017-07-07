package org.simplity.examples.filter;

import java.io.IOException;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.simplity.examples.TTServiceConfig;
import org.simplity.examples.auth.ApiKeySecurityContext;
import org.simplity.examples.auth.BasicSecurityContext;
import org.simplity.examples.auth.OAuth2SecurityContext;

import io.swagger.models.Swagger;
import io.swagger.models.auth.ApiKeyAuthDefinition;
import io.swagger.models.auth.OAuth2Definition;
import io.swagger.models.auth.In;
import io.swagger.models.auth.SecuritySchemeDefinition;
import io.swagger.parser.SwaggerParser;

@Provider
@PreMatching
public class AuthFilter implements ContainerRequestFilter  {
	private static Map<String, SecuritySchemeDefinition> secDefs = TTServiceConfig.secDefs;

	@Override
	public void filter(ContainerRequestContext request) throws IOException {

		for (SecuritySchemeDefinition secDef : secDefs.values()) {
			if (secDef.getType().equals("basic")) {
				if (request.getHeaders().containsKey("authorization")) {
					SecurityContext sc = basicAuth(request.getHeaders().get("authorization").get(0));
					if(sc!=null){
						request.setSecurityContext(sc);
						break;
					}
				}
			}
			if (secDef.getType().equals("api_key")) {
				if(((ApiKeyAuthDefinition)secDef).getIn().equals(In.HEADER)){
					if(request.getHeaders().containsKey("api_key")){
						SecurityContext sc = apiKeyAuth(request.getHeaders().getFirst("api_key"));
						if(sc!=null){
							request.setSecurityContext(sc);
							break;
						}
					}
				}
				if(((ApiKeyAuthDefinition)secDef).getIn().equals(In.QUERY)){
					if(request.getUriInfo().getQueryParameters().containsKey("api_key")){
						SecurityContext sc = apiKeyAuth(request.getUriInfo().getQueryParameters().getFirst("api_key"));
						if(sc!=null){
							request.setSecurityContext(sc);
							break;
						}						
					}					
				}				
			}
			if (secDef.getType().equals("oauth2")) {
				if(((OAuth2Definition)secDef).getFlow().equals("implicit")){
					if(request.getUriInfo().getQueryParameters().containsKey("token")){
						SecurityContext sc = oAuth2KeyAuth(request.getUriInfo().getQueryParameters().getFirst("token"));
						if(sc!=null){
							request.setSecurityContext(sc);
							break;
						}
					}
				}
			}
		}
	}

	private SecurityContext oAuth2KeyAuth(String auth) {
		SecurityContext sc= new OAuth2SecurityContext(auth);
		return sc;
	}

	private SecurityContext apiKeyAuth(String auth) {
		SecurityContext sc= new ApiKeySecurityContext(auth);
		return sc;
	}

	private SecurityContext basicAuth(String auth) {
		SecurityContext sc= new BasicSecurityContext(auth);
		return sc;
	}

}
