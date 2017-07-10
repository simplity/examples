package org.simplity.examples.troubleTicketUI.filter;

import java.io.IOException;
import java.util.UUID;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

import org.slf4j.MDC;

@Provider
@PreMatching
public class TTUIEntryFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext request) throws IOException {

		String correlationId;
		if ((correlationId = request.getUriInfo().getQueryParameters().getFirst("correlationId")) == null) {
			correlationId = genCorrelationId();
		}
		MDC.put("correlationId", correlationId);
	}
	
	private String genCorrelationId() {
		String uuidGen = UUID.randomUUID().toString();
		return uuidGen.substring(uuidGen.length() - 9);

	}
}
