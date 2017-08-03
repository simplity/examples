package org.simplity.examples.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.simplity.examples.util.TtTroubleTicket;

public class TestFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		org.simplity.examples.util.TtTroubleTicket.TroubleTicket.Builder a = TtTroubleTicket.TroubleTicket.newBuilder();
		a.setCorrelationId("123");
		a.build().writeTo(response.getOutputStream());
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
