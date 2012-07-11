/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio;

import java.net.SocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 * @author chengchun
 *
 */
public class ShortLink extends Link{
	private ChannelFactory serverChannelFactory;	
	private ChannelGroup channels = new DefaultChannelGroup("SHORT-LINK");
	public ShortLink(SocketAddress address){
		super(address);
		serverChannelFactory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());		
	}
	
	private void imcomingImpl(Channel channel){
		
	}
	
	private void outgoingImpl(Channel channel){
		
	}
	
	class InnerHanlder extends SimpleChannelHandler{

		/* (non-Javadoc)
		 * @see org.jboss.netty.channel.SimpleChannelHandler#channelConnected(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
		 */
		@Override
		public void channelConnected(ChannelHandlerContext ctx,
				ChannelStateEvent e) throws Exception {
			imcomingImpl(ctx.getChannel());
		}

		/* (non-Javadoc)
		 * @see org.jboss.netty.channel.SimpleChannelHandler#channelDisconnected(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
		 */
		@Override
		public void channelDisconnected(ChannelHandlerContext ctx,
				ChannelStateEvent e) throws Exception {
			outgoingImpl(ctx.getChannel());
		}

		/* (non-Javadoc)
		 * @see org.jboss.netty.channel.SimpleChannelHandler#messageReceived(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.MessageEvent)
		 */
		@Override
		public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
				throws Exception {
			receiveImpl(ctx.getChannel(), e.getMessage());
		}
		
	}
	@Override
	protected void closeImpl() {
		ChannelGroupFuture channelGroupFuture = channels.close();
		channelGroupFuture.awaitUninterruptibly();				
		if(null != serverChannelFactory){
			serverChannelFactory.releaseExternalResources();
		}
	}
}
