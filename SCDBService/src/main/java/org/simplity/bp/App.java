package org.simplity.bp;

import java.io.File;
import java.net.URI;

import javax.servlet.ServletRegistration;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.simplity.apiscdb.ScdbApi.ContractHeader;
import org.simplity.apiscdb.ScdbApi.ContractHeaders;
import org.simplity.kernel.Application;
import org.simplity.rest.Operations;
import org.simplity.rest.Serve;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
	public static HttpServer server;
	final static Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		try {
			File jarPath = new File(App.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			String folder = jarPath.getParent() + File.separator + "comp" + File.separator;

			try {
				Application.bootStrap(folder);
				Operations.loadAll(folder + "/openapi/");				
			} catch (Exception e) {
				logger.error("error while bootstrapping with compFolder=" + folder, e);
				return;
			}

			
			WebappContext wContext = new WebappContext("SCDB Context");
			
			ServletRegistration rRegistration = wContext.addServlet("RestSimplity", org.simplity.rest.Serve.class);
			rRegistration.addMapping("/scdb/*");
	

			server = GrizzlyHttpServerFactory.createHttpServer(new URI("http://localhost:8070"));
			wContext.deploy(server);

			server.getServerConfiguration().addHttpHandler(new CLStaticHttpHandler(HttpServer.class.getClassLoader(), "./webapp/"),"/webapp/*");
			
			Serve.startUsingProto();
			Operations.setProtoClassPrefix("org.simplity.apiscdb.ScdbApi$");

			server.start();
			Thread.currentThread().join();
			

		} catch (Exception e) {
			logger.error("Error in Service", e);
			server.shutdown();
		}
	}
	
}