package org.simplity.examples.troubleTicketDemo.OAuthServer.endpoints;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import org.simplity.examples.troubleTicketDemo.OAuthServer.OAuthServerMain;
import org.simplity.kernel.comp.ComponentManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.auth.OAuth2Definition;
import io.swagger.models.auth.SecuritySchemeDefinition;

@Path("/login")
public class LoginEndpoint {
	private static String[][] UserPwd = {{"Jack","Bentley"},{"Aaron","Srinivas"},{"Vidya","Krishnamurthy"}}; 
	
	@GET
	public String scope(@QueryParam("scope") String scope ){
		String[] scopes = scope.split(",");
		
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		List<Scope> scopeList = new ArrayList<Scope>();
		SecuritySchemeDefinition lscopeDef = OAuthServerMain.secDefs.get("oauth");
		for(String lscope:scopes){
			scopeList.add(new Scope(lscope,((OAuth2Definition)lscopeDef).getScopes().get(lscope)));
		}	
		try {
			return mapper.writeValueAsString(scopeList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;	
	}
	
	@POST
    public Response authorize(@Context HttpServletRequest request)
        throws URISyntaxException, OAuthSystemException {

        OAuthAuthzRequest oauthRequest = null;

        OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());

        try {
            oauthRequest = new OAuthAuthzRequest(request);

            ObjectMapper ob = new ObjectMapper();
            JsonNode json = null;
            try {
				json = ob.readTree(request.getInputStream());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
            
            if(!isAuthenticated(json.get("loginId").textValue(),json.get("password").textValue())){
                // Return the OAuth error message
                OAuthResponse oauthResponse = OAuthRSResponse
                    .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                    .setRealm(TestContent.RESOURCE_SERVER_NAME)
                    .setError(OAuthError.ResourceResponse.EXPIRED_TOKEN)
                    .buildHeaderMessage();
                
            	return Response.status(Response.Status.UNAUTHORIZED)
                        .header(OAuth.HeaderType.WWW_AUTHENTICATE,
                            oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE))
                        .build();
            };
            
            
            //build response according to response_type
            String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);

            OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse
                .authorizationResponse(request,HttpServletResponse.SC_FOUND);

            if (responseType.equals(ResponseType.CODE.toString())) {
            	String accessCode = oauthIssuerImpl.authorizationCode();
                builder.setCode(accessCode);
            }
            if (responseType.equals(ResponseType.TOKEN.toString())) {
                builder.setAccessToken(oauthIssuerImpl.accessToken());
                builder.setExpiresIn(3600l);
            }

            String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);

            final OAuthResponse response = builder.location(redirectURI).buildQueryMessage();
            URI url = new URI(response.getLocationUri());

            return Response.ok(response.getLocationUri()).build();

        } catch (OAuthProblemException e) {

            final Response.ResponseBuilder responseBuilder = Response.status(HttpServletResponse.SC_FOUND);

            String redirectUri = e.getRedirectUri();

            if (OAuthUtils.isEmpty(redirectUri)) {
                throw new WebApplicationException(
                    responseBuilder.entity("OAuth callback url needs to be provided by client!!!").build());
            }
            final OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND)
                .error(e)
                .location(redirectUri).buildQueryMessage();
            final URI location = new URI(response.getLocationUri());
            return responseBuilder.location(location).build();
        }
    }

	private boolean isAuthenticated(String username, String pwd) {
		for(String[] tuple:UserPwd){
			if(tuple[0].equals(username) && tuple[1].equals(pwd)){
				return true;
			}
		}
		return false;
	}
	
	class Scope{
		
		public String name;
		public String description;
		public Scope(String name, String description) {
			super();
			this.name = name;
			this.description = description;
		}
	}
}
