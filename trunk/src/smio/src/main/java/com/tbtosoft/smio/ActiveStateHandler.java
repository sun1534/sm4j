/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio;

import static org.jboss.netty.channel.Channels.fireExceptionCaught;

import java.util.concurrent.TimeUnit;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.LifeCycleAwareChannelHandler;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.timeout.DefaultIdleStateEvent;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.util.ExternalResourceReleasable;
import org.jboss.netty.util.Timeout;
import org.jboss.netty.util.Timer;
import org.jboss.netty.util.TimerTask;

/**
 * @author chengchun
 *
 */
public class ActiveStateHandler extends SimpleChannelUpstreamHandler implements
		LifeCycleAwareChannelHandler, ExternalResourceReleasable {
	final Timer timer;
	final long activeTimeMillis;
	public ActiveStateHandler(Timer timer, long activeTimeMillis){
		this.timer = timer;
		this.activeTimeMillis = activeTimeMillis;
	}
	/* (non-Javadoc)
	 * @see org.jboss.netty.util.ExternalResourceReleasable#releaseExternalResources()
	 */
	@Override
	public void releaseExternalResources() {
		timer.stop();
	}

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.LifeCycleAwareChannelHandler#beforeAdd(org.jboss.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void beforeAdd(ChannelHandlerContext ctx) throws Exception {
		if (ctx.getPipeline().isAttached()) {
            // channelOpen event has been fired already, which means
            // this.channelOpen() will not be invoked.
            // We have to initialize here instead.
            initialize(ctx);
        } else {
            // channelOpen event has not been fired yet.
            // this.channelOpen() will be invoked and initialization will occur there.
        }
	}
	private void initialize(ChannelHandlerContext ctx) {
		State state = new State();
		ctx.setAttachment(state);
		state.lastActiveTime = System.currentTimeMillis();
		if(activeTimeMillis > 0){
			state.activeIdleTimeout = this.timer.newTimeout(
					new ActiveIdleTimeoutTask(ctx), 
					activeTimeMillis, TimeUnit.MILLISECONDS);
		}       
	}
	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.LifeCycleAwareChannelHandler#afterAdd(org.jboss.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void afterAdd(ChannelHandlerContext ctx) throws Exception {
		// NOOP
	}

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.LifeCycleAwareChannelHandler#beforeRemove(org.jboss.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void beforeRemove(ChannelHandlerContext ctx) throws Exception {
		destroy(ctx);
	}
	private void destroy(ChannelHandlerContext ctx) {		
		State state = (State) ctx.getAttachment();
		if(state.activeIdleTimeout != null){
			state.activeIdleTimeout.cancel();
			state.activeIdleTimeout = null;
		}       
	}
	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.LifeCycleAwareChannelHandler#afterRemove(org.jboss.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void afterRemove(ChannelHandlerContext ctx) throws Exception {
		// NOOP
	}
		
	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#handleUpstream(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelEvent)
	 */
	@Override
	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e)
			throws Exception {
		if(e instanceof ActiveMessageEvent){
			State state = (State)ctx.getAttachment();
			state.lastActiveTime = System.currentTimeMillis();
		} else {
			super.handleUpstream(ctx, e);
		}
	}
	protected void channelIdle(
            ChannelHandlerContext ctx, IdleState state, long lastActivityTimeMillis) throws Exception {
        ctx.sendUpstream(new DefaultIdleStateEvent(ctx.getChannel(), state, lastActivityTimeMillis));
    }
	private final class ActiveIdleTimeoutTask implements TimerTask {

        private final ChannelHandlerContext ctx;

        ActiveIdleTimeoutTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        public void run(Timeout timeout) throws Exception {
            if (timeout.isCancelled() || !ctx.getChannel().isOpen()) {
                return;
            }

            State state = (State) ctx.getAttachment();
            long currentTime = System.currentTimeMillis();
            long lastReadTime = state.lastActiveTime;
            long nextDelay = activeTimeMillis - (currentTime - lastReadTime);
            if (nextDelay <= 0) {
                // Reader is idle - set a new timeout and notify the callback.
                state.activeIdleTimeout =
                    timer.newTimeout(this, activeTimeMillis, TimeUnit.MILLISECONDS);
                try {
                    channelIdle(ctx, IdleState.READER_IDLE, lastReadTime);
                } catch (Throwable t) {
                    fireExceptionCaught(ctx, t);
                }
            } else {
                // Read occurred before the timeout - set a new timeout with shorter delay.
                state.activeIdleTimeout =
                    timer.newTimeout(this, nextDelay, TimeUnit.MILLISECONDS);
            }
        }

    }
	   
	private static final class State {
        State() {
            super();
        }
        volatile Timeout activeIdleTimeout;
        volatile long lastActiveTime;               
    }
}
