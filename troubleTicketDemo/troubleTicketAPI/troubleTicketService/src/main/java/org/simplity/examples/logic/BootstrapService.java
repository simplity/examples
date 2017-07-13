package org.simplity.examples.logic;

import org.simplity.kernel.value.Value;
import org.simplity.service.JavaAgent;
import org.simplity.service.PayloadType;
import org.simplity.service.ServiceContext;
import org.simplity.service.ServiceData;
import org.simplity.tp.LogicInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BootstrapService implements LogicInterface {
	static final Logger logger = LoggerFactory.getLogger(BootstrapService.class);
	
	@SuppressWarnings("unused")
	@Override
	public Value execute(ServiceContext ctx) {
		logger.info("Running BootstrapService");
		ServiceData troubleTicketFindOutData = JavaAgent.getAgent("100", null).serve("troubleTicketFind", null, PayloadType.JSON);
		return Value.VALUE_TRUE;
	}

}