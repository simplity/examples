/*
 * Copyright (c) 2016 simplity.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.simplity.examples;

import java.util.Arrays;

import org.omg.IOP.ServiceContext;
import org.simplity.kernel.Tracer;
import org.simplity.kernel.comp.ComponentManager;
import org.simplity.kernel.value.Value;
import org.simplity.service.JavaAgent;
import org.simplity.service.PayloadType;
import org.simplity.service.ServiceCacheManager;
import org.simplity.service.ServiceData;
import org.simplity.service.ServiceInterface;
import org.simplity.service.ServiceProtocol;
import org.simplity.tp.Service;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

/**
 * this is a singleton by design. An instance is cached by HttpAgent and reused.
 *
 * @author simplity.org
 *
 */
public class SimpleMemCacheManager implements ServiceCacheManager {

	private SockIOPool sockIOPool;
	private MemCachedClient memCachedClient;

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
		String fieldsForHash = inData.getCacheForInput();		
		CacheValueObject cacheValueObject = (CacheValueObject) memCachedClient.get(this.getHash(serviceName,inData,fieldsForHash));
		if(cacheValueObject != null) {
			ServiceData outData = cacheValueObject.getOutData();
			Tracer.trace("Responding from cache");
			return outData;
		}
		return null;
	}

	private String getHash(String serviceName, ServiceData inData, String fieldsForHash) {
		int hashKey = serviceName.hashCode();
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
		return Integer.toString(hashKey);
	}

	@Override
	public void cache(ServiceData inData, ServiceData outData) {
		// Get the Memcached Client from SockIOPool named Test1
		String serviceName = inData.getServiceName();
		String fieldsForHash = inData.getCacheForInput();
		CacheValueObject cacheValueObject = new CacheValueObject();
		cacheValueObject.setServiceName(serviceName);
		cacheValueObject.setInData(inData);
		cacheValueObject.setOutData(outData);
		String cacheKey = this.getHash(serviceName, inData, fieldsForHash);
		if(memCachedClient.add(cacheKey, cacheValueObject)) {
			Tracer.trace("Added to cache");
			
			Service service = (Service) ComponentManager.getServiceOrNull(serviceName);
			int cacheRefreshTime = Integer.valueOf(service.getCacheRefreshTime());
			String payLoad = "{'cacheKey':'" + cacheKey + "',"
					+ "'refreshTimePeriod':" + cacheRefreshTime + ","
					+ "'lastRefreshTime':" + String.valueOf(System.currentTimeMillis()) + "}";
			ServiceData outData1 = JavaAgent.getAgent("100", null).serve("memCacheAddKey", payLoad, PayloadType.JSON);
			if(!outData1.hasErrors())
				Tracer.trace("cache key added to the database");
		} else {
			Tracer.trace("Error in adding data to the cache");
		}
	}

	@Override
	public void invalidate(String cacheKey, ServiceData inData) {
		Tracer.trace("Invalidate entry for viewTodos");
		if(memCachedClient.delete(cacheKey))
			Tracer.trace("viewTodos - key deleted");
		else
			Tracer.trace("viewTodos - unable to delete the key");
	}

	public Object readMemCacheData(String key) {
		return this.memCachedClient.get(key);
	}
}
