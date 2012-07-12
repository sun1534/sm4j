/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio.handlers;

import org.jboss.netty.channel.ChannelHandlerContext;

import com.tbtosoft.cmpp.ConnectReqPkg;
import com.tbtosoft.cmpp.ConnectRspPkg;
import com.tbtosoft.cmpp.IPackage;
import com.tbtosoft.smio.SmsIoHandler;

/**
 * @author chengchun
 *
 */
public class SimpleCmppHandler extends SmsIoHandler<IPackage> implements ICmppHander{
	
	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.handlers.IOCmppHander#received(org.jboss.netty.channel.ChannelHandlerContext, com.tbtosoft.cmpp.ConnectReqPkg)
	 */
	@Override
	public void received(ChannelHandlerContext ctx, ConnectReqPkg connectReqPkg) {
		
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.handlers.IOCmppHander#received(org.jboss.netty.channel.ChannelHandlerContext, com.tbtosoft.cmpp.ConnectRspPkg)
	 */
	@Override
	public void received(ChannelHandlerContext ctx, ConnectRspPkg connectRspPkg) {
		
	}

	@Override
	protected void receiveImpl(ChannelHandlerContext ctx, IPackage t) {
		
	}

}
