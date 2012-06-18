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
	/**
	 * 信息标识，生成算法如下：
	 * 采用64位（8字节）的整数：
	 * （1）	时间（格式为MMDDHHMMSS，即月日时分秒）：bit64~bit39，其中
	 * bit64~bit61：月份的二进制表示；
	 * bit60~bit56：日的二进制表示；
	 * bit55~bit51：小时的二进制表示；
	 * bit50~bit45：分的二进制表示；
	 * bit44~bit39：秒的二进制表示；
	 * （2）	短信网关代码：bit38~bit17，把短信网关的代码转换为整数填写到该字段中。
	 * （3）	序列号：bit16~bit1，顺序增加，步长为1，循环使用。
	 * 各部分如不能填满，左补零，右对齐。
	 * 
	 * （SP根据请求和应答消息的Sequence_Id一致性就可得到CMPP_Submit消息的Msg_Id）
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
	 * 8：流量控制错
	 * 9~ ：其他错误
	 */
	private byte result;
	public SubmitRspPkg() {
		this(new byte[8]);
	}
	public SubmitRspPkg(byte[] msgId){
		super(Command.SUBMIT_RSP);		
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
