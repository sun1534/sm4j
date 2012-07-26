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

import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;

import com.tbtosoft.sgip.BindReqPkg;
import com.tbtosoft.sgip.BindRspPkg;
import com.tbtosoft.smio.IClient;
import com.tbtosoft.smio.ISmsHandler;
import com.tbtosoft.smio.IoClientServer;
import com.tbtosoft.smio.codec.SgipCoder;
import com.tbtosoft.smio.handlers.ActiveEvent;
import com.tbtosoft.smio.handlers.ISgipHandler;
import com.tbtosoft.smio.handlers.KeepConnectionEvent;
import com.tbtosoft.smio.handlers.SimpleSgipHandler;
import com.tbtosoft.smsp.AbstractSP;

/**
 * @author chengchun
 *
 */
public final class ServiceProvider extends AbstractSP implements ISgipHandler, ISmsHandler {
	private IClient client;
	public ServiceProvider(SocketAddress localAddress, SocketAddress remoteAddress){
		IoClientServer ioClientServer = new IoClientServer(new SgipCoder(), localAddress, remoteAddress);
		ioClientServer.setSmsHandler(new SimpleSgipHandler());
		this.client = ioClientServer;
	}
	@Override
	public ChannelHandler getChannelHandler() {
		
		return null;
	}

	@Override
	public boolean write(Object object) {
		
		return false;
	}

	@Override
	public void onChannelIdle(ChannelHandlerContext ctx, ActiveEvent e) {
		
	}

	@Override
	public void onChannelKeepAlive(ChannelHandlerContext ctx,
			KeepConnectionEvent e) {
		
	}

	@Override
	public void onChannelConnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) {
		
	}

	@Override
	public void onChannelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) {
		
	}

	@Override
	public void received(ChannelHandlerContext ctx, BindReqPkg bindReqPkg) {
		
	}

	@Override
	public void received(ChannelHandlerContext ctx, BindRspPkg bindRspPkg) {
		
	}

	@Override
	public boolean start() {
		
		return false;
	}

	@Override
	public boolean send(String message, Collection<String> terminals) {
		
		return false;
	}

	@Override
	public void stop() {
		
		
	}
	
}
