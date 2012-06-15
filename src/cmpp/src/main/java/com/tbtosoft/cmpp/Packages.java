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
import java.util.Calendar;
import java.util.Date;

import com.tbtosoft.cmpp.exception.CmppException;

/**
 * @author stephen
 *
 */
public final class Packages {
	public static IPackage parse(ByteBuffer buffer)throws CmppException{
		buffer.rewind();
		int command = buffer.getInt(4);
		switch (command) {
		case Command.CONNECT_REQ:
			
			break;

		default:
			break;
		}
		return null;
	}

	public final static byte[] createMsgId(int gateCode, short sequence){			
		byte[] msgId = new byte[8];
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE);
		int sec = calendar.get(Calendar.SECOND);
		//4+5+5+6+6     22     16
		//   26
		//月日时分秒    网关代码   序列号
		long result = 0;
		result |= (long)month << 60L;
		result |= (long)day << 55L;
		result |= (long)hour << 50L;
		result |= (long)min << 44L;
		result |= (long)sec << 38L;
		result |= (long)gateCode << 16L;
		result |= (long)sequence & 0xffL;
		ByteBuffer buf = ByteBuffer.wrap(msgId);  
		buf.putLong(result);
		return msgId;
	}
	public final static void parseMsgId(byte[] msgId, Integer sequence){
//		long result = ByteBuffer.wrap(msgId).getLong();
//		int month     = (int)((result >>> 60) & 0xf);
//		int day       = (int)((result >>> 55) & 0x1f);
//		int hour      = (int)((result >>> 50) & 0x1f);
//		int minute    = (int)((result >>> 44) & 0x3f);
//		int second    = (int)((result >>> 38) & 0x3f);
//		int gateway   = (int)((result >>> 16) & 0x3fffff);
//		sequence  = (int)(result & 0xff);
	}
}
