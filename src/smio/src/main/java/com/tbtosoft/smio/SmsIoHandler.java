/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

/**
 * @author chengchun
 *
 */
public abstract class SmsIoHandler<T> extends SimpleChannelUpstreamHandler implements ISmsHandler{
	private Type actualType;
	private final InnerHandler handler = new InnerHandler();
	protected SmsIoHandler(){
		actualType = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.ISmsHandler#getChannelHandler()
	 */
	@Override
	public ChannelHandler getChannelHandler() {
		return this.handler;
	}

	protected abstract void receiveImpl(ChannelHandlerContext ctx, T t);
	class InnerHandler extends SimpleChannelUpstreamHandler{

		/* (non-Javadoc)
		 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#messageReceived(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.MessageEvent)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
				throws Exception {
			if(actualType.equals(e.getMessage().getClass())){
				receiveImpl(ctx, ((T)e.getMessage()));
			} else {
				super.messageReceived(ctx, e);
			}
		}		
	}
}
