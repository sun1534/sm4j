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
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

/**
 * @author chengchun
 *
 */
abstract class Link implements ILink{
	private volatile boolean isConnected = false;
	private SocketAddress hostSocketAddress;	
	private IoHandler ioHandler;
	private ClientBootstrap clientBootstrap;
	private ChannelFactory clientChannelFactory;
	private ChannelGroup clientChannels = new DefaultChannelGroup("LONG-LINK");	
	protected Link(SocketAddress address){
		this.hostSocketAddress = address;		
		clientChannelFactory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());
		clientBootstrap = new ClientBootstrap(clientChannelFactory);
	}
	
	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.ILink#open()
	 */
	@Override
	public boolean open() {
		return connectRemoteHost();
	}

	private boolean connectRemoteHost(){
		ChannelFuture channelFuture  = clientBootstrap.connect(this.hostSocketAddress);
		if(!channelFuture.isDone()){
			return false;
		}
		if(!channelFuture.awaitUninterruptibly(3, TimeUnit.SECONDS)){
			return false;
		}
		if(!channelFuture.isSuccess()){
			return false;
		}
		clientChannels.add(channelFuture.getChannel());
		return true;
	}
	protected void receiveImpl(Channel channel, Object obj){
		ioHandler.receive(channel, obj);
	}
	
	class InnerHanlder extends SimpleChannelUpstreamHandler{

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
	public void close() {
		closeImpl();
		ChannelGroupFuture channelGroupFuture = clientChannels.close();
		channelGroupFuture.awaitUninterruptibly();
		if(null != clientChannelFactory){
			clientChannelFactory.releaseExternalResources();
		}
	}
	protected abstract void closeImpl();
	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.ILink#write(java.lang.Object)
	 */
	@Override
	public boolean write(Object object) {
		if(!isConnected()){
			if(!connectRemoteHost()){
				return false;
			}
		}
		return clientChannels.write(object).isDone();	
	}
	
	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.ILink#isConnected()
	 */
	@Override
	public boolean isConnected() {		
		return isConnected;
	}
	
}
