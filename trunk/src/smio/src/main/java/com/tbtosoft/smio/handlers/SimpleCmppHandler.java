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
import com.tbtosoft.cmpp.TerminateReqPkg;
import com.tbtosoft.cmpp.TerminateRspPkg;
import com.tbtosoft.smio.ISmsHandler;
import com.tbtosoft.smio.SmsIoHandler;

/**
 * @author chengchun
 *
 */
public class SimpleCmppHandler extends SmsIoHandler<IPackage> implements ICmppHandler{
	private ICmppHandler cmppHandler;
	public SimpleCmppHandler(){
		
	}
	public SimpleCmppHandler(ICmppHandler cmppHandler, ISmsHandler smsHandler){
		super(smsHandler);
		this.cmppHandler = cmppHandler;		
	}
	@Override
	public void received(ChannelHandlerContext ctx, ConnectReqPkg connectReqPkg) {
		if(null != this.cmppHandler){
			this.cmppHandler.received(ctx, connectReqPkg);
		}
	}

	@Override
	public void received(ChannelHandlerContext ctx, ConnectRspPkg connectRspPkg) {
		if(null != this.cmppHandler){
			this.cmppHandler.received(ctx, connectRspPkg);
		}
	}

	@Override
	public void received(ChannelHandlerContext ctx,	TerminateReqPkg terminateReqPkg) {
		if(null != this.cmppHandler){
			this.cmppHandler.received(ctx, terminateReqPkg);
		}
	}

	@Override
	public void received(ChannelHandlerContext ctx,	TerminateRspPkg terminateRspPkg) {
		if(null != this.cmppHandler){
			this.cmppHandler.received(ctx, terminateRspPkg);
		}
	}
	@Override
	public void received(ChannelHandlerContext ctx,	ActiveTestReqPkg activeTestReqPkg) {
		if(null != this.cmppHandler){
			this.cmppHandler.received(ctx, activeTestReqPkg);
		}
	}

	@Override
	public void received(ChannelHandlerContext ctx,	ActiveTestRspPkg activeTestRspPkg) {
		if(null != this.cmppHandler){
			this.cmppHandler.received(ctx, activeTestRspPkg);
		}
	}

	@Override
	public void received(ChannelHandlerContext ctx, SubmitReqPkg submitReqPkg) {
		if(null != this.cmppHandler){
			this.cmppHandler.received(ctx, submitReqPkg);
		}
	}

	@Override
	public void received(ChannelHandlerContext ctx, SubmitRspPkg submitRspPkg) {
		if(null != this.cmppHandler){
			this.cmppHandler.received(ctx, submitRspPkg);
		}
	}

	@Override
	public void received(ChannelHandlerContext ctx, DeliverReqPkg deliverReqPkg) {
		if(null != this.cmppHandler){
			this.cmppHandler.received(ctx, deliverReqPkg);
		}
	}

	@Override
	public void received(ChannelHandlerContext ctx, DeliverRspPkg deliverRspPkg) {
		if(null != this.cmppHandler){
			this.cmppHandler.received(ctx, deliverRspPkg);
		}
	}

	@Override
	protected void receiveImpl(ChannelHandlerContext ctx, IPackage t) {
		if(t instanceof ConnectReqPkg){
			received(ctx, (ConnectReqPkg)t);
		} else if(t instanceof ConnectRspPkg){
			received(ctx, (ConnectRspPkg)t);
		} else if(t instanceof TerminateReqPkg){
			received(ctx, (TerminateReqPkg)t);
		} else if(t instanceof TerminateRspPkg){
			received(ctx, (TerminateRspPkg)t);
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
