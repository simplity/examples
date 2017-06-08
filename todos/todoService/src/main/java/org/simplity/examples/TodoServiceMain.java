package org.simplity.examples;

import java.io.File;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.simplity.kernel.Application;




public class TodoServiceMain {
		public static HttpServer server;
		public static void main(String[] args) {

		try {
	        ResourceConfig rc = new TodoServiceConfig();
	        //rc.register(CorsFilter.class);
	        
			server = GrizzlyHttpServerFactory.createHttpServer(new URI("http://localhost:8082"),rc);
			File jarPath = new File(TodoServiceMain.class.getProtectionDomain().getCodeSource().getLocation().getPath());			
			String folder = jarPath.getParent()+File.separator+"comp"+File.separator;
			
			try {
				Application.bootStrap(folder);
			} catch (Exception e) {
				System.err.println(
						"error while bootstrapping with compFolder=" + folder);
				e.printStackTrace(System.err);
				return;
			}
			
			server.start();
	        Thread.currentThread().join();	
		} catch (Exception e) {
			e.printStackTrace();
			server.shutdown();
		}
	}
}
