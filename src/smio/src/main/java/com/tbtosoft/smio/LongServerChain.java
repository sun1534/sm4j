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
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.ServerChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

import com.tbtosoft.smio.handlers.ActiveAwareChannelHander;
import com.tbtosoft.smio.utils.ChannelPipeHelper;

/**
 * @author chengchun
 *
 */
public class LongServerChain extends BasicChain {
	private final ICoder coder;
	private final SocketAddress localAddress;
	private final long activeTimeMillis;
	private final Timer timer;
	private ServerChannelFactory serverChannelFactory;
	private ServerBootstrap serverBootstrap;	
	public LongServerChain(SocketAddress address, long activeTimeMillis, ICoder coder){
		this.coder =coder;
		this.localAddress = address;
		this.activeTimeMillis = activeTimeMillis;
		this.timer = new HashedWheelTimer();		
		this.serverChannelFactory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), 
				Executors.newCachedThreadPool());
		this.serverBootstrap = new ServerBootstrap(this.serverChannelFactory);		
		this.serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();		
				pipeline.addLast("DECODER", new Decoder(LongServerChain.this.coder));				
				pipeline.addLast("IDLE-STATE-HANDLER", new IdleStateHandler(LongServerChain.this.timer, 0, 0, LongServerChain.this.activeTimeMillis, TimeUnit.MILLISECONDS));				
				pipeline.addLast("ACTIVE-AWARE-HANDLER",new ActiveAwareChannelHander());				
				ChannelPipeHelper.addLast(pipeline, getSmsHandlerFactory().getPipeline());				
				pipeline.addLast("ENCODER", new Encoder(LongServerChain.this.coder));
				return pipeline;
			}
		});
		this.serverBootstrap.bind(localAddress);
	}
	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.BasicChain#open()
	 */
	@Override
	public boolean open() {
		//this.serverBootstrap.bind(localAddress);
		return true;
	}
	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.BasicChain#close()
	 */
	@Override
	public void close() {		
		this.serverBootstrap.releaseExternalResources();
	}	
}
