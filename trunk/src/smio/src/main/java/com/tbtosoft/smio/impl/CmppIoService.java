/**
 * Copyright(C) 2011-2012 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio.impl;

import java.nio.channels.Channel;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

/**
 * @author chun.cheng
 *
 */
public class CmppIoService extends AbstractSmService<com.tbtosoft.cmpp.IPackage> {
	private NioClientSocketChannelFactory channelFactory = new NioClientSocketChannelFactory(
            Executors.newCachedThreadPool(),
            Executors.newCachedThreadPool());
	private ClientBootstrap bootstrap = new ClientBootstrap(channelFactory);
	private ChannelGroup channelGroup = new DefaultChannelGroup();
	public CmppIoService(){
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				//pipeline.addLast("cmpp-decoder", new CmppDecoder());
				return pipeline;
			}
		});
	}
	@Override
	protected boolean onStart() {
//		bootstrap.connect(InetSocketAddress.))
		return false;
	}
	@Override
	protected void onStop() {
		if(null != this.bootstrap){
			this.bootstrap.releaseExternalResources();
		}
	}
	class Client{
		private Channel channel;
		public Client(Channel channel){
			this.channel = channel;
		}
		
	}
	class ClientPiplelineFactory implements ChannelPipelineFactory{

		@Override
		public ChannelPipeline getPipeline() throws Exception {
			ChannelPipeline pipeline = Channels.pipeline();
			return null;
		}
		
	}
}
