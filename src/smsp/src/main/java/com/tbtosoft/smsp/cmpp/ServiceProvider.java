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

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;

import com.tbtosoft.cmpp.ActiveTestReqPkg;
import com.tbtosoft.cmpp.ActiveTestRspPkg;
import com.tbtosoft.cmpp.ConnectReqPkg;
import com.tbtosoft.cmpp.ConnectRspPkg;
import com.tbtosoft.cmpp.DeliverReqPkg;
import com.tbtosoft.cmpp.IPackage;
import com.tbtosoft.cmpp.SubmitRspPkg;
import com.tbtosoft.cmpp.TerminateReqPkg;
import com.tbtosoft.cmpp.TerminateRspPkg;
import com.tbtosoft.smio.IChain;
import com.tbtosoft.smio.ICoder;
import com.tbtosoft.smio.ISmsHandlerFactory;
import com.tbtosoft.smio.LongClientChain;
import com.tbtosoft.smio.ShortChain;
import com.tbtosoft.smio.codec.CmppCoder;
import com.tbtosoft.smio.handlers.ActiveEvent;
import com.tbtosoft.smio.handlers.KeepConnectionEvent;
import com.tbtosoft.smio.handlers.SimpleCmppHandler;
import com.tbtosoft.smsp.AbstractSP;

/**
 * @author chengchun
 *
 */
public final class ServiceProvider extends AbstractSP{
	private static final InternalLogger logger =
	        InternalLoggerFactory.getInstance(ServiceProvider.class);
	private IChain chain;
	public ServiceProvider(SocketAddress serverAddress){
		this.chain = new LongClientChain<IPackage, ICoder<IPackage>>(serverAddress, 30000, new CmppCoder());		
		initialize();
	}
	public ServiceProvider(SocketAddress serverAddress, SocketAddress localAddress){
		this.chain = new ShortChain<IPackage, ICoder<IPackage>>(serverAddress, localAddress, 30000, new CmppCoder());
		initialize();
	}
	private void initialize(){
		this.chain.setSmsHandlerFactory(new ISmsHandlerFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				return  Channels.pipeline(new InnerSmsIoHandler().getChannelHandler());
			}
		});
	}
	@Override
	public boolean start() {		
		return this.chain.open();
	}
	@Override
	public void stop() {
		this.chain.close();
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
	private void login(){
		ConnectReqPkg connectReqPkg = new ConnectReqPkg("1234", "1234");		
		connectReqPkg.setVersion((byte)0x20);
		this.chain.write(connectReqPkg);
	}
	private void activeTest(){		
		this.chain.write(new ActiveTestReqPkg());
	}
	private void write(ChannelHandlerContext ctx, IPackage t){
		ctx.getChannel().write(t);
	}
	class InnerSmsIoHandler extends SimpleCmppHandler{
		
		/* (non-Javadoc)
		 * @see com.tbtosoft.smio.SmsIoHandler#onChannelIdle(org.jboss.netty.channel.ChannelHandlerContext, com.tbtosoft.smio.handlers.ActiveEvent)
		 */
		@Override
		protected void onChannelIdle(ChannelHandlerContext ctx, ActiveEvent e) {
			logger.info(ctx.getChannel()+" idle.");
			activeTest();
		}

		/* (non-Javadoc)
		 * @see com.tbtosoft.smio.SmsIoHandler#onChannelKeepAlive(org.jboss.netty.channel.ChannelHandlerContext, com.tbtosoft.smio.handlers.KeepAliveEvent)
		 */
		@Override
		protected void onChannelKeepAlive(ChannelHandlerContext ctx,
				KeepConnectionEvent e) {
			logger.info("try open");
			ServiceProvider.this.chain.open();
		}

		/* (non-Javadoc)
		 * @see com.tbtosoft.smio.SmsIoHandler#onChannelConnected(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
		 */
		@Override
		protected void onChannelConnected(ChannelHandlerContext ctx,
				ChannelStateEvent e) {
			logger.info(ctx.getChannel()+" connected.");
			login();
		}

		/* (non-Javadoc)
		 * @see com.tbtosoft.smio.SmsIoHandler#onChannelDisconnected(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
		 */
		@Override
		protected void onChannelDisconnected(ChannelHandlerContext ctx,
				ChannelStateEvent e) {
			logger.info(ctx.getChannel()+" disconnected.");
		}

		@Override
		public void received(ChannelHandlerContext ctx,
				ConnectReqPkg connectReqPkg) {
			logger.info("");
		}

		@Override
		public void received(ChannelHandlerContext ctx,
				ConnectRspPkg connectRspPkg) {
			logger.info(connectRspPkg.toString());
		}
		/* (non-Javadoc)
		 * @see com.tbtosoft.smio.handlers.SimpleCmppHandler#received(org.jboss.netty.channel.ChannelHandlerContext, com.tbtosoft.cmpp.TerminateReqPkg)
		 */
		@Override
		public void received(ChannelHandlerContext ctx,
				TerminateReqPkg terminateReqPkg) {			
			TerminateRspPkg terminateRspPkg = new TerminateRspPkg();
			terminateRspPkg.setSequence(terminateReqPkg.getSequence());
			write(ctx, terminateRspPkg);
			
		}

		/* (non-Javadoc)
		 * @see com.tbtosoft.smio.handlers.SimpleCmppHandler#received(org.jboss.netty.channel.ChannelHandlerContext, com.tbtosoft.cmpp.TerminateRspPkg)
		 */
		@Override
		public void received(ChannelHandlerContext ctx,
				TerminateRspPkg terminateRspPkg) {
			
		}
		/* (non-Javadoc)
		 * @see com.tbtosoft.smio.handlers.SimpleCmppHandler#received(org.jboss.netty.channel.ChannelHandlerContext, com.tbtosoft.cmpp.ActiveTestReqPkg)
		 */
		@Override
		public void received(ChannelHandlerContext ctx,
				ActiveTestReqPkg activeTestReqPkg) {
			
		}

		/* (non-Javadoc)
		 * @see com.tbtosoft.smio.handlers.SimpleCmppHandler#received(org.jboss.netty.channel.ChannelHandlerContext, com.tbtosoft.cmpp.ActiveTestRspPkg)
		 */
		@Override
		public void received(ChannelHandlerContext ctx,
				ActiveTestRspPkg activeTestRspPkg) {
			logger.info(activeTestRspPkg.toString());
		}

		/* (non-Javadoc)
		 * @see com.tbtosoft.smio.handlers.SimpleCmppHandler#received(org.jboss.netty.channel.ChannelHandlerContext, com.tbtosoft.cmpp.SubmitRspPkg)
		 */
		@Override
		public void received(ChannelHandlerContext ctx,
				SubmitRspPkg submitRspPkg) {
			
		}
		
		/* (non-Javadoc)
		 * @see com.tbtosoft.smio.handlers.SimpleCmppHandler#received(org.jboss.netty.channel.ChannelHandlerContext, com.tbtosoft.cmpp.DeliverReqPkg)
		 */
		@Override
		public void received(ChannelHandlerContext ctx,
				DeliverReqPkg deliverReqPkg) {
			
		}		
	}
	
}
