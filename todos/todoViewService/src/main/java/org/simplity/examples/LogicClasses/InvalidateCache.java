package org.simplity.examples.LogicClasses;

import org.simplity.kernel.value.Value;
import org.simplity.service.ServiceAgent;
import org.simplity.service.ServiceContext;
import org.simplity.tp.LogicInterface;

public class InvalidateCache implements LogicInterface{

	@Override
	public Value execute(ServiceContext ctx) {
		ServiceAgent.invalidateCache(ctx.getServiceName(), null);
		return Value.newBooleanValue(true);
	}

}
