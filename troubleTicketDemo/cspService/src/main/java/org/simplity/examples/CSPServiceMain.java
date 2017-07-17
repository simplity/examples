package org.simplity.examples;

import java.io.File;
import java.net.URI;

import javax.servlet.ServletRegistration;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.simplity.kernel.Application;
import org.simplity.rest.Operations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CSPServiceMain {
	public static HttpServer server;
	final static Logger logger = LoggerFactory.getLogger(CSPServiceMain.class);

	public static void main(String[] args) {

		try {
			File jarPath = new File(CSPServiceMain.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			String folder = jarPath.getParent() + File.separator + "comp" + File.separator;

			try {
				Application.bootStrap(folder);
				Operations.loadAll(folder+"/openapi/");
			} catch (Exception e) {
				logger.error("error while bootstrapping with compFolder=" + folder,e);
				return;
			}
			WebappContext wContext = new WebappContext("Car Context");
			
			ServletRegistration rRegistration = wContext.addServlet("RestSimplity", org.simplity.rest.Serve.class);
			rRegistration.addMapping("api");

			server = GrizzlyHttpServerFactory.createHttpServer(new URI("http://localhost:8087"));
			wContext.deploy(server);

			HttpHandler httpHandler = new CLStaticHttpHandler(HttpServer.class.getClassLoader(), "/webapp/");
			server.getServerConfiguration().addHttpHandler(httpHandler, "/");

			server.start();
			Thread.currentThread().join();
		} catch (Exception e) {
			logger.error("Error in Service",e);
			server.shutdown();
		}
	}
}
