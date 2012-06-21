/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio;

import java.net.InetSocketAddress;

import com.tbtosoft.smio.session.ISession;
import com.tbtosoft.smio.session.impl.ClientSessionFactory;

import junit.framework.TestCase;

/**
 * @author chengchun
 *
 */
public class ClientBootstrapTest extends TestCase {
	public void testClientBootstrap(){
		ClientBootstrap clientBootstrap = new ClientBootstrap(new ClientSessionFactory());
//		clientBootstrap.set
		ISession mtISession = clientBootstrap.createSession(new InetSocketAddress("127.0.1", 7890), null);
		ISession moISession = clientBootstrap.createSession(new InetSocketAddress("127.0.1", 7890), null);
	}
}
