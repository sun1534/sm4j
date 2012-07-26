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
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

import com.tbtosoft.smio.handlers.ActiveAwareChannelHandler;
import com.tbtosoft.smio.handlers.ReconnectChannelHandler;
import com.tbtosoft.smio.handlers.ReconnectChannelHandler.IReconnector;
import com.tbtosoft.smio.impl.IOClientBootstrap;


/**
 * @author chengchun
 *
 */
public class IoClient extends IoSms implements IClient{
	private IOClientBootstrap clientBootstrap;
	private Timer timer;	
	public IoClient(ICoder coder, SocketAddress remoteAddress){
		this.clientBootstrap = new IOClientBootstrap(new NioClientSocketChannelFactory());
		this.clientBootstrap.setCoder(coder);
		this.clientBootstrap.setOption("remoteAddress", remoteAddress);
		this.timer = new HashedWheelTimer();
		this.clientBootstrap.setChannelPipelineFactory(new ChannelPipelineFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("IDLE-STATE", new IdleStateHandler(timer, 0, 0, getActiveTimeMillis(), TimeUnit.MILLISECONDS));
				pipeline.addLast("ACTIVE-AWARE-HANDLER",new ActiveAwareChannelHandler());
				pipeline.addLast("RECONNECT-HANLDER", new ReconnectChannelHandler(new IReconnector() {					
					@Override
					public void connect() {
						IoClient.this.connect();
					}
				}));
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
	}
	
}
