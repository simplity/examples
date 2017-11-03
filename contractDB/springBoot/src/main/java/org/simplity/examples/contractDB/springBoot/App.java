package org.simplity.examples.contractDB.springBoot;

import java.io.File;

import org.simplity.kernel.Application;
import org.simplity.rest.Operations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class App {
	final static Logger logger = LoggerFactory.getLogger(App.class);

	@Bean
	public DispatcherServlet dispatcherServlet() {
		return new DispatcherServlet();
	}

	@Bean
	public ServletRegistrationBean dispatchServletRegistration() {
		ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet(), "/*");
		registration.setName(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
		return registration;
	}
	
	@Bean
	public ServletRegistrationBean servletRegistrationBean(){
		ServletRegistrationBean registration =  new ServletRegistrationBean(new org.simplity.rest.Serve(),"/scdb/*");
		registration.setName("RestSimplity");
		return registration;
	}	

	public static void main(String[] args) throws Exception {
		File jarPath = new File(App.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String folder = jarPath.getParent() + File.separator + "classes/comp" + File.separator;

		try {
			Application.bootStrap(folder);
			Operations.loadAll(folder + "/openapi/");
		} catch (Exception e) {
			logger.error("error while bootstrapping with compFolder=" + folder, e);
			return;
		}
		
		//Serve.startUsingProto();
		Operations.setProtoClassPrefix("org.simplity.examples.contractDB.apiscdb.ScdbApi$");		

		SpringApplication.run(App.class, args);
	}
}
