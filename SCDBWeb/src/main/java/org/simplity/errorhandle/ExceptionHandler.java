package org.simplity.errorhandle;


import org.simplity.http.HttpAgent;
import org.simplity.kernel.value.Value;
import org.simplity.service.ExceptionListener;
import org.simplity.service.ServiceContext;
import org.simplity.service.ServiceData;
import org.simplity.tp.LogicInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionHandler implements ExceptionListener {
	private static final Logger logger = LoggerFactory.getLogger(HttpAgent.class);


	@Override
	public void listen(ServiceData inputData, Exception e) {
		logger.error("Error", e);

	}

}
