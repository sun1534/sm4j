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

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

/**
 * @author chengchun
 *
 */
class IOClient {
	private SocketAddress serverAddress;
	private ClientBootstrap clientBootstrap;
	private ChannelFactory clientChannelFactory;
				
	public IOClient(SocketAddress socketAddress){
		this.serverAddress = socketAddress;
		clientChannelFactory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());
		clientBootstrap = new ClientBootstrap(clientChannelFactory);		
	}
	public void setPipelineFactory(ChannelPipelineFactory pipelineFactory){
		clientBootstrap.setPipelineFactory(pipelineFactory);
	}
}
