package org.simplity.examples;

import org.simplity.kernel.comp.ComponentManager;
import org.simplity.service.InputField;
import org.simplity.service.JavaAgent;
import org.simplity.service.PayloadType;
import org.simplity.service.ServiceCacheManager;
import org.simplity.service.ServiceData;
import org.simplity.tp.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

/**
 * this is a singleton by design. An instance is cached by HttpAgent and reused.
 *
 * @author simplity.org
 *
 */
public class SimpleMemCacheManager implements ServiceCacheManager {
	static final Logger logger = LoggerFactory.getLogger(SimpleMemCacheManager.class);
	private SockIOPool sockIOPool;
	private MemCachedClient memCachedClient;
	private String[] fieldNames;
	
	public SimpleMemCacheManager() {
		sockIOPool = SockIOPool.getInstance("default");
		String[] servers = { "localhost:11211" };
		sockIOPool.setServers(servers);
		sockIOPool.setFailover(true);
		sockIOPool.setInitConn(10);
		sockIOPool.setMinConn(5);
		sockIOPool.setMaxConn(250);
		sockIOPool.setMaintSleep(30);
		sockIOPool.setNagle(false);
		sockIOPool.setSocketTO(3000);
		sockIOPool.setAliveCheck(true);
		sockIOPool.initialize();

		memCachedClient = new MemCachedClient("default");		
	}

	@Override
	public ServiceData respond(String key) {
		CacheValueObject obj = (CacheValueObject) memCachedClient.get(key);		
		if(obj != null){
			logger.info("Responding from cache");
			return obj.getOutData();
		}
		return null;
	}


	@Override
	public void cache(ServiceData inputData,ServiceData outData) {
		// Get the Memcached Client from SockIOPool named Test1
		CacheValueObject obj = new CacheValueObject(inputData, outData, inputData.getServiceName());
		String cacheKey = outData.getCacheKey();
		boolean successFlag = false;
		if(memCachedClient.keyExists(cacheKey)) {
			memCachedClient.set(cacheKey, obj);
			successFlag = true;
		} else {
			memCachedClient.add(cacheKey, obj);
			successFlag = true;
		}
		if(successFlag) {
			logger.info("Added to cache");
			Service service = (Service) ComponentManager.getServiceOrNull(outData.getServiceName());
			if(service.getCacheRefreshTime() != null) {
				int cacheRefreshTime = Integer.valueOf(service.getCacheRefreshTime());
				String payLoad = "{'cacheKey':'" + cacheKey + "',"
						+ "'refreshTimePeriod':" + cacheRefreshTime + ","
						+ "'lastRefreshTime':" + String.valueOf(System.currentTimeMillis()) + "}";
				ServiceData outData1 = JavaAgent.getAgent("100", null).serve("memcache.memCacheAddKey", payLoad, PayloadType.JSON);
				if(!outData1.hasErrors())
					logger.info("cache key added to the database");
			} else {
				logger.info("cache refresh is not enabled for this service");
			}
		} else {
			logger.info("Error in adding data to the cache");
		}
	}

	@Override
	public void invalidate(String key) {
		logger.info("Invalidate entry");
		if(memCachedClient.delete(key))
			logger.info("key "+key+" deleted");
		else
			logger.info("unable to delete the key "+key);
	}

	public Object readMemCacheData(String key) {
		Object obj = this.memCachedClient.get(key);
		return obj;
	}
		
}
