/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smsp.sgip;

import java.net.SocketAddress;
import java.util.Collection;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

import com.tbtosoft.cmpp.DeliverReqPkg;
import com.tbtosoft.sgip.BindReqPkg;
import com.tbtosoft.sgip.BindRspPkg;
import com.tbtosoft.sgip.DeliverRspPkg;
import com.tbtosoft.sgip.ReportReqPkg;
import com.tbtosoft.sgip.ReportRspPkg;
import com.tbtosoft.sgip.SubmitReqPkg;
import com.tbtosoft.sgip.SubmitRspPkg;
import com.tbtosoft.sgip.UnBindReqPkg;
import com.tbtosoft.sgip.UnBindRspPkg;
import com.tbtosoft.smio.IClient;
import com.tbtosoft.smio.IoChannelHandler;
import com.tbtosoft.smio.IoClientServer;
import com.tbtosoft.smio.codec.SgipCoder;
import com.tbtosoft.smio.handlers.ActiveEvent;
import com.tbtosoft.smio.handlers.DefaultIoChannelHandler;
import com.tbtosoft.smio.handlers.ISgipHandler;
import com.tbtosoft.smio.handlers.KeepConnectionEvent;
import com.tbtosoft.smio.handlers.SimpleSgipHandler;
import com.tbtosoft.smsp.AbstractSP;

/**
 * @author chengchun
 *
 */
public final class ServiceProvider extends AbstractSP implements ISgipHandler, IoChannelHandler {
	private static final InternalLogger logger =
	        InternalLoggerFactory.getInstance(ServiceProvider.class);
	private IClient client;
	private Timer timer = new HashedWheelTimer();
	public ServiceProvider(SocketAddress localAddress, SocketAddress remoteAddress){
		IoClientServer ioClientServer = new IoClientServer(new SgipCoder(), localAddress, remoteAddress);		
		ioClientServer.setActiveTimeMillis(30000);
		ioClientServer.setTimer(this.timer);
		ioClientServer.setSmsHandler(new SimpleSgipHandler(this));
		ioClientServer.setIoChannelHandler(new DefaultIoChannelHandler(this));
		this.client = ioClientServer;
	}
	private void logined(boolean isLogined){
	}
	private void login(Channel channel){
		BindReqPkg bindReqPkg = new BindReqPkg();
		bindReqPkg.setName("test");
		bindReqPkg.setPassword("test");
		bindReqPkg.setType((byte)0x01);
		channel.write(bindReqPkg);
	}
	@Override
	public void received(ChannelHandlerContext ctx, BindReqPkg bindReqPkg) {
		logger.info(bindReqPkg.toString());
		BindRspPkg bindRspPkg = new BindRspPkg();
		bindRspPkg.setSequence(bindReqPkg.getSequence());
		bindRspPkg.setResult((byte)0);
		ctx.getChannel().write(bindRspPkg);		
	}

	@Override
	public void received(ChannelHandlerContext ctx, BindRspPkg bindRspPkg) {
		if(0 == bindRspPkg.getResult()){
			logined(true);
		} else {
			logined(false);
		}
	}

	@Override
	public boolean start() {
		this.client.connect();
		return false;
	}

	@Override
	public boolean send(String message, Collection<String> terminals) {
		
		return false;
	}

	@Override
	public void stop() {
		this.timer.stop();
		this.client.close();
		
	}

	@Override
	public void onChannelIdle(ChannelHandlerContext ctx, ActiveEvent e) {
		this.client.close();
	}

	@Override
	public void onChannelKeepAlive(ChannelHandlerContext ctx,
			KeepConnectionEvent e) {
		
	}

	@Override
	public void onChannelConnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) {
		logger.info(ctx.getChannel()+" connected.");
		login(ctx.getChannel());
	}

	@Override
	public void onChannelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) {
		logger.info(ctx.getChannel()+" disconnected.");
		logined(false);
	}

	@Override
	public void received(ChannelHandlerContext ctx, DeliverReqPkg deliverReqPkg) {
		
	}

	@Override
	public void received(ChannelHandlerContext ctx, DeliverRspPkg deliverRspPkg) {
		
	}

	@Override
	public void received(ChannelHandlerContext ctx, ReportReqPkg reportReqPkg) {
		
	}

	@Override
	public void received(ChannelHandlerContext ctx, ReportRspPkg reportRspPkg) {
		
	}

	@Override
	public void received(ChannelHandlerContext ctx, SubmitReqPkg submitReqPkg) {
		
	}

	@Override
	public void received(ChannelHandlerContext ctx, SubmitRspPkg submitRspPkg) {
		
	}

	@Override
	public void received(ChannelHandlerContext ctx, UnBindReqPkg unBindReqPkg) {
		
	}

	@Override
	public void received(ChannelHandlerContext ctx, UnBindRspPkg unBindRspPkg) {
		
	}
	
}
