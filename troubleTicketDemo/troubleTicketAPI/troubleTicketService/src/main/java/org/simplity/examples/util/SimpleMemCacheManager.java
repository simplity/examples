package org.simplity.examples.util;

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
	SockIOPool pool;
	MemCachedClient mcc;

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
		ServiceData outData = (ServiceData) mcc.get(serviceName);
		Tracer.trace("Responding from cache");
		return outData;
	}

	@Override
	public void cache(ServiceData inData, ServiceData outData) {
		// Get the Memcached Client from SockIOPool named Test1
		Tracer.trace("Added to trace");
		mcc.add(inData.getServiceName(), outData);

	}

	@Override
	public void invalidate(String serviceName) {
		Tracer.trace("Invalidate entry for viewTodos");
		System.out.println(mcc.delete("viewTodos"));
	}

}
