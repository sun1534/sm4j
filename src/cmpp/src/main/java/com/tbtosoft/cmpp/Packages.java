/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.cmpp;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.tbtosoft.cmpp.exception.CmppException;

/**
 * @author stephen
 *
 */
public final class Packages {
	public static IPackage parse(ByteBuffer buffer)throws CmppException{
		buffer.rewind();
		int command = buffer.getInt(4);
		IPackage pack = null;
		switch (command) {
		case Command.CONNECT_REQ:
			pack = new ConnectReqPkg();
			break;
		case Command.CONNECT_RSP:
			pack = new ConnectRspPkg();
			break;
		case Command.ACTIVE_TEST_REQ:
			pack = new ActiveTestReqPkg();
			break;
		case Command.ACTIVE_TEST_RSP:
			pack = new ActiveTestRspPkg();
			break;
		case Command.DELIVER_REQ:
			pack = new DeliverReqPkg();
			break;
		case Command.DELIVER_RSP:
			pack = new DeliverRspPkg();
			break;
		case Command.SUBMIT_REQ:
			pack = new SubmitReqPkg();
			break;
		case Command.SUBMIT_RSP:
			pack = new SubmitRspPkg();
			break;
		default:
			throw new CmppException(Arrays.toString(buffer.array())+" not support command id");			
		}
		pack.loadBuffer(buffer);
		return pack;
	}
	public static void setSequenceFactory(ISequenceFactory sequenceFactory){
		AbstractPackage.setSequenceFactory(sequenceFactory);
	}
}
