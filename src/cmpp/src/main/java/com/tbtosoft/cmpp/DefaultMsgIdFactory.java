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

/**
 * @author stephen
 *
 */
class DefaultMsgIdFactory implements IMsgIdFactory {

	@Override
	public byte[] createMsgId(MsgId msgId) {
		byte[] tmp = new byte[8];
		long result = 0;
		result |= (long)msgId.getMonth() << 60L;
		result |= (long)msgId.getDay() << 55L;
		result |= (long)msgId.getHour() << 50L;
		result |= (long)msgId.getMin() << 44L;
		result |= (long)msgId.getSec() << 38L;
		result |= (long)msgId.getGateCode() << 16L;
		result |= (long)msgId.getSequence() & 0xffL;
		ByteBuffer.wrap(tmp).putLong(result);		
		return tmp;
	}

	@Override
	public MsgId parseMsgId(byte[] msgId) {
		MsgId tmp = new MsgId();
		long result = ByteBuffer.wrap(msgId).getLong();
		tmp.setMonth((int)((result >>> 60) & 0xf));
		tmp.setDay((int)((result >>> 55) & 0x1f));
		tmp.setHour((int)((result >>> 50) & 0x1f));
		tmp.setMin((int)((result >>> 44) & 0x3f));
		tmp.setSec((int)((result >>> 38) & 0x3f));
		tmp.setGateCode((int)((result >>> 16) & 0x3fffff));
		tmp.setSequence((int)(result & 0xff));
		return tmp;
	}

}
