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

import com.tbtosoft.sgip.BindReqPkg;
import com.tbtosoft.sgip.BindRspPkg;
import com.tbtosoft.sgip.DeliverReqPkg;
import com.tbtosoft.sgip.DeliverRspPkg;
import com.tbtosoft.sgip.ReportReqPkg;
import com.tbtosoft.sgip.ReportRspPkg;
import com.tbtosoft.sgip.SubmitReqPkg;
import com.tbtosoft.sgip.SubmitRspPkg;
import com.tbtosoft.sgip.UnBindReqPkg;
import com.tbtosoft.sgip.UnBindRspPkg;

/**
 * @author chengchun
 *
 */
public class SimpleSgipHandler extends DefaultSmsHandler implements ISgipHandler{
	
	private final ISgipHandler sgipHandler;
	
	public SimpleSgipHandler(ISgipHandler sgipHandler){		
		this.sgipHandler = sgipHandler;		
	}

	@Override
	public void received(ChannelHandlerContext ctx, BindReqPkg bindReqPkg) {
		this.sgipHandler.received(ctx, bindReqPkg);
	}

	@Override
	public void received(ChannelHandlerContext ctx, BindRspPkg bindRspPkg) {
		this.sgipHandler.received(ctx, bindRspPkg);
	}

	@Override
	public void received(ChannelHandlerContext ctx, DeliverReqPkg deliverReqPkg) {
		this.sgipHandler.received(ctx, deliverReqPkg);
	}

	@Override
	public void received(ChannelHandlerContext ctx, DeliverRspPkg deliverRspPkg) {
		this.sgipHandler.received(ctx, deliverRspPkg);
	}

	@Override
	public void received(ChannelHandlerContext ctx, ReportReqPkg reportReqPkg) {
		this.sgipHandler.received(ctx, reportReqPkg);
	}

	@Override
	public void received(ChannelHandlerContext ctx, ReportRspPkg reportRspPkg) {
		this.sgipHandler.received(ctx, reportRspPkg);
	}

	@Override
	public void received(ChannelHandlerContext ctx, SubmitReqPkg submitReqPkg) {
		this.sgipHandler.received(ctx, submitReqPkg);
	}

	@Override
	public void received(ChannelHandlerContext ctx, SubmitRspPkg submitRspPkg) {
		this.sgipHandler.received(ctx, submitRspPkg);
	}

	@Override
	public void received(ChannelHandlerContext ctx, UnBindReqPkg unBindReqPkg) {
		this.sgipHandler.received(ctx, unBindReqPkg);
	}

	@Override
	public void received(ChannelHandlerContext ctx, UnBindRspPkg unBindRspPkg) {
		this.sgipHandler.received(ctx, unBindRspPkg);
	}

	@Override
	protected void receiveImpl(ChannelHandlerContext ctx, Object obj) {
		if(obj instanceof BindReqPkg){
			received(ctx, (BindReqPkg)obj);
		} else if(obj instanceof BindRspPkg){
			received(ctx, (BindRspPkg)obj);
		} else if(obj instanceof DeliverReqPkg){
			received(ctx, (DeliverReqPkg)obj);
		} else if(obj instanceof DeliverRspPkg){
			received(ctx, (DeliverRspPkg)obj);
		} else if(obj instanceof SubmitReqPkg){
			received(ctx, (SubmitReqPkg)obj);
		} else if(obj instanceof SubmitRspPkg){
			received(ctx, (SubmitRspPkg)obj);
		} else if(obj instanceof ReportReqPkg){
			received(ctx, (ReportReqPkg)obj);
		} else if(obj instanceof ReportRspPkg){
			received(ctx, (ReportRspPkg)obj);
		} else if(obj instanceof UnBindReqPkg){
			received(ctx, (UnBindReqPkg)obj);
		} else if(obj instanceof UnBindRspPkg){
			received(ctx, (UnBindRspPkg)obj);
		}
	}

}
