package org.simplity.examples;

import java.io.File;
import java.net.URI;
import java.util.EnumSet;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.simplity.examples.filter.CorsFilter;
import org.simplity.examples.filter.CSPEntryFilter;
import org.simplity.kernel.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CSPServiceMain {
	final static Logger logger = LoggerFactory.getLogger(CSPServiceMain.class);
	public static HttpServer server;

	public static void main(String[] args) {

		try {
			File jarPath = new File(CSPServiceMain.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			String folder = jarPath.getParent() + File.separator + "comp" + File.separator;

			try {
				Application.bootStrap(folder);
				CSPServiceConfig.setApiPath(folder + "openapi" + File.separator + "csp_troubleTicket.json");
			} catch (Exception e) {
				logger.error("error while bootstrapping with compFolder=" + folder,e);
				return;
			}

			ResourceConfig rc = new CSPServiceConfig();
			rc.register(CSPEntryFilter.class);
			rc.register(CorsFilter.class);

			server = GrizzlyHttpServerFactory.createHttpServer(new URI("http://localhost:8087/api"), rc);
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
