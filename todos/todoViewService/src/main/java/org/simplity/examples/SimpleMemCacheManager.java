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

import org.simplity.kernel.Tracer;
import org.simplity.service.ServiceCacheManager;
import org.simplity.service.ServiceData;

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
		init();
	}
	
	@Override
	public ServiceData respond(ServiceData inData) {
		String serviceName = inData.getServiceName();
		Tracer.trace("Responding from cache");
		ServiceData outData = (ServiceData) memCachedClient.get(serviceName);
		return outData;
	}

	private void init() {
		sockIOPool = SockIOPool.getInstance("default");
		String[] servers = {"127.0.0.1:11211"};
		sockIOPool.setServers( servers );
		sockIOPool.setFailover( true );
		sockIOPool.setInitConn( 10 );
		sockIOPool.setMinConn( 5 );
		sockIOPool.setMaxConn( 250 );
		sockIOPool.setMaintSleep( 30 );
		sockIOPool.setNagle( false );
		sockIOPool.setSocketTO( 3000 );
		sockIOPool.setAliveCheck( true );
		sockIOPool.initialize();
		memCachedClient = new MemCachedClient("default");
	}

	@Override
	public void cache(ServiceData inData, ServiceData outData) {
		memCachedClient.add(inData.getServiceName(), outData);
	}

	@Override
	public void invalidate(String serviceName) {
		
	}

}

