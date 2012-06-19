/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio.session.impl;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.tbtosoft.smio.net.CmppChannelPipeline;
import com.tbtosoft.smio.session.CmppConfig;
import com.tbtosoft.smio.session.IConfig;

/**
 * @author chengchun
 *
 */
class CmppClientSession extends BasicSession {
	private NioClientSocketChannelFactory channelFactory = new NioClientSocketChannelFactory(
            Executors.newCachedThreadPool(),
            Executors.newCachedThreadPool());
	private ClientBootstrap bootstrap = new ClientBootstrap(channelFactory);
	private ChannelGroup channelGroup = new DefaultChannelGroup("CMPP-SESSION");
	private CmppChannelPipeline cmppChannelPipeline = new CmppChannelPipeline();
	private ILink link;
	public CmppClientSession(){
		cmppChannelPipeline.addChannelHander("CMPP-SESSION", new DefaultChannleHander());
		bootstrap.setPipelineFactory(cmppChannelPipeline);
		link = createLink();
	}
	public void connect(){
		
		link.connect();
	}
	private ILink createLink(){
		IConfig config = getConfig();
		String linkFlag = config.getString(CmppConfig.LINK_FLAG);
		if(linkFlag.equalsIgnoreCase("single")){
			String mtmoHostIp = config.getString(CmppConfig.MT_MO_HOST_IP);
			Integer mtmoHostPort = config.getInteger(CmppConfig.MT_MO_HOST_PORT);
			return new SingleLink(mtmoHostIp, mtmoHostPort);
		} else {			
			String moHostIp = config.getString(CmppConfig.MO_HOST_IP);
			Integer moHostPort = config.getInteger(CmppConfig.MO_HOST_PORT);
			String mtHostIp = config.getString(CmppConfig.MT_HOST_IP);
			Integer mtHostPort = config.getInteger(CmppConfig.MT_HOST_PORT);
			return new DoubleLink(mtHostIp, mtHostPort, moHostIp, moHostPort);
		}		
	}
	@Override
	protected void loop() throws InterruptedException {
				
	}
	private boolean checkIsMoMessage(Object obj){
		if(obj instanceof com.tbtosoft.cmpp.DeliverReqPkg){
			return true;
		} else {
			return false;
		}
	}
	
	class DefaultChannleHander extends SimpleChannelHandler{

		/* (non-Javadoc)
		 * @see org.jboss.netty.channel.SimpleChannelHandler#messageReceived(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.MessageEvent)
		 */
		@Override
		public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
				throws Exception {			
			super.messageReceived(ctx, e);
		}

		/* (non-Javadoc)
		 * @see org.jboss.netty.channel.SimpleChannelHandler#exceptionCaught(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ExceptionEvent)
		 */
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
				throws Exception {			
			super.exceptionCaught(ctx, e);
		}

		/* (non-Javadoc)
		 * @see org.jboss.netty.channel.SimpleChannelHandler#channelConnected(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
		 */
		@Override
		public void channelConnected(ChannelHandlerContext ctx,
				ChannelStateEvent e) throws Exception {			
			//super.channelConnected(ctx, e);
			
			channelGroup.add(ctx.getChannel());
		}

		/* (non-Javadoc)
		 * @see org.jboss.netty.channel.SimpleChannelHandler#channelDisconnected(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
		 */
		@Override
		public void channelDisconnected(ChannelHandlerContext ctx,
				ChannelStateEvent e) throws Exception {			
//			super.channelDisconnected(ctx, e);
		}		
	}
	interface ILink{
		public ChannelFuture write(Object obj);
		public void connect();		
	}
	abstract class BasicLink implements ILink{

		/* (non-Javadoc)
		 * @see com.tbtosoft.smio.session.impl.CmppClientSession.ILink#connect()
		 */
		@Override
		public void connect() {
			onConnect();
		}
		protected abstract void onConnect();
		@Override
		public ChannelFuture write(Object obj) {
			return onWrite(obj);
		}
		protected abstract ChannelFuture onWrite(Object obj);
	}
	class SingleLink extends BasicLink{		
		Channel channel;
		private String hostIp;
		private Integer hostPort;
		private boolean connected;
		@Override
		protected synchronized ChannelFuture onWrite(Object obj) {			
			return channel.write(obj);
		}
		public SingleLink(String ip, Integer port){
			this.hostIp = ip;
			this.hostPort = port;
		}
		@Override
		protected synchronized void onConnect() {
			if(!connected){
				ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(hostIp, hostPort));
				channelFuture.awaitUninterruptibly(3, TimeUnit.SECONDS);
				Channel channel = channelFuture.getChannel();
				if(channel.isConnected()){
					connected = true;
					this.channel = channel;
					this.channel.getCloseFuture().addListener(new ChannelFutureListener() {
						
						@Override
						public void operationComplete(ChannelFuture future) throws Exception {
							onClosed();
						}
					});
				}
			}
		}
		private synchronized void onClosed(){
			this.connected = false;
			this.channel = null;
		}
		
	}
	class DoubleLink implements ILink{
		private SingleLink moLink;
		private SingleLink mtLink;
		
		public DoubleLink(String mtIp, Integer mtPort, String moIp, Integer moPort){
			this.moLink = new SingleLink(moIp, moPort);
			this.mtLink = new SingleLink(mtIp, mtPort);			
		}
	
		@Override
		public ChannelFuture write(Object obj) {
			if(checkIsMoMessage(obj)){
				return moLink.write(obj);
			}
			return mtLink.write(obj);
		}
		@Override
		public void connect() {
			moLink.connect();
			mtLink.connect();
		}		
	}
}
