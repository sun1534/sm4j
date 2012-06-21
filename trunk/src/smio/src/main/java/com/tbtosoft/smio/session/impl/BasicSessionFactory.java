/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio.session.impl;

import java.net.SocketAddress;

import org.jboss.netty.bootstrap.Bootstrap;

import com.tbtosoft.smio.session.ISession;
import com.tbtosoft.smio.session.ISessionFactory;

/**
 * @author chengchun
 *
 */
abstract class BasicSessionFactory implements ISessionFactory{

	protected Bootstrap bootstrap;
	
	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.session.ISessionFactory#setBootstrap(org.jboss.netty.bootstrap.Bootstrap)
	 */
	@Override
	public void setBootstrap(Bootstrap bootstrap) {
		this.bootstrap = bootstrap;
	}
	
	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.session.ISessionFactory#newSession(java.net.SocketAddress, java.lang.Object)
	 */
	@Override
	public ISession newSession(SocketAddress socketAddress, Object attachment) {
		
		return newSessionImpl(socketAddress, attachment);
	}
	protected abstract ISession newSessionImpl(SocketAddress socketAddress, Object attachment);	
}
