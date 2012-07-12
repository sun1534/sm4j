/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio;

import static org.jboss.netty.channel.Channels.succeededFuture;

import java.net.SocketAddress;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.util.internal.StringUtil;

/**
 * @author chengchun
 *
 */
public class ActiveMessageEvent implements MessageEvent {
	private final Channel channel;
    private final Object message;
    private final SocketAddress remoteAddress;
    public ActiveMessageEvent(
            Channel channel, Object message, SocketAddress remoteAddress) {

        if (channel == null) {
            throw new NullPointerException("channel");
        }
        if (message == null) {
            throw new NullPointerException("message");
        }
        this.channel = channel;
        this.message = message;
        if (remoteAddress != null) {
            this.remoteAddress = remoteAddress;
        } else {
            this.remoteAddress = channel.getRemoteAddress();
        }
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

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.MessageEvent#getMessage()
	 */
	@Override
	public Object getMessage() {
		// TODO Auto-generated method stub
		return this.message;
	}

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.MessageEvent#getRemoteAddress()
	 */
	@Override
	public SocketAddress getRemoteAddress() {
		return this.remoteAddress;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (getRemoteAddress() == getChannel().getRemoteAddress()) {
            return getChannel().toString() + " RECEIVED: " +
                   StringUtil.stripControlCharacters(getMessage());
        } else {
            return getChannel().toString() + " RECEIVED: " +
                   StringUtil.stripControlCharacters(getMessage()) + " from " +
                   getRemoteAddress();
        }
	}
}
