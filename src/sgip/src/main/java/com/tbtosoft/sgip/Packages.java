/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.sgip;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.tbtosoft.sgip.exception.SgipException;

/**
 * @author stephen
 *
 */
public final class Packages {
	public static IPackage parse(ByteBuffer buffer)throws SgipException{
		buffer.rewind();
		int command = buffer.getInt(4);
		IPackage pack = null;
		switch (command) {
		case Command.BIND_REQ:
			pack = new BindReqPkg();
			break;
		case Command.BIND_RSP:
			pack = new BindRspPkg();
			break;
		case Command.SUBMIT_REQ:
			pack = new SubmitReqPkg();
			break;
		case Command.SUBMIT_RSP:
			pack = new SubmitRspPkg();
			break;
		case Command.DELIVER_REQ:
			pack = new DeliverReqPkg();
			break;
		case Command.DELIVER_RSP:
			pack = new DeliverRspPkg();
			break;
		case Command.REPORT_REQ:
			pack = new ReportReqPkg();
			break;
		case Command.REPORT_RSP:
			pack = new ReportRspPkg();
			break;
		case Command.UNBIND_REQ:
			pack = new UnBindReqPkg();
			break;
		case Command.UNBIND_RSP:
			pack = new UnBindRspPkg();
			break;
		default:
			throw new SgipException(Arrays.toString(buffer.array())+" not support command id");			
		}
		pack.loadBuffer(buffer);
		return pack;
	}
}
