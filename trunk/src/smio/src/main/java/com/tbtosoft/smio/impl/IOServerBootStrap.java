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

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelHandler;

/**
 * @author chengchun
 *
 */
public class IOServerBootStrap extends IOBootstrap {
	private final ServerBootstrap serverBootstrap;
	public IOServerBootStrap(ChannelFactory channelFactory){
		this.serverBootstrap = new ServerBootstrap(channelFactory);
		this.serverBootstrap.setPipelineFactory(getInnerChannelPipelineFactory());
	}
	public void setOption(String key, Object value) {
		this.serverBootstrap.setOption(key, value);
	}
	public Channel bind() {
		return this.serverBootstrap.bind();
	}
	public Channel bind(final SocketAddress localAddress) {
		return this.serverBootstrap.bind(localAddress);
	}	
	public void setParentHandler(ChannelHandler parentHandler) {
		 this.serverBootstrap.setParentHandler(parentHandler);
	}
	public ChannelHandler getParentHandler() {
		return this.serverBootstrap.getParentHandler();
	}
	@Override
	protected void releaseExternalResourcesImpl() {
		this.serverBootstrap.getFactory().releaseExternalResources();
	}
}
