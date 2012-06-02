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

/**
 * @author stephen
 *
 */
public final class DeliverReqPkg extends AbstractPackage {
	/**
	 * 发送短消息的用户手机号，手机号码前加“86”国别标志
	 */
	private String userNumber;//21bytes
	/**
	 * SP的接入号码
	 */
	private String spNumber;//21bytes
	/**
	 * GSM协议类型。详细解释请参考GSM03.40中的9.2.3.9
	 */
	private byte tppId;
	/**
	 * GSM协议类型。详细解释请参考GSM03.40中的9.2.3.23,仅使用1位，右对齐
	 */
	private byte tpUdhi;
	/**
	 * 短消息的编码格式。
	 * 0：纯ASCII字符串
	 * 3：写卡操作
	 * 4：二进制编码
	 * 8：UCS2编码
	 * 15: GBK编码
	 * 其它参见GSM3.38第4节：SMS Data Coding Scheme
	 */
	private byte messageCoding;
	/**
	 * 短消息的长度
	 */
	private int messageLength;
	/**
	 * 短消息的内容
	 */
	private byte[] messageContent;
	/**
	 * 保留，扩展用
	 */
	private byte[] reserve;//8bytes
	
	protected DeliverReqPkg() {
		super(Command.DELIVER_REQ);	
		this.reserve = new byte[8];
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.cmpp.AbstractPackage#onToBuffer(java.nio.ByteBuffer)
	 */
	@Override
	protected int onToBuffer(ByteBuffer buffer) {
		int len = 0;
		writeToBuffer(buffer, this.userNumber, 21);
		len+=21;
		writeToBuffer(buffer, this.spNumber, 21);
		len+=21;
		buffer.put(this.tppId);
		len+=1;
		buffer.put(this.tpUdhi);
		len+=1;
		buffer.put(this.messageCoding);
		len+=1;		
		buffer.putInt(this.messageLength);
		len+=4;
		buffer.put(this.messageContent);
		len+=this.messageContent.length;
		buffer.put(this.reserve);
		len+=this.reserve.length;
		return len;
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.cmpp.AbstractPackage#onLoadBuffer(java.nio.ByteBuffer)
	 */
	@Override
	protected void onLoadBuffer(ByteBuffer buffer) {
		this.userNumber = readFromBuffer(buffer, 21);
		this.spNumber = readFromBuffer(buffer, 21);
		this.tppId = buffer.get();
		this.tpUdhi = buffer.get();
		this.messageCoding = buffer.get();
		this.messageLength = buffer.getInt();
		this.messageContent = new byte[this.messageLength];
		buffer.get(this.messageContent);
		buffer.get(this.reserve);
	}

	/**
	 * @return the userNumber
	 */
	public String getUserNumber() {
		return userNumber;
	}

	/**
	 * @param userNumber the userNumber to set
	 */
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	/**
	 * @return the spNumber
	 */
	public String getSpNumber() {
		return spNumber;
	}

	/**
	 * @param spNumber the spNumber to set
	 */
	public void setSpNumber(String spNumber) {
		this.spNumber = spNumber;
	}

	/**
	 * @return the tppId
	 */
	public byte getTppId() {
		return tppId;
	}

	/**
	 * @param tppId the tppId to set
	 */
	public void setTppId(byte tppId) {
		this.tppId = tppId;
	}

	/**
	 * @return the tpUdhi
	 */
	public byte getTpUdhi() {
		return tpUdhi;
	}

	/**
	 * @param tpUdhi the tpUdhi to set
	 */
	public void setTpUdhi(byte tpUdhi) {
		this.tpUdhi = tpUdhi;
	}

	/**
	 * @return the messageCoding
	 */
	public byte getMessageCoding() {
		return messageCoding;
	}

	/**
	 * @param messageCoding the messageCoding to set
	 */
	public void setMessageCoding(byte messageCoding) {
		this.messageCoding = messageCoding;
	}

	/**
	 * @return the messageLength
	 */
	public int getMessageLength() {
		return messageLength;
	}

	/**
	 * @param messageLength the messageLength to set
	 */
	public void setMessageLength(int messageLength) {
		this.messageLength = messageLength;
	}

	/**
	 * @return the messageContent
	 */
	public byte[] getMessageContent() {
		return messageContent;
	}

	/**
	 * @param messageContent the messageContent to set
	 */
	public void setMessageContent(byte[] messageContent) {
		this.messageContent = messageContent;
	}

	/**
	 * @return the reserve
	 */
	public byte[] getReserve() {
		return reserve;
	}

	/**
	 * @param reserve the reserve to set
	 */
	public void setReserve(byte[] reserve) {
		this.reserve = reserve;
	}	
	
}
