package org.simplity.examples.troubleTicketUI;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.simplity.examples.troubleTicketUI.filter.CorsFilter;
import org.simplity.examples.troubleTicketUI.filter.TTUIEntryFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TTUIMain {
	final static Logger logger = LoggerFactory.getLogger(TTUIMain.class);
	public static HttpServer server;
	public static Map<String,String[]> internalCache = new HashMap<String,String[]>();
	public static void main(String[] args) {

		try {
			File jarPath = new File(TTUIMain.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			String folder = jarPath.getParent() + File.separator + "comp" + File.separator;

			try {
				TTUIConfig.setApiPath(folder + "openapi" + File.separator + "ttui_troubleTicket.json");
			} catch (Exception e) {
				logger.error("error while bootstrapping with compFolder=" + folder,e);
				return;
			}
			ResourceConfig rc = new TTUIConfig();
			rc.register(TTUIEntryFilter.class);			
			rc.register(CorsFilter.class);			

			server = GrizzlyHttpServerFactory.createHttpServer(new URI("http://localhost:8095/api"), rc);
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
