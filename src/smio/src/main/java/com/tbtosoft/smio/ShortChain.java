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
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ServerChannelFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;

/**
 * @author chengchun
 *
 */
public class ShortChain<E, T extends ICoder<E>> extends BasicChain {
	private final T coder;
	private final long activeTimeMillis;
	private final Client client;
	private final Server server;	
	private volatile long keepAliveTimeMillis;
	public ShortChain(SocketAddress serverAddress, SocketAddress localAddress, long activeTimeMillis, T coder){
		this.coder = coder;
		this.activeTimeMillis = activeTimeMillis;
		this.client = new Client(serverAddress);
		this.server = new Server(localAddress);
	}
	
	/**
	 * @return the keepAliveTimeMillis
	 */
	public final long getKeepAliveTimeMillis() {
		return keepAliveTimeMillis;
	}

	/**
	 * @param keepAliveTimeMillis the keepAliveTimeMillis to set
	 */
	public final void setKeepAliveTimeMillis(long keepAliveTimeMillis) {
		this.keepAliveTimeMillis = keepAliveTimeMillis;
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.BasicChain#open()
	 */
	@Override
	public boolean open() {
		
		return super.open();
	}
	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.BasicChain#close()
	 */
	@Override
	public void close() {
		this.client.close();
		this.server.close();
	}
	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.BasicChain#write(java.lang.Object)
	 */
	@Override
	public boolean write(Object object) {		
		return writeToRemote(object);
	}	
	private boolean writeToRemote(Object object){
		return false;
	}
	class Client implements IChain{
		private ClientBootstrap clientBootstrap;
		private ChannelFactory clientChannelFactory;
		private final SocketAddress serverAddress;
		private volatile Channel channel;		
		public Client(SocketAddress serverAddress) {
			this.serverAddress = serverAddress;
			this.clientChannelFactory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),
					Executors.newCachedThreadPool());
			this.clientBootstrap = new ClientBootstrap(this.clientChannelFactory);
			this.clientBootstrap.setPipelineFactory(new DefaultChannelPipeFactory() {

				/* (non-Javadoc)
				 * @see com.tbtosoft.smio.ShortChain.DefaultChannelPipeFactory#getPipeline()
				 */
				@Override
				public ChannelPipeline getPipeline() throws Exception {
					ChannelPipeline channelPipeline =  super.getPipeline();
					channelPipeline.addLast("", new IdleStateAwareChannelHandler(){

						/* (non-Javadoc)
						 * @see org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler#channelIdle(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.handler.timeout.IdleStateEvent)
						 */
						@Override
						public void channelIdle(ChannelHandlerContext ctx,
								IdleStateEvent e) throws Exception {
							if(IdleState.ALL_IDLE == e.getState()){
								if(null != Client.this.channel){
									Client.this.channel.close();
								}
							}
							super.channelIdle(ctx, e);
						}						
					});
					return channelPipeline;
				}			
			});
		}
		private void connect(){
			if(null != this.channel && this.channel.isConnected()){
				return;
			}
			ChannelFuture channelFuture = this.clientBootstrap.connect(this.serverAddress);
			channelFuture.awaitUninterruptibly();
			if(!channelFuture.isSuccess()){
				
				return ;
			}
			this.channel = channelFuture.getChannel();
			this.channel.getCloseFuture().addListener(new ChannelFutureListener() {
				
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {				
					Client.this.channel = null;
				}
			});		
		}
		private boolean isConnected(){
			if(null != this.channel){
				return this.channel.isConnected();
			}
			return false;
		}
		@Override
		public void addHandler(String name, ISmsHandler smsHandler) {
			
		}
		@Override
		public boolean write(Object object) {
			if(!isConnected()){
				connect();
			}
			return false;
		}
		@Override
		public boolean open() {
			
			return false;
		}

		@Override
		public void close() {
			if(null != this.clientChannelFactory){
				this.clientChannelFactory.releaseExternalResources();
			}
		}
	}
	class Server implements IChain{
		private final SocketAddress localAddress;
		private ServerChannelFactory serverChannelFactory;
		private ServerBootstrap serverBootstrap;
		private volatile Channel channel;
		public Server(SocketAddress localAddress){
			this.localAddress = localAddress;
			this.serverChannelFactory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), 
					Executors.newCachedThreadPool());
			this.serverBootstrap = new ServerBootstrap(this.serverChannelFactory);
			this.serverBootstrap.setPipelineFactory(new DefaultChannelPipeFactory() {

				/* (non-Javadoc)
				 * @see com.tbtosoft.smio.ShortChain.DefaultChannelPipeFactory#getPipeline()
				 */
				@Override
				public ChannelPipeline getPipeline() throws Exception {					
					ChannelPipeline channelPipeline = super.getPipeline();
					channelPipeline.addLast("", new IdleStateAwareChannelHandler(){

						/* (non-Javadoc)
						 * @see org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler#channelIdle(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.handler.timeout.IdleStateEvent)
						 */
						@Override
						public void channelIdle(ChannelHandlerContext ctx,
								IdleStateEvent e) throws Exception {
							if(IdleState.ALL_IDLE == e.getState()){
								if(null != Server.this.channel){
									Server.this.channel.close();
								}
							}
							super.channelIdle(ctx, e);
						}						
					});
					return  channelPipeline;
				}
								
			});
			this.serverBootstrap.bind(this.localAddress);
		}
		@Override
		public void addHandler(String name, ISmsHandler smsHandler) {
			
		}
		@Override
		public boolean write(Object object) {
			
			return false;
		}
		@Override
		public boolean open() {
			
			return false;
		}
		@Override
		public void close() {
						
		}		
	}
	class DefaultChannelPipeFactory implements ChannelPipelineFactory{

		@Override
		public ChannelPipeline getPipeline() throws Exception {
			ChannelPipeline channelPipeline = getChannelPipeline();
			channelPipeline.addLast("IDLE-STATE-HANDLER", new IdleStateHandler(new HashedWheelTimer(), 0, 0, ShortChain.this.activeTimeMillis, TimeUnit.MILLISECONDS));			
			channelPipeline.addLast("ENCODER", new Encoder<E, T>(ShortChain.this.coder));
			channelPipeline.addLast("DECODER", new Decoder<E, T>(ShortChain.this.coder));
			return channelPipeline;
		}
		
	}
}
