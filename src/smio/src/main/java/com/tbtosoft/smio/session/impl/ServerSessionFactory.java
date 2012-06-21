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
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.tbtosoft.smio.session.ISession;

/**
 * @author chengchun
 *
 */
public class ServerSessionFactory extends BasicSessionFactory {
	private ServerBootstrap bootstrap;
	public ServerSessionFactory(){
		bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool()));		
	}
	
	@Override
	protected ISession newSessionImpl(SocketAddress socketAddress,
			Object attachment) {
		ServerSession serverSession = new ServerSession(socketAddress);
		serverSession.setAttachment(attachment);
		bootstrap.bind(socketAddress);
		return serverSession;
	}

}
