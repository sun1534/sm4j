/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio;

import java.net.SocketAddress;

import com.tbtosoft.smio.session.ISession;
import com.tbtosoft.smio.session.ISessionFactory;

/**
 * @author chengchun
 *
 */
public final class ServerBootstrap extends Bootstrap {
	public ServerBootstrap(){
		super();
	}
	public ServerBootstrap(ISessionFactory sessionFactory){
		super(sessionFactory);
	}
	public ISession createSession(SocketAddress socketAddress, Object attachment){
		return this.getSessionFactory().newSession(socketAddress, attachment);
	}
}
