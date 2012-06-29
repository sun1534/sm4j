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

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.tbtosoft.smio.session.ISession;

/**
 * @author chengchun
 *
 */
public final class ClientSessionFactory extends BasicSessionFactory {
	private ClientBootstrap clientBootstrap;
//	private ChannelGroup channelGroup = new DefaultChannelGroup();
//	private ChannelLocal<Object> channelLocal = new ChannelLocal<Object>();
	public ClientSessionFactory(){		
		clientBootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool()));
	}
	
	@Override
	protected ISession newSessionImpl(SocketAddress socketAddress,
			Object attachment) {	
		ClientSession clientSession = new ClientSession();
		
		clientBootstrap.setPipelineFactory(bootstrap.getSmioPipelineFactory());
		
		clientSession.setAttachment(attachment);
		clientSession.setSocketAddress(socketAddress);
		clientSession.setClientSessionFactory(this);
		return clientSession;
	}
	protected ChannelFuture connect(SocketAddress socketAddress){
		
		return clientBootstrap.connect(socketAddress);
	}	
}
