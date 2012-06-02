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
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author stephen
 *
 */
public final class SubmitReqPkg extends AbstractPackage {
	/**
	 * SP的接入号码
	 */
	private String spNumber;//21bytes
	/**
	 * 付费号码，手机号码前加“86”国别标志；当且仅当群发且对用户收费时为空；
	 * 如果为空，则该条短消息产生的费用由UserNumber代表的用户支付；
	 * 如果为全零字符串“000000000000000000000”，表示该条短消息产生的费用由SP支付。
	 */
	private String chargeNumber;//21bytes
	/**
	 * 接收短消息的手机数量，取值范围1至100
	 */
	private byte userCount;
	/**
	 * 接收该短消息的手机号，该字段重复UserCount指定的次数，手机号码前加“86”国别标志
	 */
	private Collection<String> userNumbers;//21*n bytes
	/**
	 * 企业代码，取值范围0-99999
	 */
	private String corpId;//5bytes
	/**
	 * 业务代码，由SP定义
	 */
	private String serviceType;//10bytes
	/**
	 * 计费类型
	 */
	private byte feeType;
	/**
	 * 取值范围0-99999，该条短消息的收费值，单位为分，由SP定义
	 * 对于包月制收费的用户，该值为月租费的值
	 */
	private String feeValue;//6bytes
	/**
	 * 取值范围0-99999，赠送用户的话费，单位为分，由SP定义，特指由SP向用户发送广告时的赠送话费
	 */
	private String givenValue;//6bytes
	/**
	 * 代收费标志，0：应收；1：实收
	 */
	private byte agentFlag;
	/**
	 * 引起MT消息的原因
	 * 0-MO点播引起的第一条MT消息；
	 * 1-MO点播引起的非第一条MT消息；
	 * 2-非MO点播引起的MT消息；
	 * 3-系统反馈引起的MT消息。
	 */
	private byte morelatetoMTFlag;
	/**
	 * 优先级0-9从低到高，默认为0
	 */
	private byte priority;
	/**
	 * 短消息寿命的终止时间，如果为空，表示使用短消息中心的缺省值。
	 * 时间内容为16个字符，格式为”yymmddhhmmsstnnp” ，其中“tnnp”取固定值“032+”，即默认系统为北京时间
	 */
	private String expireTime;//16bytes
	/**
	 * 短消息定时发送的时间，如果为空，表示立刻发送该短消息。
	 * 时间内容为16个字符，格式为“yymmddhhmmsstnnp” ，其中“tnnp”取固定值“032+”，即默认系统为北京时间
	 */
	private String scheduleTime;//16bytes
	/**
	 * 状态报告标记
	 * 0-该条消息只有最后出错时要返回状态报告
	 * 1-该条消息无论最后是否成功都要返回状态报告
	 * 2-该条消息不需要返回状态报告
	 * 3-该条消息仅携带包月计费信息，不下发给用户，要返回状态报告
	 * 其它-保留
	 * 缺省设置为0
	 */
	private byte reportFlag;
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
	 * 信息类型：
	 * 0-短消息信息
	 * 其它：待定
	 */
	private byte messageType;
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
	public SubmitReqPkg() {
		super(Command.SUBMIT_REQ);
		this.userNumbers = new ArrayList<String>();
		this.reserve = new byte[8];
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.cmpp.AbstractPackage#onToBuffer(java.nio.ByteBuffer)
	 */
	@Override
	protected int onToBuffer(ByteBuffer buffer) {
		int len = 0;
		writeToBuffer(buffer, this.spNumber, 21);
		len+=21;
		writeToBuffer(buffer, this.chargeNumber, 21);
		len+=21;
		this.userCount = (byte)(this.userNumbers.size());
		buffer.put(this.userCount);
		len+=1;
		for(String mobile : userNumbers){
			writeToBuffer(buffer, mobile, 21);
			len+=21;
		}
		writeToBuffer(buffer, this.corpId, 5);
		len+=5;
		writeToBuffer(buffer, this.serviceType, 10);
		len+=10;
		buffer.put(this.feeType);
		len+=1;
		writeToBuffer(buffer, this.feeValue, 6);
		len+=6;
		writeToBuffer(buffer, this.givenValue, 6);
		len+=6;
		buffer.put(this.agentFlag);
		len+=1;
		buffer.put(this.morelatetoMTFlag);
		len+=1;
		buffer.put(this.priority);
		len+=1;
		writeToBuffer(buffer, this.expireTime, 16);
		len+=16;
		writeToBuffer(buffer, this.scheduleTime, 16);
		len+=16;
		buffer.put(this.reportFlag);
		len+=1;
		buffer.put(this.tppId);
		len+=1;
		buffer.put(this.tpUdhi);
		len+=1;
		buffer.put(this.messageCoding);
		len+=1;
		buffer.put(this.messageType);
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
		this.spNumber = readFromBuffer(buffer, 21);
		this.chargeNumber = readFromBuffer(buffer, 21);
		this.userCount = buffer.get();
		int count = this.userCount;
		while(--count>0){
			this.userNumbers.add(readFromBuffer(buffer, 21));
		}
		this.corpId = readFromBuffer(buffer, 5);
		this.serviceType = readFromBuffer(buffer, 10);
		this.feeType = buffer.get();
		this.feeValue = readFromBuffer(buffer, 6);
		this.givenValue = readFromBuffer(buffer, 6);
		this.agentFlag = buffer.get();
		this.morelatetoMTFlag = buffer.get();
		this.priority = buffer.get();
		this.expireTime = readFromBuffer(buffer, 16);
		this.scheduleTime = readFromBuffer(buffer, 16);
		this.reportFlag = buffer.get();
		this.tppId = buffer.get();
		this.tpUdhi = buffer.get();
		this.messageCoding = buffer.get();
		this.messageType = buffer.get();
		this.messageLength = buffer.getInt();
		this.messageContent = new byte[this.messageLength];
		buffer.get(this.messageContent);
		buffer.get(this.reserve);
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
	 * @return the chargeNumber
	 */
	public String getChargeNumber() {
		return chargeNumber;
	}

	/**
	 * @param chargeNumber the chargeNumber to set
	 */
	public void setChargeNumber(String chargeNumber) {
		this.chargeNumber = chargeNumber;
	}

	/**
	 * @return the userCount
	 */
	public byte getUserCount() {
		return userCount;
	}

	/**
	 * @param userCount the userCount to set
	 */
	public void setUserCount(byte userCount) {
		this.userCount = userCount;
	}

	/**
	 * @return the userNumbers
	 */
	public Collection<String> getUserNumbers() {
		return userNumbers;
	}

	/**
	 * @param userNumbers the userNumbers to set
	 */
	public void setUserNumbers(Collection<String> userNumbers) {
		this.userNumbers = userNumbers;
	}

	/**
	 * @return the corpId
	 */
	public String getCorpId() {
		return corpId;
	}

	/**
	 * @param corpId the corpId to set
	 */
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	/**
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}

	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * @return the feeType
	 */
	public byte getFeeType() {
		return feeType;
	}

	/**
	 * @param feeType the feeType to set
	 */
	public void setFeeType(byte feeType) {
		this.feeType = feeType;
	}

	/**
	 * @return the feeValue
	 */
	public String getFeeValue() {
		return feeValue;
	}

	/**
	 * @param feeValue the feeValue to set
	 */
	public void setFeeValue(String feeValue) {
		this.feeValue = feeValue;
	}

	/**
	 * @return the givenValue
	 */
	public String getGivenValue() {
		return givenValue;
	}

	/**
	 * @param givenValue the givenValue to set
	 */
	public void setGivenValue(String givenValue) {
		this.givenValue = givenValue;
	}

	/**
	 * @return the agentFlag
	 */
	public byte getAgentFlag() {
		return agentFlag;
	}

	/**
	 * @param agentFlag the agentFlag to set
	 */
	public void setAgentFlag(byte agentFlag) {
		this.agentFlag = agentFlag;
	}

	/**
	 * @return the morelatetoMTFlag
	 */
	public byte getMorelatetoMTFlag() {
		return morelatetoMTFlag;
	}

	/**
	 * @param morelatetoMTFlag the morelatetoMTFlag to set
	 */
	public void setMorelatetoMTFlag(byte morelatetoMTFlag) {
		this.morelatetoMTFlag = morelatetoMTFlag;
	}

	/**
	 * @return the priority
	 */
	public byte getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(byte priority) {
		this.priority = priority;
	}

	/**
	 * @return the expireTime
	 */
	public String getExpireTime() {
		return expireTime;
	}

	/**
	 * @param expireTime the expireTime to set
	 */
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	/**
	 * @return the scheduleTime
	 */
	public String getScheduleTime() {
		return scheduleTime;
	}

	/**
	 * @param scheduleTime the scheduleTime to set
	 */
	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

	/**
	 * @return the reportFlag
	 */
	public byte getReportFlag() {
		return reportFlag;
	}

	/**
	 * @param reportFlag the reportFlag to set
	 */
	public void setReportFlag(byte reportFlag) {
		this.reportFlag = reportFlag;
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
	 * @return the messageType
	 */
	public byte getMessageType() {
		return messageType;
	}

	/**
	 * @param messageType the messageType to set
	 */
	public void setMessageType(byte messageType) {
		this.messageType = messageType;
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
