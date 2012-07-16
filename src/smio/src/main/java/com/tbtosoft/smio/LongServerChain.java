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
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ServerChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 * @author chengchun
 *
 */
public class LongServerChain<E, T extends ICoder<E>> extends BasicChain {
	private final SocketAddress localAddress;
	private ServerChannelFactory serverChannelFactory;
	private ServerBootstrap serverBootstrap;
	public LongServerChain(SocketAddress address){
		this.localAddress = address;
		this.serverChannelFactory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), 
				Executors.newCachedThreadPool());
		this.serverBootstrap = new ServerBootstrap(this.serverChannelFactory);
		this.serverBootstrap.bind(localAddress);
	}
}
