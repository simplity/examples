package org.simplity.examples.troubleTicketUI;

import java.io.File;
import java.net.URI;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.simplity.examples.troubleTicketUI.filter.CorsFilter;

public class TTUIMain {
	public static HttpServer server;

	public static void main(String[] args) {

		try {
			File jarPath = new File(TTUIMain.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			String folder = jarPath + File.separator + "comp" + File.separator;

			try {
				TTUIConfig.setApiPath(folder + "openapi" + File.separator + "troubleTicket.json");
			} catch (Exception e) {
				System.err.println("error while bootstrapping with compFolder=" + folder);
				e.printStackTrace(System.err);
				return;
			}
			ResourceConfig rc = new TTUIConfig();
			rc.register(CorsFilter.class);			

			server = GrizzlyHttpServerFactory.createHttpServer(new URI("http://localhost:8095/api"), rc);
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
