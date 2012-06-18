/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.cmpp;

/**
 * @author stephen
 *
 */
public interface IMsgIdFactory {
	public byte[] createMsgId(MsgId msgId);
	public MsgId parseMsgId(byte[] msgId);
}
