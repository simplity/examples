package org.simplity.examples;

import java.io.File;
import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.simplity.kernel.Application;

/**
 * Hello world!
 *
 */
public class TodoViewServiceMain {
	public static void main(String[] args) {
		try {
			Server server = new Server(8081);
			File jarPath = new File(TodoViewServiceMain.class.getProtectionDomain().getCodeSource().getLocation().getPath());			
			String folder = jarPath.getParent()+File.separator+"comp"+File.separator;
			
			try {
				Application.bootStrap(folder);
			} catch (Exception e) {
				System.err.println(
						"error while bootstrapping with compFolder=" + folder);
				e.printStackTrace(System.err);
				return;
			}
			ServletContextHandler context =  new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
			context.setContextPath("/");
			server.setHandler(context);

			FilterHolder cors = context.addFilter(CrossOriginFilter.class,"/*",EnumSet.allOf(DispatcherType.class));
			cors.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
			cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET, POST, PUT, DELETE, OPTIONS, HEAD");
			cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,origin, content-type, accept, authorization");
			
			ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
	        jerseyServlet.setInitParameter("jersey.config.server.provider.packages","org.simplity.examples");
			// Create the server level handler list.
			HandlerList handlers = new HandlerList();
			// Make sure DefaultHandler is last (for error handling reasons)
			handlers.setHandlers(new Handler[] { context, new DefaultHandler() });
			server.setHandler(handlers);
			server.start();
			server.join();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
