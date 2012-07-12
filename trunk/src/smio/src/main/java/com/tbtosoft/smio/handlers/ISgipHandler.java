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

/**
 * @author chengchun
 *
 */
interface ISgipHandler {
	public void received(ChannelHandlerContext ctx, BindReqPkg bindReqPkg);
	public void received(ChannelHandlerContext ctx, BindRspPkg bindRspPkg);
}
