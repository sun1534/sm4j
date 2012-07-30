/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smsp.sgip;

import java.io.IOException;
import java.net.InetSocketAddress;

import junit.framework.TestCase;

/**
 * @author chengchun
 *
 */
public class ServiceProviderTest extends TestCase {
	public void testServiceProvider() throws IOException{
		ServiceProvider serviceProvider = new ServiceProvider(new InetSocketAddress("127.0.0.1", 8802), new InetSocketAddress("127.0.0.1", 8801));
		serviceProvider.start();
		System.in.read();
		serviceProvider.stop();
	}
}
