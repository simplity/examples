package org.simplity.examples.util;

import java.util.logging.Level;

import org.simplity.examples.model.CacheObject;
import org.simplity.http.HttpAgent;
import org.simplity.kernel.Tracer;
import org.simplity.kernel.comp.ComponentManager;
import org.simplity.kernel.data.InputField;
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
	SockIOPool pool;
	MemCachedClient mcc;
	private String[] fieldNames;

	public SimpleMemCacheManager() {
		pool = SockIOPool.getInstance("default");
		String[] servers = { "localhost:11211" };
		pool.setServers(servers);
		pool.setFailover(true);
		pool.setInitConn(10);
		pool.setMinConn(5);
		pool.setMaxConn(250);
		pool.setMaintSleep(30);
		pool.setNagle(false);
		pool.setSocketTO(3000);
		pool.setAliveCheck(true);
		pool.initialize();
		mcc = new MemCachedClient("default");		
	}

	@Override
	public ServiceData respond(ServiceData inData) {
		String serviceName = inData.getServiceName();
		CacheObject object = (CacheObject) mcc.get(getInDataKey(serviceName,inData));
		logger.info("Responding from cache");
		if(object != null){
			return object.getOutData();
		}
		return null;
	}

	@Override
	public void cache(ServiceData inData, ServiceData outData) {
		// Get the Memcached Client from SockIOPool named Test1
		logger.info("Added to cache");
		String serviceName = inData.getServiceName();
		CacheObject object = new CacheObject(inData, outData, serviceName);
		boolean added = mcc.add(getInDataKey(inData.getServiceName(),inData), object);
		System.out.println(added);
	}

	@Override
	public void invalidate(String serviceName,ServiceData inData) {
		logger.info("Invalidate entry for "+serviceName);
		System.out.println(mcc.delete(getInDataKey(serviceName,inData)));
	}

	/**
	 * generate index key based on input field values
	 *
	 * @param text
	 * @return
	 */
	private String getInDataKey(String serviceName, ServiceData inData) {
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
	}	
}
