package org.simplity.examples.logic;

import org.simplity.examples.model.CacheValueObject;
import org.simplity.examples.util.SimpleMemCacheManager;
import org.simplity.kernel.data.DataSheet;
import org.simplity.kernel.value.Value;
import org.simplity.service.JavaAgent;
import org.simplity.service.ServiceContext;
import org.simplity.service.ServiceData;
import org.simplity.tp.LogicInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemCacheRefresh implements LogicInterface {
	static final Logger logger = LoggerFactory.getLogger(MemCacheRefresh.class);
	
	@Override
	public Value execute(ServiceContext ctx) {
		DataSheet memcacheKeysSheet = ctx.getDataSheet("memcacheKeysSheet");
		SimpleMemCacheManager simpleMemCacheManager = new SimpleMemCacheManager();

		String serviceName = null;
		ServiceData inData = null;
		for (int i = 0; i < memcacheKeysSheet.length(); i++) {
			String cacheKey = memcacheKeysSheet.getColumnValue("cacheKey", i).toText();
			long refreshTimePeriod = Long.valueOf(memcacheKeysSheet.getColumnValue("refreshTimePeriod", i).toText());
			long lastRefreshTime = Long.valueOf(memcacheKeysSheet.getColumnValue("lastRefreshTime", i).toText());

			if ((System.currentTimeMillis() - lastRefreshTime) >= refreshTimePeriod) {
				CacheValueObject cacheValueObject = (CacheValueObject) simpleMemCacheManager.readMemCacheData(cacheKey);
				if (cacheValueObject != null) {
					serviceName = cacheValueObject.getServiceName();
					inData = cacheValueObject.getInData();
					/*
					 * Invoke cache invalidate to invalidate the key
					 */
					simpleMemCacheManager.invalidate(serviceName, inData);
					/*
					 * Invoke service to set cacheValueObject in the memCache.
					 * Explicitly we don't require to set cacheValueObject in
					 * the memCache.
					 */
					ServiceData outData = JavaAgent.getAgent("100", null).serve(serviceName, inData.getPayLoad());

					if (!outData.hasErrors()) {
						logger.info("Data refreshed successfully in the memcache for the key - " + cacheKey);
					}
					Value updatedLastRefreshTime = Value.newTextValue(String.valueOf(System.currentTimeMillis()));
					memcacheKeysSheet.setColumnValue("lastRefreshTime", i, updatedLastRefreshTime);
				} else {
					logger.info("Data not available in the memcache");
				}
			}
		}
		return null;
	}

}