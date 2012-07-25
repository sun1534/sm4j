/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio.impl;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.util.Timer;

import com.tbtosoft.smio.ICoder;
import com.tbtosoft.smio.handlers.ActiveAwareChannelHandler;
import com.tbtosoft.smio.handlers.ReconnectChannelHandler;
import com.tbtosoft.smio.handlers.ReconnectChannelHandler.IReconnector;

/**
 * @author chengchun
 *
 */
public class LongChannelPipeFactory extends BasicChannelPipeFactory {
	private final ClientBootstrap clientBootstrap;
	public LongChannelPipeFactory(ClientBootstrap clientBootstrap, ICoder coder, Timer timer, long idleTimeMillis) {
		super(coder, timer, idleTimeMillis);
		this.clientBootstrap = clientBootstrap;
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.impl.BasicChannelPipeFactory#getPipeline()
	 */
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = super.getPipeline();
		pipeline.addLast("ACTIVE-AWARE-HANDLER",new ActiveAwareChannelHandler());	
		pipeline.addLast("RECONNECT-HANLDER", new ReconnectChannelHandler(new IReconnector() {
			
			@Override
			public void connect() {
				clientBootstrap.connect();
			}
		}));
		return pipeline;
	}

	
	
}
