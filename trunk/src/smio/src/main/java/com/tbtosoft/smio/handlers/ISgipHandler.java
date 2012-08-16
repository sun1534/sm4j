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
public interface ISgipHandler {
	public void received(ChannelHandlerContext ctx, BindReqPkg bindReqPkg);
	public void received(ChannelHandlerContext ctx, BindRspPkg bindRspPkg);
	public void received(ChannelHandlerContext ctx, DeliverReqPkg deliverReqPkg);
	public void received(ChannelHandlerContext ctx, DeliverRspPkg deliverRspPkg);
	public void received(ChannelHandlerContext ctx, ReportReqPkg reportReqPkg);
	public void received(ChannelHandlerContext ctx, ReportRspPkg reportRspPkg);
	public void received(ChannelHandlerContext ctx, SubmitReqPkg submitReqPkg);
	public void received(ChannelHandlerContext ctx, SubmitRspPkg submitRspPkg);
	public void received(ChannelHandlerContext ctx, UnBindReqPkg unBindReqPkg);
	public void received(ChannelHandlerContext ctx, UnBindRspPkg unBindRspPkg);
}
