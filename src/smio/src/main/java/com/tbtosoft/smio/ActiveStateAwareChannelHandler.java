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
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * @author chengchun
 *
 */
public class ActiveStateAwareChannelHandler extends SimpleChannelHandler {

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.SimpleChannelHandler#handleUpstream(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelEvent)
	 */
	@Override
	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e)
			throws Exception {
		if(e instanceof ActiveMessageEvent){
			channelActive(ctx, (ActiveMessageEvent)e);
		} else {
			super.handleUpstream(ctx, e);
		}
	}
	
	public void channelActive(ChannelHandlerContext ctx, ActiveMessageEvent e)throws Exception {
        ctx.sendUpstream(e);
    }
}
