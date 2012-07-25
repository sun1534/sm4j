/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio.handlers;

import java.net.ConnectException;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * @author chengchun
 *
 */
public class ReconnectChannelHandler extends SimpleChannelHandler {
	public static interface IReconnector{
		public void connect();
	}	
	public final IReconnector reconnector;
	public ReconnectChannelHandler(IReconnector reconnector){
		this.reconnector = reconnector;
	}	
	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.SimpleChannelHandler#exceptionCaught(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ExceptionEvent)
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		Throwable cause = e.getCause();
		if(cause instanceof ConnectException){			
			ctx.getChannel().close();
		}
		super.exceptionCaught(ctx, e);
	}

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.SimpleChannelHandler#channelClosed(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
	 */
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		channelReconnect(ctx, e);
		super.channelClosed(ctx, e);
	}
	private void channelReconnect(ChannelHandlerContext ctx, ChannelStateEvent e){
		if(null != this.reconnector){
			this.reconnector.connect();
		}
	}

}
