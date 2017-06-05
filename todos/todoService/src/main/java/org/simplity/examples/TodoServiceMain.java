package org.simplity.examples;

import java.io.File;
import org.glassfish.grizzly.http.server.HttpServer;
import org.simplity.kernel.Application;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;


public class TodoServiceMain {
		public static HttpServer server;
		public static void main(String[] args) {

		try {
			
			PackagesResourceConfig rc = new  PackagesResourceConfig("org.simplity.examples");
			rc.getContainerResponseFilters().add(new CorsFilter());
			server =  GrizzlyServerFactory.createHttpServer("http://localhost:8082", rc);	 
	
			
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
			System.out.println(e.getMessage());
			server.stop();
		}
	}
}
