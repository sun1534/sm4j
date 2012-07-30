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
import java.util.concurrent.TimeUnit;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;
import org.jboss.netty.handler.timeout.IdleStateHandler;

import com.tbtosoft.smio.impl.IOClientBootstrap;
import com.tbtosoft.smio.impl.IOServerBootStrap;

/**
 * @author chengchun
 *
 */
public class IoClientServer extends IoSms implements IClient{	
	private IOClientBootstrap clientBootstrap;
	private IOServerBootStrap serverBootStrap;
//	private DefaultSmsHandler clientSmsHandler;
//	private DefaultIoChannelHandler clientIoChannelHandler;
//	private DefaultSmsHandler serverSmsHandler;
//	private DefaultIoChannelHandler serverIoChannelHandler;
	
	public IoClientServer(ICoder coder, SocketAddress localAddress, SocketAddress remoteAddress){
		this.clientBootstrap = new IOClientBootstrap(new NioClientSocketChannelFactory());
		this.clientBootstrap.setCoder(coder);
		this.clientBootstrap.setOption("remoteAddress", remoteAddress);
		this.serverBootStrap = new IOServerBootStrap(new NioServerSocketChannelFactory());
		this.serverBootStrap.setCoder(coder);
		this.serverBootStrap.setOption("localAddress", localAddress);
		this.clientBootstrap.setChannelPipelineFactory(new ChannelPipelineFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();				
				pipeline.addLast("IDLE-STATE", new IdleStateHandler(getTimer(), 0, 0, getActiveTimeMillis(), TimeUnit.MILLISECONDS));
				pipeline.addLast("ACTIVE-AWARE-HANDLER",new IdleStateAwareChannelHandler(){

					/* (non-Javadoc)
					 * @see org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler#channelIdle(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.handler.timeout.IdleStateEvent)
					 */
					@Override
					public void channelIdle(ChannelHandlerContext ctx,
							IdleStateEvent e) throws Exception {
						if(IdleState.ALL_IDLE == e.getState()){
							ctx.getChannel().close();
						} else {
							super.channelIdle(ctx, e);
						}
					}
					
				});
				if(null != getIoChannelHandler()){
					pipeline.addLast("IO-C-HANDLER", getIoChannelHandler());
				}
				if(null != getSmsHandler()){
					pipeline.addLast("SMS-C-HANDLER", getSmsHandler());
				}
				return pipeline;
			}
		});
		this.serverBootStrap.setChannelPipelineFactory(new ChannelPipelineFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("IDLE-STATE", new IdleStateHandler(getTimer(), 0, 0, getActiveTimeMillis(), TimeUnit.MILLISECONDS));
				pipeline.addLast("ACTIVE-AWARE-HANDLER",new IdleStateAwareChannelHandler(){

					/* (non-Javadoc)
					 * @see org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler#channelIdle(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.handler.timeout.IdleStateEvent)
					 */
					@Override
					public void channelIdle(ChannelHandlerContext ctx,
							IdleStateEvent e) throws Exception {
						if(IdleState.ALL_IDLE == e.getState()){
							ctx.getChannel().close();
						} else {
							super.channelIdle(ctx, e);
						}
					}
					
				});
				if(null != getSmsHandler()){
					pipeline.addLast("SMS-S-HANDLER", getSmsHandler());
				}
				return pipeline;
			}
		});
		this.serverBootStrap.bind();
	}	
	@Override
	public boolean write(Object object) {
		
		return false;
	}
	@Override
	public void connect() {
		this.clientBootstrap.connect();
	}
	@Override
	public void close() {
		if(null != this.clientBootstrap){
			this.clientBootstrap.releaseExternalResources();
		}
		if(null != this.serverBootStrap){
			this.serverBootStrap.releaseExternalResources();
		}
	}
//	/**
//	 * @return the clientSmsHandler
//	 */
//	public final DefaultSmsHandler getClientSmsHandler() {
//		return clientSmsHandler;
//	}
//	/**
//	 * @param clientSmsHandler the clientSmsHandler to set
//	 */
//	public final void setClientSmsHandler(DefaultSmsHandler clientSmsHandler) {
//		this.clientSmsHandler = clientSmsHandler;
//	}
//	/**
//	 * @return the serverSmsHandler
//	 */
//	public final DefaultSmsHandler getServerSmsHandler() {
//		return serverSmsHandler;
//	}
//	/**
//	 * @param serverSmsHandler the serverSmsHandler to set
//	 */
//	public final void setServerSmsHandler(DefaultSmsHandler serverSmsHandler) {
//		this.serverSmsHandler = serverSmsHandler;
//	}
//	/**
//	 * @return the clientIoChannelHandler
//	 */
//	public final DefaultIoChannelHandler getClientIoChannelHandler() {
//		return clientIoChannelHandler;
//	}
//	/**
//	 * @param clientIoChannelHandler the clientIoChannelHandler to set
//	 */
//	public final void setClientIoChannelHandler(
//			DefaultIoChannelHandler clientIoChannelHandler) {
//		this.clientIoChannelHandler = clientIoChannelHandler;
//	}
//	/**
//	 * @return the serverIoChannelHandler
//	 */
//	public final DefaultIoChannelHandler getServerIoChannelHandler() {
//		return serverIoChannelHandler;
//	}
//	/**
//	 * @param serverIoChannelHandler the serverIoChannelHandler to set
//	 */
//	public final void setServerIoChannelHandler(
//			DefaultIoChannelHandler serverIoChannelHandler) {
//		this.serverIoChannelHandler = serverIoChannelHandler;
//	}	
}
