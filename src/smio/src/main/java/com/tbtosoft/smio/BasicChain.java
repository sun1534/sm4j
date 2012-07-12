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
import org.jboss.netty.channel.Channels;

/**
 * @author chengchun
 *
 */
public class BasicChain implements IChain{	
	private ChannelPipeline channelPipeline = Channels.pipeline();
//	@Override
//	public void addHandler(String name, SmsIoHandler smsIoHandler) {
//		this.channelPipeline.addLast(name, smsIoHandler);		
//	}
	protected ChannelPipeline getChannelPipeline(){
		return this.channelPipeline;
	}
	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.IChain#open()
	 */
	@Override
	public boolean open() {
		
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.IChain#close()
	 */
	@Override
	public void close() {
		
		
	}
	
	@Override
	public void addHandler(String name, ISmsHandler smsHandler) {
		this.channelPipeline.addLast(name, smsHandler.getChannelHandler());			
	}
	@Override
	public boolean write(Object object) {
		
		return false;
	}	
	
}
