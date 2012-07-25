/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio.impl;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.util.Timer;

import com.tbtosoft.smio.ICoder;
import com.tbtosoft.smio.handlers.ActiveAwareChannelHandler;

/**
 * @author chengchun
 *
 */
public class ShortChannelPipeFactory extends BasicChannelPipeFactory{

	public ShortChannelPipeFactory(ICoder coder, Timer timer,
			long idleTimeMillis) {
		super(coder, timer, idleTimeMillis);
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.impl.BasicChannelPipeFactory#getPipeline()
	 */
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = super.getPipeline();
		pipeline.addLast("ACTIVE-AWARE-HANDLER",new ActiveAwareChannelHandler());	
		return super.getPipeline();
	}	
}
