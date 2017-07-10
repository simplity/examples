package org.simplity.examples;

import java.io.File;
import java.net.URI;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.simplity.examples.filter.CorsFilter;
import org.simplity.examples.filter.HubEntryFilter;
import org.simplity.kernel.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HubMainService {
	final static Logger logger = LoggerFactory.getLogger(HubMainService.class);
	public static HttpServer server;		
	public static void main(String[] args) {
		try {
			File jarPath = new File(HubMainService.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			String folder = jarPath.getParent() + File.separator + "comp" + File.separator;
			try {
				Application.bootStrap(folder);
				HubServiceConfig.setApiPath(folder + "openapi" + File.separator + "hub.json");
			} catch (Exception e) {
				logger.error("error while bootstrapping with compFolder=" + folder);
				e.printStackTrace(System.err);
				return;
			}

			ResourceConfig rc = new HubServiceConfig();
			rc.register(HubEntryFilter.class);
			rc.register(CorsFilter.class);

			server = GrizzlyHttpServerFactory.createHttpServer(new URI("http://localhost:8088/api"), rc);
			HttpHandler httpHandler = new CLStaticHttpHandler(HttpServer.class.getClassLoader(), "/webapp/");
			server.getServerConfiguration().addHttpHandler(httpHandler, "/");
			
			server.start();
			Thread.currentThread().join();
		} catch (Exception e) {
			logger.error("Error in service",e);
			server.shutdown();
		}
	}
}
