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
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.logging.LoggingHandler;
import org.jboss.netty.logging.InternalLogLevel;
import org.jboss.netty.util.ExternalResourceReleasable;

import com.tbtosoft.smio.ICoder;
import com.tbtosoft.smio.handlers.DecodeHandler;
import com.tbtosoft.smio.handlers.EncodeHandler;
import com.tbtosoft.smio.utils.ChannelPipeHelper;

/**
 * @author chengchun
 *
 */
abstract class IOBootstrap implements ExternalResourceReleasable {
	private ICoder coder;
	private ChannelPipelineFactory channelPipelineFactory;
	/* (non-Javadoc)
	 * @see org.jboss.netty.util.ExternalResourceReleasable#releaseExternalResources()
	 */
	@Override
	public void releaseExternalResources() {
		releaseExternalResourcesImpl();
	}
	protected abstract void releaseExternalResourcesImpl();
	protected ChannelPipelineFactory getInnerChannelPipelineFactory(){
		return new ChannelPipelineFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("logger", new LoggingHandler(InternalLogLevel.INFO));
				if(null != coder){
					pipeline.addLast("DECODER", new DecodeHandler(coder));
					pipeline.addLast("ENCODER", new EncodeHandler(coder));
				}
				if(null != channelPipelineFactory){
					ChannelPipeHelper.addLast(pipeline, channelPipelineFactory.getPipeline());
				}				
				return pipeline;
			}
		};
	}
	/**
	 * @return the coder
	 */
	public ICoder getCoder() {
		return coder;
	}
	/**
	 * @param coder the coder to set
	 */
	public void setCoder(ICoder coder) {
		this.coder = coder;
	}
	/**
	 * @return the channelPipelineFactory
	 */
	public ChannelPipelineFactory getChannelPipelineFactory() {
		return channelPipelineFactory;
	}
	/**
	 * @param channelPipelineFactory the channelPipelineFactory to set
	 */
	public void setChannelPipelineFactory(
			ChannelPipelineFactory channelPipelineFactory) {
		this.channelPipelineFactory = channelPipelineFactory;
	}	
}
