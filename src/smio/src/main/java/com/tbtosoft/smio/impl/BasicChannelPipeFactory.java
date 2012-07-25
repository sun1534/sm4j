/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio.impl;

import java.util.concurrent.TimeUnit;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.Timer;

import com.tbtosoft.smio.ICoder;
import com.tbtosoft.smio.handlers.DecodeHandler;
import com.tbtosoft.smio.handlers.EncodeHandler;

/**
 * @author chengchun
 *
 */
class BasicChannelPipeFactory implements ChannelPipelineFactory {
	private final ICoder coder;
	private final long idleTimeMillis;
	private final Timer timer;
	protected BasicChannelPipeFactory(ICoder coder, Timer timer, long idleTimeMillis){
		this.coder = coder;
		this.timer = timer;
		this.idleTimeMillis = idleTimeMillis;
	}
	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.ChannelPipelineFactory#getPipeline()
	 */
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		pipeline.addLast("IDLE-STATE", new IdleStateHandler(this.timer, 0, 0, this.idleTimeMillis, TimeUnit.MILLISECONDS));
		pipeline.addLast("DECODER", new DecodeHandler(this.coder));
		pipeline.addLast("ENCODER", new EncodeHandler(this.coder));		
		return pipeline;
	}

}
