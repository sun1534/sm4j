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
import com.tbtosoft.cmpp.SubmitReqPkg;
import com.tbtosoft.cmpp.SubmitRspPkg;
import com.tbtosoft.cmpp.TerminateReqPkg;
import com.tbtosoft.cmpp.TerminateRspPkg;

/**
 * @author chengchun
 *
 */
public class SimpleCmppHandler extends DefaultSmsHandler implements ICmppHandler{
	private final ICmppHandler cmppHandler;
	
	public SimpleCmppHandler(ICmppHandler cmppHandler){		
		this.cmppHandler = cmppHandler;		
	}
	@Override
	public final void received(ChannelHandlerContext ctx, ConnectReqPkg connectReqPkg) {
		this.cmppHandler.received(ctx, connectReqPkg);
	}

	@Override
	public final void received(ChannelHandlerContext ctx, ConnectRspPkg connectRspPkg) {
		this.cmppHandler.received(ctx, connectRspPkg);
	}

	@Override
	public final void received(ChannelHandlerContext ctx,	TerminateReqPkg terminateReqPkg) {
		this.cmppHandler.received(ctx, terminateReqPkg);
	}

	@Override
	public final void received(ChannelHandlerContext ctx,	TerminateRspPkg terminateRspPkg) {
		this.cmppHandler.received(ctx, terminateRspPkg);
	}
	@Override
	public final void received(ChannelHandlerContext ctx,	ActiveTestReqPkg activeTestReqPkg) {
		this.cmppHandler.received(ctx, activeTestReqPkg);
	}

	@Override
	public final void received(ChannelHandlerContext ctx,	ActiveTestRspPkg activeTestRspPkg) {
		this.cmppHandler.received(ctx, activeTestRspPkg);
	}

	@Override
	public final void received(ChannelHandlerContext ctx, SubmitReqPkg submitReqPkg) {
		this.cmppHandler.received(ctx, submitReqPkg);
	}

	@Override
	public final void received(ChannelHandlerContext ctx, SubmitRspPkg submitRspPkg) {
		this.cmppHandler.received(ctx, submitRspPkg);
	}

	@Override
	public final void received(ChannelHandlerContext ctx, DeliverReqPkg deliverReqPkg) {
		this.cmppHandler.received(ctx, deliverReqPkg);
	}

	@Override
	public final void received(ChannelHandlerContext ctx, DeliverRspPkg deliverRspPkg) {
		this.cmppHandler.received(ctx, deliverRspPkg);
	}

	@Override
	protected void receiveImpl(ChannelHandlerContext ctx, Object obj) {
		if(obj instanceof ConnectReqPkg){
			received(ctx, (ConnectReqPkg)obj);
		} else if(obj instanceof ConnectRspPkg){
			received(ctx, (ConnectRspPkg)obj);
		} else if(obj instanceof TerminateReqPkg){
			received(ctx, (TerminateReqPkg)obj);
		} else if(obj instanceof TerminateRspPkg){
			received(ctx, (TerminateRspPkg)obj);
		} else if(obj instanceof SubmitReqPkg){
			received(ctx, (SubmitReqPkg)obj);
		} else if(obj instanceof SubmitRspPkg){
			received(ctx, (SubmitRspPkg)obj);
		} else if(obj instanceof DeliverReqPkg){
			received(ctx, (DeliverReqPkg)obj);
		} else if(obj instanceof DeliverRspPkg){
			received(ctx, (DeliverRspPkg)obj);
		} else if(obj instanceof ActiveTestReqPkg){
			received(ctx, (ActiveTestReqPkg)obj);
		} else if(obj instanceof ActiveTestRspPkg){
			received(ctx, (ActiveTestRspPkg)obj);
		}
	}
//	@Override
//	public void onChannelConnected(ChannelHandlerContext ctx,
//			ChannelStateEvent e) {
//		this.cmppHandler.onChannelConnected(ctx, e);
//	}
//	@Override
//	public void onChannelDisconnected(ChannelHandlerContext ctx,
//			ChannelStateEvent e) {
//		this.cmppHandler.onChannelDisconnected(ctx, e);
//	}
//	@Override
//	public void onChannelAccepted(ChannelHandlerContext ctx, ChannelStateEvent e) {
//		this.cmppHandler.onChannelAccepted(ctx, e);
//	}
//	@Override
//	public void onChannelIdle(ChannelHandlerContext ctx, ActiveEvent e) {
//		this.cmppHandler.onChannelIdle(ctx, e);
//	}

}
