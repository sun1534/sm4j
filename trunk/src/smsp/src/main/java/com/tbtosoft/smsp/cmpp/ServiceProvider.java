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
import org.jboss.netty.channel.ChannelStateEvent;

import com.tbtosoft.cmpp.ConnectReqPkg;
import com.tbtosoft.cmpp.ConnectRspPkg;
import com.tbtosoft.cmpp.IPackage;
import com.tbtosoft.smio.IChain;
import com.tbtosoft.smio.ICoder;
import com.tbtosoft.smio.LongChain;
import com.tbtosoft.smio.ShortChain;
import com.tbtosoft.smio.codec.CmppCoder;
import com.tbtosoft.smio.handlers.ActiveEvent;
import com.tbtosoft.smio.handlers.KeepAliveEvent;
import com.tbtosoft.smio.handlers.SimpleCmppHandler;
import com.tbtosoft.smsp.AbstractSP;

/**
 * @author chengchun
 *
 */
public final class ServiceProvider extends AbstractSP{
	private IChain chain;
	public ServiceProvider(SocketAddress serverAddress){
		this.chain = new LongChain<IPackage, ICoder<IPackage>>(serverAddress, 30000, new CmppCoder());
		this.chain.addHandler("INNER-SMS-IO-HANDLER", new InnerSmsIoHandler());
	}
	public ServiceProvider(SocketAddress serverAddress, SocketAddress localAddress){
		this.chain = new ShortChain<IPackage, ICoder<IPackage>>(serverAddress, localAddress, 30000, new CmppCoder());
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
	
	class InnerSmsIoHandler extends SimpleCmppHandler{

		/* (non-Javadoc)
		 * @see com.tbtosoft.smio.SmsIoHandler#onChannelIdle(org.jboss.netty.channel.ChannelHandlerContext, com.tbtosoft.smio.handlers.ActiveEvent)
		 */
		@Override
		protected void onChannelIdle(ChannelHandlerContext ctx, ActiveEvent e) {
			
		}

		/* (non-Javadoc)
		 * @see com.tbtosoft.smio.SmsIoHandler#onChannelKeepAlive(org.jboss.netty.channel.ChannelHandlerContext, com.tbtosoft.smio.handlers.KeepAliveEvent)
		 */
		@Override
		protected void onChannelKeepAlive(ChannelHandlerContext ctx,
				KeepAliveEvent e) {

		}

		/* (non-Javadoc)
		 * @see com.tbtosoft.smio.SmsIoHandler#onChannelConnected(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
		 */
		@Override
		protected void onChannelConnected(ChannelHandlerContext ctx,
				ChannelStateEvent e) {
			
		}

		/* (non-Javadoc)
		 * @see com.tbtosoft.smio.SmsIoHandler#onChannelDisconnected(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
		 */
		@Override
		protected void onChannelDisconnected(ChannelHandlerContext ctx,
				ChannelStateEvent e) {
			
		}

		@Override
		public void received(ChannelHandlerContext ctx,
				ConnectReqPkg connectReqPkg) {
			
		}

		@Override
		public void received(ChannelHandlerContext ctx,
				ConnectRspPkg connectRspPkg) {
			
		}		
	}		
}
