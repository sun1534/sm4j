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
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timeout;
import org.jboss.netty.util.Timer;
import org.jboss.netty.util.TimerTask;

import com.tbtosoft.smio.handlers.ActiveEvent;

/**
 * @author chengchun
 *
 */
public class LongChain<E, T extends ICoder<E>> extends BasicChain {
	private final T coder;
	private final long activeTimeMillis;
	private SocketAddress serverAddress;
	private ClientBootstrap clientBootstrap;
	private ChannelFactory clientChannelFactory;
	private Timer timer = new HashedWheelTimer();
	private Timeout timeout;
	private volatile Channel channel;
	public LongChain(SocketAddress socketAddress, long activeTimeMillis, T coder){
		this.coder = coder;
		this.activeTimeMillis = activeTimeMillis;
		this.serverAddress = socketAddress;
		clientChannelFactory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());
		clientBootstrap = new ClientBootstrap(clientChannelFactory);	
		clientBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline channelPipeline = getChannelPipeline();
				channelPipeline.addLast("IDLE-STATE-HANDLER", new IdleStateHandler(new HashedWheelTimer(), 0, 0, LongChain.this.activeTimeMillis, TimeUnit.MILLISECONDS));
				channelPipeline.addLast("", new IdleStateAwareChannelHandler(){

					/* (non-Javadoc)
					 * @see org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler#channelIdle(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.handler.timeout.IdleStateEvent)
					 */
					@Override
					public void channelIdle(ChannelHandlerContext ctx,
							IdleStateEvent e) throws Exception {
						if(IdleState.ALL_IDLE == e.getState()){
							ctx.sendUpstream(new ActiveEvent(e.getChannel(), e.getLastActivityTimeMillis()));
						}
						super.channelIdle(ctx, e);
					}						
				});				
				channelPipeline.addLast("ENCODER", new Encoder<E, T>(LongChain.this.coder));
				channelPipeline.addLast("DECODER", new Decoder<E, T>(LongChain.this.coder));
				return channelPipeline;
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
	
	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.BasicChain#write(java.lang.Object)
	 */
	@Override
	public synchronized boolean write(Object object) {
		if(isConnected()){
			this.channel.write(object);
			return true;
		}
		return false;
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
			scheduleReconnect();
			return ;
		}
		this.channel = channelFuture.getChannel();
		this.channel.getCloseFuture().addListener(new ChannelFutureListener() {
			
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {				
				scheduleReconnect();
			}
		});		
	}
	private void scheduleReconnect(){
		this.timeout = this.timer.newTimeout(new TimerTask() {
			
			@Override
			public void run(Timeout timeout) throws Exception {
				connect();				
			}
		}, 3000, TimeUnit.MILLISECONDS);
	}
	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.BasicChain#close()
	 */
	@Override
	public void close() {
		if(null != this.timeout){
			this.timeout.cancel();
			this.timeout = null;
		}
		if(null != clientChannelFactory){
			clientChannelFactory.releaseExternalResources();
		}
	}	
}
