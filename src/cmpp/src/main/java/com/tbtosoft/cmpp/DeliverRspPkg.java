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
public final class DeliverRspPkg extends AbstractPackage {
	/**
	 * 信息标识（CMPP_DELIVER中的Msg_Id字段）
	 */
	private byte[] msgId;
	/**
	 * 结果
	 * 0：正确
	 * 1：消息结构错
 	 * 2：命令字错
 	 * 3：消息序号重复
	 * 4：消息长度错
	 * 5：资费代码错
	 * 6：超过最大信息长
	 * 7：业务代码错
	 * 8: 流量控制错
	 * 9~ ：其他错误
	 */
	private byte result;
	public DeliverRspPkg() {
		this(new byte[8]);
	}
	public DeliverRspPkg(byte[] msgId){
		super(Command.DELIVER_RSP);	
		this.msgId = msgId;
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.cmpp.AbstractPackage#onToBuffer(java.nio.ByteBuffer)
	 */
	@Override
	protected int onToBuffer(ByteBuffer buffer) {		
		int len = 0;
		len+=write(buffer, this.msgId);
		len+=write(buffer, this.result);
		return len;
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.cmpp.AbstractPackage#onLoadBuffer(java.nio.ByteBuffer)
	 */
	@Override
	protected void onLoadBuffer(ByteBuffer buffer) {
		read(buffer, this.msgId);
		this.result = read(buffer);
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
