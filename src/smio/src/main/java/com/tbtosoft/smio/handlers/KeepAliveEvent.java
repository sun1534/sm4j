/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio.handlers;

import static org.jboss.netty.channel.Channels.succeededFuture;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelFuture;

/**
 * @author chengchun
 *
 */
public class KeepAliveEvent implements ChannelEvent {
	private final Channel channel;
	public KeepAliveEvent(Channel channel){
		this.channel = channel;
	}
	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.ChannelEvent#getChannel()
	 */
	@Override
	public Channel getChannel() {		
		return this.channel;
	}

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.ChannelEvent#getFuture()
	 */
	@Override
	public ChannelFuture getFuture() {
		return succeededFuture(getChannel());
	}

}
