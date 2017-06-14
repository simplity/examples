package org.simplity.examples;

import java.net.URL;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;


public class TTUIMain {

	public static void main(String[] args) {
		
		String baseStr = "/webapp";
		URL baseUrl = TTUIMain.class.getResource(baseStr);
		String basePath = baseUrl.toExternalForm();

		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setResourceBase(basePath);
		resourceHandler.setWelcomeFiles(new String[]{ "index.html" });
		
		final Server server = new Server(8090);
		server.setHandler(resourceHandler);
		try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}