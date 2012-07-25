/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import com.tbtosoft.smio.handlers.ActiveEvent;
import com.tbtosoft.smio.handlers.KeepConnectionEvent;

/**
 * @author chengchun
 *
 */
public abstract class SmsIoHandler<T> implements ISmsHandler{
	private final InnerHandler handler = new InnerHandler();
	private ISmsHandler smsHandler;
	protected SmsIoHandler(){
		
	}
	protected SmsIoHandler(ISmsHandler smsHandler){
		this.smsHandler = smsHandler;
	}
	
	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.ISmsHandler#getChannelHandler()
	 */
	@Override
	public ChannelHandler getChannelHandler() {
		return this.handler;
	}
	
	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.ISmsHandler#write(java.lang.Object)
	 */
	@Override
	public boolean write(Object object) {
		return writeImpl(object);
	}
	protected boolean writeImpl(Object object){
		return false;
	}
	protected abstract void receiveImpl(ChannelHandlerContext ctx, T t);
	
	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.ISmsHandler#onChannelIdle(org.jboss.netty.channel.ChannelHandlerContext, com.tbtosoft.smio.handlers.ActiveEvent)
	 */
	@Override
	public void onChannelIdle(ChannelHandlerContext ctx, ActiveEvent e) {
		if(null != this.smsHandler){
			this.smsHandler.onChannelIdle(ctx, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.ISmsHandler#onChannelKeepAlive(org.jboss.netty.channel.ChannelHandlerContext, com.tbtosoft.smio.handlers.KeepConnectionEvent)
	 */
	@Override
	public void onChannelKeepAlive(ChannelHandlerContext ctx,
			KeepConnectionEvent e) {
		if(null != this.smsHandler){
			this.smsHandler.onChannelKeepAlive(ctx, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.ISmsHandler#onChannelConnected(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
	 */
	@Override
	public void onChannelConnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) {
		if(null != this.smsHandler){
			this.smsHandler.onChannelConnected(ctx, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.ISmsHandler#onChannelDisconnected(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
	 */
	@Override
	public void onChannelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) {
		if(null != this.smsHandler){
			this.smsHandler.onChannelDisconnected(ctx, e);
		}
	}

	class InnerHandler extends SimpleChannelUpstreamHandler{

		/* (non-Javadoc)
		 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#handleUpstream(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelEvent)
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
		 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#messageReceived(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.MessageEvent)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
				throws Exception {
			receiveImpl(ctx, ((T)e.getMessage()));
		}

		/* (non-Javadoc)
		 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#channelConnected(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
		 */
		@Override
		public void channelConnected(ChannelHandlerContext ctx,
				ChannelStateEvent e) throws Exception {
			onChannelConnected(ctx, e);
		}

		/* (non-Javadoc)
		 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#channelDisconnected(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
		 */
		@Override
		public void channelDisconnected(ChannelHandlerContext ctx,
				ChannelStateEvent e) throws Exception {
			onChannelDisconnected(ctx, e);
		}		
	}
}
