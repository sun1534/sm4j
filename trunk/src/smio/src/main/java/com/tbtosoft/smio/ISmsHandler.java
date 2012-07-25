/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio;

import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;

import com.tbtosoft.smio.handlers.ActiveEvent;
import com.tbtosoft.smio.handlers.KeepConnectionEvent;

/**
 * @author chengchun
 *
 */
public interface ISmsHandler {
	public ChannelHandler getChannelHandler();
	public boolean write(Object object);
	public void onChannelIdle(ChannelHandlerContext ctx, ActiveEvent e);
	public void onChannelKeepAlive(ChannelHandlerContext ctx, KeepConnectionEvent e);
	public void onChannelConnected(ChannelHandlerContext ctx, ChannelStateEvent e);
	public void onChannelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e);
}
