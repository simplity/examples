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

import org.simplity.kernel.Tracer;
import org.simplity.service.ServiceCacheManager;
import org.simplity.service.ServiceData;
import org.simplity.service.ServiceProtocol;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

/**
 * this is a singleton by design. An instance is cached by HttpAgent and reused.
 *
 * @author simplity.org
 *
 */
public class SimpleMemCacheManager implements ServiceCacheManager {

	SockIOPool pool;
	MemCachedClient mcc;

	public SimpleMemCacheManager() {
		pool = SockIOPool.getInstance("default");
		String[] servers = { "localhost:11213" };
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
		String fieldsForHash = inData.getCacheForInput();		
		ServiceData outData = (ServiceData) mcc.get(this.getHash(serviceName,inData,fieldsForHash));
		Tracer.trace("Responding from cache");
		return outData;
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
		Tracer.trace("Added to trace");
		mcc.add(this.getHash(serviceName,inData,fieldsForHash), outData);

	}

	@Override
	public void invalidate(String serviceName,ServiceData inData) {
		Tracer.trace("Invalidate entry for viewTodos");
		System.out.println(mcc.delete("viewTodos"));
	}

}
