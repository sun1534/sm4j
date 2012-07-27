/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio.handlers;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.tbtosoft.smio.IoChannelHandler;

/**
 * @author chengchun
 *
 */
public class DefaultIoChannelHandler extends SimpleChannelHandler implements
		IoChannelHandler {
	private final IoChannelHandler ioChannelHandler;
	public DefaultIoChannelHandler(IoChannelHandler ioChannelHandler){
		this.ioChannelHandler = ioChannelHandler;
	}
	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.SimpleChannelHandler#handleDownstream(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelEvent)
	 */
	@Override
	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e)
			throws Exception {
		if(e instanceof ActiveEvent){
			onChannelIdle(ctx, (ActiveEvent)e);
		} else if(e instanceof KeepConnectionEvent){
			onChannelKeepAlive(ctx, (KeepConnectionEvent)e);
		} else if (e instanceof ExceptionEvent) {
            exceptionCaught(ctx, (ExceptionEvent) e);
        } else {
			super.handleUpstream(ctx, e);
		}
	}

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.SimpleChannelHandler#channelConnected(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
	 */
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		this.ioChannelHandler.onChannelConnected(ctx, e);
	}

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.SimpleChannelHandler#channelDisconnected(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
	 */
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		this.ioChannelHandler.onChannelDisconnected(ctx, e);
	}

	@Override
	public void onChannelIdle(ChannelHandlerContext ctx, ActiveEvent e) {
		this.ioChannelHandler.onChannelIdle(ctx, e);
	}

	@Override
	public void onChannelKeepAlive(ChannelHandlerContext ctx,
			KeepConnectionEvent e) {
		this.ioChannelHandler.onChannelKeepAlive(ctx, e);
	}

	@Override
	public void onChannelConnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) {
		this.ioChannelHandler.onChannelConnected(ctx, e);
	}

	@Override
	public void onChannelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) {
		this.ioChannelHandler.onChannelDisconnected(ctx, e);
	}	
}
