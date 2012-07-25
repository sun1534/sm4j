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
public class IoClient implements IClient{
	private IOClientBootstrap clientBootstrap;
	private Timer timer;
	private long activeTimeMillis;
	private ISmsHandler smsHandler;
	public IoClient(ICoder coder, SocketAddress remoteAddress){
		this.clientBootstrap = new IOClientBootstrap(new NioClientSocketChannelFactory());
		this.clientBootstrap.setCoder(coder);
		this.clientBootstrap.setOption("remoteAddress", remoteAddress);
		this.timer = new HashedWheelTimer();
		this.clientBootstrap.setChannelPipelineFactory(new ChannelPipelineFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("IDLE-STATE", new IdleStateHandler(timer, 0, 0, activeTimeMillis, TimeUnit.MILLISECONDS));
				pipeline.addLast("ACTIVE-AWARE-HANDLER",new ActiveAwareChannelHandler());
				pipeline.addLast("RECONNECT-HANLDER", new ReconnectChannelHandler(new IReconnector() {					
					@Override
					public void connect() {
						IoClient.this.connect();
					}
				}));
				if(null != smsHandler){
					pipeline.addLast("SMS-HANDLER", smsHandler.getChannelHandler());
				}
				return pipeline;
			}
		});
	}
	/**
	 * @return the activeTimeMillis
	 */
	public long getActiveTimeMillis() {
		return activeTimeMillis;
	}
	/**
	 * @param activeTimeMillis the activeTimeMillis to set
	 */
	public void setActiveTimeMillis(long activeTimeMillis) {
		this.activeTimeMillis = activeTimeMillis;
	}
	
	/**
	 * @return the smsHandler
	 */
	public ISmsHandler getSmsHandler() {
		return smsHandler;
	}
	/**
	 * @param smsHandler the smsHandler to set
	 */
	public void setSmsHandler(ISmsHandler smsHandler) {
		this.smsHandler = smsHandler;
	}
	@Override
	public boolean write(Object object) {		
		return this.smsHandler.write(object);
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
