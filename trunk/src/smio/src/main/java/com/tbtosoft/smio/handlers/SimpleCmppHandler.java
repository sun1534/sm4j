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

import com.tbtosoft.cmpp.ActiveTestReqPkg;
import com.tbtosoft.cmpp.ActiveTestRspPkg;
import com.tbtosoft.cmpp.ConnectReqPkg;
import com.tbtosoft.cmpp.ConnectRspPkg;
import com.tbtosoft.cmpp.DeliverReqPkg;
import com.tbtosoft.cmpp.DeliverRspPkg;
import com.tbtosoft.cmpp.IPackage;
import com.tbtosoft.cmpp.SubmitReqPkg;
import com.tbtosoft.cmpp.SubmitRspPkg;
import com.tbtosoft.smio.SmsIoHandler;

/**
 * @author chengchun
 *
 */
public class SimpleCmppHandler extends SmsIoHandler<IPackage> implements ICmppHander{

	@Override
	public void received(ChannelHandlerContext ctx, ConnectReqPkg connectReqPkg) {
		
	}

	@Override
	public void received(ChannelHandlerContext ctx, ConnectRspPkg connectRspPkg) {
		
	}

	@Override
	public void received(ChannelHandlerContext ctx,
			ActiveTestReqPkg activeTestReqPkg) {
		
	}

	@Override
	public void received(ChannelHandlerContext ctx,
			ActiveTestRspPkg activeTestRspPkg) {
		
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

	@Override
	protected void receiveImpl(ChannelHandlerContext ctx, IPackage t) {
		if(t instanceof ConnectReqPkg){
			received(ctx, (ConnectReqPkg)t);
		} else if(t instanceof ConnectRspPkg){
			received(ctx, (ConnectRspPkg)t);
		} else if(t instanceof SubmitReqPkg){
			received(ctx, (SubmitReqPkg)t);
		} else if(t instanceof SubmitRspPkg){
			received(ctx, (SubmitRspPkg)t);
		} else if(t instanceof DeliverReqPkg){
			received(ctx, (DeliverReqPkg)t);
		} else if(t instanceof DeliverRspPkg){
			received(ctx, (DeliverRspPkg)t);
		} else if(t instanceof ActiveTestReqPkg){
			received(ctx, (ActiveTestReqPkg)t);
		} else if(t instanceof ActiveTestRspPkg){
			received(ctx, (ActiveTestRspPkg)t);
		}
	}
}
