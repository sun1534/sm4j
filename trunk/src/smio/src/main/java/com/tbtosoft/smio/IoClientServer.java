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
import java.util.concurrent.TimeUnit;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

import com.tbtosoft.smio.handlers.ActiveAwareChannelHandler;
import com.tbtosoft.smio.impl.IOClientBootstrap;
import com.tbtosoft.smio.impl.IOServerBootStrap;

/**
 * @author chengchun
 *
 */
public class IoClientServer extends IoSms implements IClient{	
	private Timer timer;	
	private IOClientBootstrap clientBootstrap;
	private IOServerBootStrap serverBootStrap;	
	public IoClientServer(ICoder coder, SocketAddress localAddress, SocketAddress remoteAddress){
		this.clientBootstrap = new IOClientBootstrap(new NioClientSocketChannelFactory());
		this.clientBootstrap.setCoder(coder);
		this.clientBootstrap.setOption("remoteAddress", remoteAddress);
		this.serverBootStrap = new IOServerBootStrap(new NioServerSocketChannelFactory());
		this.serverBootStrap.setCoder(coder);
		this.serverBootStrap.setOption("localAddress", localAddress);
		this.timer = new HashedWheelTimer();
		this.clientBootstrap.setChannelPipelineFactory(new ChannelPipelineFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("IDLE-STATE", new IdleStateHandler(timer, 0, 0, getActiveTimeMillis(), TimeUnit.MILLISECONDS));
				pipeline.addLast("ACTIVE-AWARE-HANDLER",new ActiveAwareChannelHandler());
				if(null != getSmsHandler()){
					pipeline.addLast("SMS-HANDLER", getSmsHandler());
				}
				return pipeline;
			}
		});
		this.serverBootStrap.setChannelPipelineFactory(new ChannelPipelineFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("IDLE-STATE", new IdleStateHandler(timer, 0, 0, getActiveTimeMillis(), TimeUnit.MILLISECONDS));
				pipeline.addLast("ACTIVE-AWARE-HANDLER",new ActiveAwareChannelHandler());
				if(null != getSmsHandler()){
					pipeline.addLast("SMS-HANDLER", getSmsHandler());
				}
				return pipeline;
			}
		});
	}	
	@Override
	public boolean write(Object object) {
		
		return false;
	}
	@Override
	public void connect() {
		this.clientBootstrap.connect();
	}
	@Override
	public void close() {
		if(null != this.clientBootstrap){
			this.clientBootstrap.releaseExternalResources();
		}
		if(null != this.serverBootStrap){
			this.serverBootStrap.releaseExternalResources();
		}
	}
		
}
