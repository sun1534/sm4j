/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * @author chengchun
 *
 */
public class SmioPipelineFactory implements ChannelPipelineFactory {
	private SimpleSmioHandler smioHandler;
	private FrameDecoder decoder;
	private OneToOneEncoder encoder;
	public SmioPipelineFactory(){
		
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.ChannelPipelineFactory#getPipeline()
	 */
	@Override
	public final ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		if(null != this.smioHandler){
			pipeline.addFirst("sm-io-handler", this.smioHandler);
		}
		if(null != this.encoder){
			pipeline.addLast("encoder", this.encoder);
		}
		if(null != this.decoder){
			pipeline.addLast("decoder", this.decoder);
		}
		append(pipeline);
		return pipeline;
	}
	protected void append(ChannelPipeline pipeline){
		
	}
	/**
	 * @return the smioHandler
	 */
	public final SimpleSmioHandler getSmioHandler() {
		return smioHandler;
	}

	/**
	 * @param smioHandler the smioHandler to set
	 */
	public final void setSmioHandler(SimpleSmioHandler smioHandler) {
		this.smioHandler = smioHandler;
	}

	/**
	 * @return the decoder
	 */
	public final FrameDecoder getDecoder() {
		return decoder;
	}

	/**
	 * @param decoder the decoder to set
	 */
	public final void setDecoder(FrameDecoder decoder) {
		this.decoder = decoder;
	}

	/**
	 * @return the encoder
	 */
	public final OneToOneEncoder getEncoder() {
		return encoder;
	}

	/**
	 * @param encoder the encoder to set
	 */
	public final void setEncoder(OneToOneEncoder encoder) {
		this.encoder = encoder;
	}
	
}
