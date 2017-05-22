package org.simplity.examples;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;


public class TodosUIMain {

	public static void main(String[] args) {
		ContextHandlerCollection contexts = new ContextHandlerCollection();
		
		contexts.setHandlers(new Handler[] 
			{ new AppContextBuilder().buildWebAppContext()});
		
		final JettyServer server = new JettyServer();
		server.setHandler(contexts);
		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}