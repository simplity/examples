package org.simplity.examples.troubleTicketDemo.OAuthServer;

import java.io.File;
import java.net.URI;
import java.util.EnumSet;
import java.util.HashMap;

import javax.servlet.DispatcherType;
import javax.servlet.ServletRegistration;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.FilterRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;
import org.simplity.examples.troubleTicketDemo.OAuthServer.endpoints.LoginEndpoint;
import org.simplity.examples.troubleTicketDemo.OAuthServer.filters.CorsFilter;
import org.simplity.examples.troubleTicketDemo.OAuthServer.filters.AuthEntryFilter;

import com.sun.javafx.collections.MappingChange.Map;

import io.swagger.models.Swagger;
import io.swagger.models.auth.SecuritySchemeDefinition;
import io.swagger.parser.SwaggerParser;

/**
 * OAuthService
 *
 */
public class OAuthServerMain {
	public static java.util.Map<String, SecuritySchemeDefinition> secDefs;

	public static void main(String[] args) {
		HttpServer server = null;
		try {

			File jarPath = new File(
					OAuthServerMain.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			String folder = jarPath + File.separator + "webapp";
			final Swagger swagger = new SwaggerParser()
					.read(jarPath.getParent() + File.separator + "comp/openapi" + File.separator + "troubleTicket.json");
			secDefs = swagger.getSecurityDefinitions();

			WebappContext wContext = new WebappContext("OAuthServer Context");

			FilterRegistration testFilterReg1 = wContext.addFilter("EntryFilter", AuthEntryFilter.class);			
			testFilterReg1.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), "/*");
			
			ServletRegistration sRegistration = wContext.addServlet("Jersey", ServletContainer.class);
			sRegistration.setInitParameter(ServerProperties.PROVIDER_PACKAGES,
					"org.simplity.examples.troubleTicketDemo.OAuthServer.endpoints");
			sRegistration.setInitParameter(ServerProperties.PROVIDER_SCANNING_RECURSIVE, "false");
			sRegistration.addMapping("auth");

			server = GrizzlyHttpServerFactory.createHttpServer(new URI("http://localhost:8090"));
			wContext.deploy(server);

			HttpHandler httpHandler = new CLStaticHttpHandler(HttpServer.class.getClassLoader(), "/webapp/");
			server.getServerConfiguration().addHttpHandler(httpHandler, "/");

			server.start();
			Thread.currentThread().join();
		} catch (Exception e) {
			e.printStackTrace();
			server.shutdown();
		}
	}
}
