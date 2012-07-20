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

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

/**
 * @author chengchun
 *
 */
public class IoClient {
	private ClientBootstrap clientBootstrap;
	private ChannelFactory channelFactory;
	private ChannelPipelineFactory channelPipelineFactory;
	private ChannelGroup channelGroup = new DefaultChannelGroup("IO-CLIENT");
	public IoClient(){
		this.channelFactory = new NioClientSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());
		this.clientBootstrap = new ClientBootstrap(this.channelFactory);
		this.clientBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {							
				return getChannelPipeline();
			}
		});
	}
	private ChannelPipeline getChannelPipeline() throws Exception{
		ChannelPipeline pipeline = null == this.channelPipelineFactory ? Channels
				.pipeline() : this.channelPipelineFactory.getPipeline();
		pipeline.addFirst("", new SimpleChannelHandler(){

			/* (non-Javadoc)
			 * @see org.jboss.netty.channel.SimpleChannelHandler#channelConnected(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
			 */
			@Override
			public void channelConnected(ChannelHandlerContext ctx,
					ChannelStateEvent e) throws Exception {
				newChannelImcoming(ctx.getChannel());
				super.channelConnected(ctx, e);
			}			
		});
		return pipeline;
	}
	private void newChannelImcoming(Channel channel){
		this.channelGroup.add(channel);
	}
	/**
	 * @param channelPipelineFactory the channelPipelineFactory to set
	 */
	protected final void setChannelPipelineFactory(
			ChannelPipelineFactory channelPipelineFactory) {
		this.channelPipelineFactory = channelPipelineFactory;
	}
	
}
