package org.simplity.examples.troubleTicketDemo.OAuthServer.filters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;

public class AuthEntryFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String correlationId;
		if ((correlationId = request.getParameter("correlationId")) == null) {
			correlationId = genCorrelationId();
		}
		MDC.put("correlationId", correlationId);
			
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		MDC.remove("correlationId");
	}

	private String genCorrelationId() {
		String uuidGen = UUID.randomUUID().toString();
		return uuidGen.substring(uuidGen.length() - 9);

	}
}