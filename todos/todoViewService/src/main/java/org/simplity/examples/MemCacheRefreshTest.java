package org.simplity.examples;

import org.simplity.kernel.value.Value;
import org.simplity.service.ServiceContext;
import org.simplity.tp.LogicInterface;

public class MemCacheRefreshTest implements LogicInterface {

	@Override
	public Value execute(ServiceContext ctx) {
		System.out.println("running MemCacheRefreshTest");
		try {
			Thread.currentThread().sleep(60000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("running MemCacheRefreshTest - After thread sleep");
		return null;
	}

}