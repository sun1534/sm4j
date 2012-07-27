/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smsp.cmpp;

import java.net.SocketAddress;
import java.util.Collection;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

import com.tbtosoft.cmpp.ActiveTestReqPkg;
import com.tbtosoft.cmpp.ActiveTestRspPkg;
import com.tbtosoft.cmpp.ConnectReqPkg;
import com.tbtosoft.cmpp.ConnectRspPkg;
import com.tbtosoft.cmpp.DeliverReqPkg;
import com.tbtosoft.cmpp.DeliverRspPkg;
import com.tbtosoft.cmpp.SubmitReqPkg;
import com.tbtosoft.cmpp.SubmitRspPkg;
import com.tbtosoft.cmpp.TerminateReqPkg;
import com.tbtosoft.cmpp.TerminateRspPkg;
import com.tbtosoft.smio.IClient;
import com.tbtosoft.smio.IoChannelHandler;
import com.tbtosoft.smio.IoClient;
import com.tbtosoft.smio.IoClientServer;
import com.tbtosoft.smio.codec.CmppCoder;
import com.tbtosoft.smio.handlers.ActiveEvent;
import com.tbtosoft.smio.handlers.DefaultIoChannelHandler;
import com.tbtosoft.smio.handlers.ICmppHandler;
import com.tbtosoft.smio.handlers.KeepConnectionEvent;
import com.tbtosoft.smio.handlers.SimpleCmppHandler;
import com.tbtosoft.smsp.AbstractSP;

/**
 * @author chengchun
 *
 */
public final class ServiceProvider extends AbstractSP implements ICmppHandler, IoChannelHandler{
	private static final InternalLogger logger =
	        InternalLoggerFactory.getInstance(ServiceProvider.class);
	private IClient client;
	private volatile boolean logined;
	private volatile Channel channel;//不管长短连接 只需要 知道 保留client端的channel就可以了
	private Timer timer = new HashedWheelTimer();
	public ServiceProvider(SocketAddress remoteAddress){		
		IoClient ioClient = new IoClient(new CmppCoder(), remoteAddress);
		ioClient.setActiveTimeMillis(30000);
		ioClient.setTimer(this.timer);
		ioClient.setClientSmsHandler(new SimpleCmppHandler(this));
		ioClient.setIoChannelHandler(new DefaultIoChannelHandler(this));
		this.client = ioClient;
	}
	public ServiceProvider(SocketAddress remoteAddress, SocketAddress localAddress){
		IoClientServer ioClientServer = new IoClientServer(new CmppCoder(), localAddress, remoteAddress);
		ioClientServer.setActiveTimeMillis(30000);
		ioClientServer.setTimer(this.timer);
		ioClientServer.setIoChannelHandler(new DefaultIoChannelHandler(this));
		ioClientServer.setClientSmsHandler(new SimpleCmppHandler(this));
		ioClientServer.setServerSmsHandler(new SimpleCmppHandler(this));
		this.client = ioClientServer;		
	}
	
	@Override
	public boolean start() {	
		this.client.connect();
		return true;
	}
	@Override
	public void stop() {
		if(null != this.timer){
			this.timer.stop();
		}
		this.client.close();			
	}	
	/* (non-Javadoc)
	 * @see com.tbtosoft.smsp.ISP#send(java.lang.String, java.util.Collection)
	 */
	@Override
	public boolean send(String message, Collection<String> terminals) {
//		SubmitReqPkg submitReqPkg = new SubmitReqPkg();
//		submitReqPkg.setDestTerminalId(terminals);		
//		return link.write(submitReqPkg);
		return true;
	}
	private void login(Channel channel){
		ConnectReqPkg connectReqPkg = new ConnectReqPkg("1234", "1234");		
		connectReqPkg.setVersion((byte)0x20);
		channel.write(connectReqPkg);
	}
	private void activeTest(Channel channel){		
		channel.write(new ActiveTestReqPkg());
	}
	private void write(ChannelHandlerContext ctx, Object object){
		ctx.getChannel().write(object);
	}
	@Override
	public void onChannelIdle(ChannelHandlerContext ctx, ActiveEvent e) {
		activeTest(ctx.getChannel());
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
	}
	@Override
	public void received(ChannelHandlerContext ctx, ConnectReqPkg connectReqPkg) {
		
	}
	@Override
	public void received(ChannelHandlerContext ctx, ConnectRspPkg connectRspPkg) {
		if(0 == connectRspPkg.getStatus()){
			logined(true);
			this.channel = ctx.getChannel();
		}
	}
	private void logined(boolean isLogined){
		this.logined = isLogined;
	}
	@Override
	public void received(ChannelHandlerContext ctx,
			TerminateReqPkg terminateReqPkg) {
		TerminateRspPkg terminateRspPkg = new TerminateRspPkg();
		terminateRspPkg.setSequence(terminateReqPkg.getSequence());
		write(ctx, terminateRspPkg);	
	}
	@Override
	public void received(ChannelHandlerContext ctx,
			TerminateRspPkg terminateRspPkg) {
		
	}
	@Override
	public void received(ChannelHandlerContext ctx,
			ActiveTestReqPkg activeTestReqPkg) {
		
	}
	@Override
	public void received(ChannelHandlerContext ctx,
			ActiveTestRspPkg activeTestRspPkg) {
		logger.info(activeTestRspPkg.toString());
	}
	@Override
	public void received(ChannelHandlerContext ctx, SubmitReqPkg submitReqPkg) {
		
	}
	@Override
	public void received(ChannelHandlerContext ctx, SubmitRspPkg submitRspPkg) {
		
	}
	@Override
	public void received(ChannelHandlerContext ctx, DeliverReqPkg deliverReqPkg) {
		
	}
	@Override
	public void received(ChannelHandlerContext ctx, DeliverRspPkg deliverRspPkg) {
		
	}	
}
