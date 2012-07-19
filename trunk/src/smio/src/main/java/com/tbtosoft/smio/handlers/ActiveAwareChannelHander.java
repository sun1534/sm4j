/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio.handlers;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;

/**
 * @author chengchun
 *
 */
public class ActiveAwareChannelHander extends IdleStateAwareChannelHandler {

	/* (non-Javadoc)
	 * @see org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler#channelIdle(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.handler.timeout.IdleStateEvent)
	 */
	@Override
	public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e)
			throws Exception {
		if(IdleState.ALL_IDLE == e.getState()){
			channelActive(ctx, new ActiveEvent(e.getChannel(), e.getLastActivityTimeMillis()));
		} else {
			super.channelIdle(ctx, e);
		}
	}
	private void channelActive(ChannelHandlerContext ctx, ActiveEvent e){
		ctx.sendUpstream(e);
	}
}
