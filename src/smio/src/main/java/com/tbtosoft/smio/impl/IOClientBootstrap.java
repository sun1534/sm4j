/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio.impl;

import java.net.SocketAddress;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;

/**
 * @author chengchun
 *
 */
public class IOClientBootstrap extends IOBootstrap {	
	private final ClientBootstrap clientBootstrap;	
	public IOClientBootstrap(ChannelFactory channelFactory){
		this.clientBootstrap = new ClientBootstrap(channelFactory);
		this.setChannelPipelineFactory(getInnerChannelPipelineFactory());
		
	}
	public void setOption(String key, Object value) {
		this.clientBootstrap.setOption(key, value);
	}
	public ChannelFuture connect(){		
		return this.clientBootstrap.connect();		
	}
	public ChannelFuture connect(SocketAddress remoteAddress){
		return this.clientBootstrap.connect(remoteAddress);
	}
	public ChannelFuture connect(final SocketAddress remoteAddress, final SocketAddress localAddress) {
		return this.clientBootstrap.connect(remoteAddress, localAddress);
	}
	public ChannelFuture bind(final SocketAddress localAddress) {
		return this.clientBootstrap.bind(localAddress);
	}
	@Override
	protected void releaseExternalResourcesImpl() {
		this.clientBootstrap.getFactory().releaseExternalResources();
	}
	
}
