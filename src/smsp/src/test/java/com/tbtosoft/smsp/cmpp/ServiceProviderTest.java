package com.tbtosoft.smsp.cmpp;

import java.io.IOException;
import java.net.InetSocketAddress;

import junit.framework.TestCase;

public class ServiceProviderTest extends TestCase {
	public void testServiceProvider() throws IOException{
		ServiceProvider serviceProvider = new ServiceProvider(new InetSocketAddress("127.0.0.1", 7890));
		serviceProvider.start();
		System.in.read();
		serviceProvider.stop();
	}
}
