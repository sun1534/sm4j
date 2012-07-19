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
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

import com.tbtosoft.smio.codec.DecodeHandler;
import com.tbtosoft.smio.codec.EncodeHandler;
import com.tbtosoft.smio.handlers.ActiveAwareChannelHandler;
import com.tbtosoft.smio.handlers.KeepConnectionChannelHandler;
import com.tbtosoft.smio.utils.ChannelPipeHelper;


/**
 * @author chengchun
 *
 */
public class LongClientChain extends BasicChain {
	private final ICoder coder;
	private final long activeTimeMillis;
	private SocketAddress serverAddress;
	private ClientBootstrap clientBootstrap;
	private ChannelFactory clientChannelFactory;
	private Timer timer = new HashedWheelTimer();	
	private volatile Channel channel;
	public LongClientChain(SocketAddress socketAddress, long activeTimeMillis, ICoder coder){
		this.coder = coder;
		this.activeTimeMillis = activeTimeMillis;
		this.serverAddress = socketAddress;
		clientChannelFactory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());
		clientBootstrap = new ClientBootstrap(clientChannelFactory);	
		clientBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				final ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("DECODER", new DecodeHandler(LongClientChain.this.coder));
				pipeline.addLast("IDLE-STATE-HANDLER", new IdleStateHandler(timer, 0, 0, LongClientChain.this.activeTimeMillis, TimeUnit.MILLISECONDS));
				pipeline.addLast("ACTIVE-AWARE-HANDLER",new ActiveAwareChannelHandler());	
				pipeline.addLast("KEEP-ALIVE-HANDLER", new KeepConnectionChannelHandler());
				ChannelPipeHelper.addLast(pipeline, getSmsHandlerFactory().getPipeline());
				pipeline.addLast("ENCODER", new EncodeHandler(LongClientChain.this.coder));				
				return pipeline;
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.BasicChain#open()
	 */
	@Override
	public boolean open() {
		connect();
		return true;
	}
	
	public boolean isConnected(){
		if(null != this.channel){
			return this.channel.isConnected();
		}
		return false;
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
	}	
	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.BasicChain#close()
	 */
	@Override
	public void close() {
		if(null != this.timer){
			this.timer.stop();
		}
		if(null != clientChannelFactory){
			clientChannelFactory.releaseExternalResources();
		}
	}	
}
