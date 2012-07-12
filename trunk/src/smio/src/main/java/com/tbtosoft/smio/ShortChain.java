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

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ServerChannelFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 * @author chengchun
 *
 */
public class ShortChain extends BasicChain {
	private final Client client;
	private final Server server;	
	private volatile long keepAliveTimeMillis;
	public ShortChain(SocketAddress serverAddress, SocketAddress localAddress){		
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
		public Client(SocketAddress serverAddress) {
			this.serverAddress = serverAddress;
			this.clientChannelFactory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),
					Executors.newCachedThreadPool());
			this.clientBootstrap = new ClientBootstrap(this.clientChannelFactory);
			this.clientBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
				
				@Override
				public ChannelPipeline getPipeline() throws Exception {
					ChannelPipeline channelPipeline = getChannelPipeline();
					return channelPipeline;
				}
			});
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
			if(null != this.clientChannelFactory){
				this.clientChannelFactory.releaseExternalResources();
			}
		}
	}
	class Server implements IChain{
		private final SocketAddress localAddress;
		private ServerChannelFactory serverChannelFactory;
		private ServerBootstrap serverBootstrap;
		public Server(SocketAddress localAddress){
			this.localAddress = localAddress;
			this.serverChannelFactory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), 
					Executors.newCachedThreadPool());
			this.serverBootstrap = new ServerBootstrap(this.serverChannelFactory);
			this.serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
				
				@Override
				public ChannelPipeline getPipeline() throws Exception {
					ChannelPipeline channelPipeline = getChannelPipeline();
					return channelPipeline;
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
}
