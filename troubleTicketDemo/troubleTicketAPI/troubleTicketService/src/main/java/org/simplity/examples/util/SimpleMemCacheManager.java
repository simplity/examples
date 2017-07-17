package org.simplity.examples.util;

import org.simplity.examples.model.CacheValueObject;
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
	public ServiceData respond(ServiceData inData) {
		String serviceName = inData.getServiceName();
		String cacheInputKey = this.getHash(serviceName,inData);
		Object obj = memCachedClient.get(cacheInputKey);
		if(obj != null) {
			CacheValueObject cacheValueObject = (CacheValueObject) obj;
			ServiceData outData = cacheValueObject.getOutData();
			logger.info("Responding from cache");
			return outData;
		}
		return null;
	}

	/**
	 * generate index key based on input field values
	 *
	 * @param text
	 * @return
	 */
	private String getHash(String serviceName, ServiceData inData) {
		StringBuilder sbf = new StringBuilder();
		String fieldsToCache = null;
		Service service = (Service) ComponentManager.getServiceOrNull(serviceName);
		if (service != null) {
			fieldsToCache = service.getFieldsToCache();
			if (fieldsToCache != null) {
				if (fieldsToCache.isEmpty()) {
					int i = 0;
					InputField[] inputFields = null;
					if(service.getInputData() != null){
						inputFields = service.getInputData().getInputFields();
					}
					if(inputFields != null){
						for (InputField field : service.getInputData().getInputFields()) {
							this.fieldNames[i] = field.getName();
							i++;
						}
					}
				} else {
					this.fieldNames = fieldsToCache.split(",");
				}
			}
		} else {
			return null;
		}
		sbf.append(serviceName);
		if(this.fieldNames != null){
			for (String nam : this.fieldNames) {
				Object obj = inData.get(nam);
				if (obj != null) {
					sbf.append(obj);
				}
			}
		}
		return String.valueOf(sbf.toString().hashCode()); 
		 
		 
		/*int hashKey = serviceName.hashCode();
		String[] fields = null;
		if (fieldsForHash.length() > 0) {
			fields = fieldsForHash.split(",");
			if (fields[0].equals(ServiceProtocol.USER_ID)) {
				hashKey += inData.getUserId().hashCode();
				int n = fields.length;
				if (n > 0) {
					n--;
					String[] newFields = new String[n];
					for (int i = 0; i < newFields.length; i++) {
						newFields[i] = fields[i + 1];
					}
					fields = newFields;
				}
			}
			for(String field:fields){
				hashKey += inData.get(field).hashCode();
			}
		}
		hashKey += Arrays.hashCode(fields);
		hashKey = Math.abs(hashKey);
		return String.valueOf(hashKey);*/
	}
	
	@Override
	public void cache(ServiceData inData, ServiceData outData) {
		// Get the Memcached Client from SockIOPool named Test1
		
		String serviceName = inData.getServiceName();
		CacheValueObject cacheValueObject = new CacheValueObject();
		cacheValueObject.setServiceName(serviceName);
		cacheValueObject.setInData(inData);
		cacheValueObject.setOutData(outData);
		String cacheKey = this.getHash(serviceName, inData);
		boolean successFlag = false;
		if(memCachedClient.keyExists(cacheKey)) {
			memCachedClient.set(cacheKey, cacheValueObject);
			successFlag = true;
		} else {
			memCachedClient.add(cacheKey, cacheValueObject);
			successFlag = true;
		}
		if(successFlag) {
			logger.info("Added to cache");
			Service service = (Service) ComponentManager.getServiceOrNull(serviceName);
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
	public void invalidate(String serviceName, ServiceData inData) {
		String cacheKey = this.getHash(serviceName,inData);
		logger.info("Invalidate entry");
		if(memCachedClient.delete(cacheKey))
			logger.info("viewTodos - key deleted");
		else
			logger.info("viewTodos - unable to delete the key");
	}

	public Object readMemCacheData(String key) {
		Object obj = this.memCachedClient.get(key);
		return obj;
	}
		
}
