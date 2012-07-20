/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio;

import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ServerChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 * @author chengchun
 *
 */
public class IoServer {
	private ServerChannelFactory channelFactory;
	private ServerBootstrap serverBootstrap;
	public IoServer(){
		this.channelFactory = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());
		this.serverBootstrap = new ServerBootstrap(this.channelFactory);
	}	
}
