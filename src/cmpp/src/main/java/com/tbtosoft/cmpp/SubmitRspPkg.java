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
public final class SubmitRspPkg extends AbstractPackage {
	private byte[] msgId;
	private byte result;
	public SubmitRspPkg() {
		super(Command.SUBMIT_RSP);		
		this.msgId = new byte[8];
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.cmpp.AbstractPackage#onToBuffer(java.nio.ByteBuffer)
	 */
	@Override
	protected int onToBuffer(ByteBuffer buffer) {
		int len = 0;
		buffer.put(this.msgId);
		len+=this.msgId.length;
		buffer.put(this.result);
		len+=1;
		return len;
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.cmpp.AbstractPackage#onLoadBuffer(java.nio.ByteBuffer)
	 */
	@Override
	protected void onLoadBuffer(ByteBuffer buffer) {
		buffer.get(this.msgId);
		this.result = buffer.get();
	}

	/**
	 * @return the msgId
	 */
	public byte[] getMsgId() {
		return msgId;
	}

	/**
	 * @param msgId the msgId to set
	 */
	public void setMsgId(byte[] msgId) {
		this.msgId = msgId;
	}

	/**
	 * @return the result
	 */
	public byte getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(byte result) {
		this.result = result;
	}

}
