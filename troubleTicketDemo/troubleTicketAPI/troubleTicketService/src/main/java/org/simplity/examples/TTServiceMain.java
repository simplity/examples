package org.simplity.examples;

import java.io.File;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.simplity.examples.filter.CorsFilter;
import org.simplity.examples.filter.TTServiceEntryFilter;
import org.simplity.kernel.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TTServiceMain {
	final static Logger logger = LoggerFactory.getLogger(TTServiceConfig.class);
	public static HttpServer server;

	public static void main(String[] args) {

		try {
			File jarPath = new File(TTServiceMain.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			String folder = jarPath.getParent() + File.separator + "comp" + File.separator;

			try {
				Application.bootStrap(folder);
				TTServiceConfig.setApiPath(folder + "openapi" + File.separator + "tt_troubleTicket.json");
			} catch (Exception e) {
				logger.error("error while bootstrapping with compFolder=" + folder,e);
				return;
			}
			ResourceConfig rc = new TTServiceConfig();
			rc.register(TTServiceEntryFilter.class);	
			rc.register(CorsFilter.class);			

			server = GrizzlyHttpServerFactory.createHttpServer(new URI("http://localhost:8085/api"), rc);
			
			server.start();
			Thread.currentThread().join();
		} catch (Exception e) {
			logger.error("Error is Service",e);
			server.shutdown();
		}
	}
}
