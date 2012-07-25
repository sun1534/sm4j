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
public interface ICmppHandler {
	public void received(ChannelHandlerContext ctx, ConnectReqPkg connectReqPkg);
	public void received(ChannelHandlerContext ctx, ConnectRspPkg connectRspPkg);
	public void received(ChannelHandlerContext ctx, TerminateReqPkg terminateReqPkg);
	public void received(ChannelHandlerContext ctx, TerminateRspPkg terminateRspPkg);
	public void received(ChannelHandlerContext ctx, ActiveTestReqPkg activeTestReqPkg);
	public void received(ChannelHandlerContext ctx, ActiveTestRspPkg activeTestRspPkg);
	public void received(ChannelHandlerContext ctx, SubmitReqPkg submitReqPkg);
	public void received(ChannelHandlerContext ctx, SubmitRspPkg submitRspPkg);
	public void received(ChannelHandlerContext ctx, DeliverReqPkg deliverReqPkg);
	public void received(ChannelHandlerContext ctx, DeliverRspPkg deliverRspPkg);
}
