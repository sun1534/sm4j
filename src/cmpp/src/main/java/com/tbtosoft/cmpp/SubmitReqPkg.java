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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.tbtosoft.cmpp.exception.CmppException;

/**
 * @author stephen
 *
 */
public final class SubmitReqPkg extends AbstractPackage {	
	/**
	 * 信息标识，由SP侧短信网关本身产生，本处填空。
	 */
	private byte[] msgId;
	/**
	 * 相同Msg_Id的信息总条数，从1开始
	 */
	private byte pkTotal;
	/**
	 * 相同Msg_Id的信息序号，从1开始
	 */
	private byte pkNumber;
	/**
	 * 是否要求返回状态确认报告：
	 * 0：不需要
	 * 1：需要
	 * 2：产生SMC话单
 	 * （该类型短信仅供网关计费使用，不发送给目的终端)
	 */
	private byte registeredDelivery;
	/**
	 * 信息级别
	 */
	private byte msgLevel;
	/**
	 * 业务类型，是数字、字母和符号的组合。
	 */
	private String serviceId;//10bytes
	/**
	 * 计费用户类型字段
	 * 0：对目的终端MSISDN计费；
	 * 1：对源终端MSISDN计费；
	 * 2：对SP计费;
	 * 3：表示本字段无效，对谁计费参见Fee_terminal_Id字段。
	 */
	private byte feeUserType;
	/**
	 * 被计费用户的号码（如本字节填空，则表示本字段无效，对谁计费参见Fee_UserType字段，本字段与Fee_UserType字段互斥）
	 */
	private String feeTerminalId;//21bytes
	/**
	 * GSM协议类型。详细是解释请参考GSM03.40中的9.2.3.9
	 */
	private byte tppId;
	/**
	 * GSM协议类型。详细是解释请参考GSM03.40中的9.2.3.23,仅使用1位，右对齐
	 */
	private byte tpUdhi;
	/**
	 * 信息格式
	 * 0：ASCII串
	 * 3：短信写卡操作
	 * 4：二进制信息
	 * 8：UCS2编码
	 * 15：含GB汉字  。。。。。。
	 */
	private byte msgFmt;
	/**
	 * 信息内容来源(SP_Id)
	 */
	private String msgSrc;//6bytes
	/**
	 * 资费类别
	 * 01：对“计费用户号码”免费
	 * 02：对“计费用户号码”按条计信息费
	 * 03：对“计费用户号码”按包月收取信息费
	 * 04：对“计费用户号码”的信息费封顶
	 * 05：对“计费用户号码”的收费是由SP实现
	 */
	private String feeType;//2bytes
	/**
	 * 资费代码（以分为单位）
	 */
	private String feeCode;//6bytes
	/**
	 * 存活有效期，格式遵循SMPP3.3协议
	 */
	private String vaildTime;//17bytes
	/**
	 * 定时发送时间，格式遵循SMPP3.3协议
	 */
	private String atTime;//17bytes
	/**
	 * 源号码
	 * SP的服务代码或前缀为服务代码的长号码, 网关将该号码完整的填到SMPP协议Submit_SM消息相应的source_addr字段，
	 * 该号码最终在用户手机上显示为短消息的主叫号码
	 */
	private String srcId;//21bytes
	/**
	 * 接收短信的MSISDN号码
	 * 接收信息的用户数量(小于100个用户)
	 */
	private Collection<String> destTerminalId;
	/**
	 * 信息长度(Msg_Fmt值为0时：<160个字节；其它<=140个字节)
	 * 信息内容
	 */
	private byte[] msgContent;	
	/**
	 * 保留
	 */
	private byte[] reserve;//8bytes
	public SubmitReqPkg() {
		super(Command.SUBMIT_REQ);
		this.msgId = new byte[8];
		this.reserve = new byte[8];
		this.destTerminalId = new ArrayList<String>();
	}
	public SubmitReqPkg(byte msgFmt, String content){
		this();
		this.msgFmt = msgFmt;
		msgContent = content.getBytes(Charset.forName(mapMsgFmt.get(this.msgFmt)));
	}
	/* (non-Javadoc)
	 * @see com.tbtosoft.cmpp.AbstractPackage#onToBuffer(java.nio.ByteBuffer)
	 */
	@Override
	protected int onToBuffer(ByteBuffer buffer) {
		int len = 0;
		len+=write(buffer, this.msgId);
		len+=write(buffer, this.pkTotal);
		len+=write(buffer, this.pkNumber);
		len+=write(buffer, this.registeredDelivery);
		len+=write(buffer, this.msgLevel);
		len+=writeString(buffer, this.serviceId, 10);
		len+=write(buffer, this.feeUserType);
		len+=writeString(buffer, this.feeTerminalId, 21);
		len+=write(buffer, this.tppId);
		len+=write(buffer, this.tpUdhi);
		len+=write(buffer, this.msgFmt);
		len+=writeString(buffer, this.msgSrc, 6);
		len+=writeString(buffer, this.feeType, 2);
		len+=writeString(buffer, this.feeCode, 6);
		len+=writeString(buffer, this.vaildTime, 17);
		len+=writeString(buffer, this.atTime, 17);
		len+=writeString(buffer, this.srcId, 21);
		len+=write(buffer, (byte)this.destTerminalId.size());
		for(String mobile:this.destTerminalId){
			len+=writeString(buffer, mobile, 21);
		}			
		
		len+=write(buffer, (byte)this.msgContent.length);
		len+=write(buffer, this.msgContent);
		len+=write(buffer, this.reserve);
		return len;
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.cmpp.AbstractPackage#onLoadBuffer(java.nio.ByteBuffer)
	 */
	@Override
	protected void onLoadBuffer(ByteBuffer buffer) {
		buffer.get(this.msgId);
		this.pkTotal = read(buffer);
		this.pkNumber = read(buffer);
		this.registeredDelivery = read(buffer);
		this.msgLevel = read(buffer);
		this.serviceId = readString(buffer, 10);
		this.feeUserType = read(buffer);
		this.feeTerminalId = readString(buffer, 21);
		this.tppId = read(buffer);
		this.tpUdhi = read(buffer);
		this.msgFmt = read(buffer);
		this.msgSrc = readString(buffer, 6);
		this.feeType = readString(buffer, 2);
		this.feeCode = readString(buffer, 6);
		this.vaildTime = readString(buffer, 17);
		this.atTime = readString(buffer, 17);
		this.srcId = readString(buffer, 21);
		byte destUsrTl = read(buffer);
		for(int index = 0; index<destUsrTl;index++){			
			this.destTerminalId.add(readString(buffer, 21));
		}
		byte msgLength = read(buffer);
		this.msgContent = new byte[msgLength];
		read(buffer, this.msgContent);
		read(buffer, this.reserve);
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
	 * @return the pkTotal
	 */
	public byte getPkTotal() {
		return pkTotal;
	}

	/**
	 * @param pkTotal the pkTotal to set
	 */
	public void setPkTotal(byte pkTotal) {
		this.pkTotal = pkTotal;
	}

	/**
	 * @return the pkNumber
	 */
	public byte getPkNumber() {
		return pkNumber;
	}

	/**
	 * @param pkNumber the pkNumber to set
	 */
	public void setPkNumber(byte pkNumber) {
		this.pkNumber = pkNumber;
	}

	/**
	 * @return the registeredDelivery
	 */
	public byte getRegisteredDelivery() {
		return registeredDelivery;
	}

	/**
	 * @param registeredDelivery the registeredDelivery to set
	 */
	public void setRegisteredDelivery(byte registeredDelivery) {
		this.registeredDelivery = registeredDelivery;
	}

	/**
	 * @return the msgLevel
	 */
	public byte getMsgLevel() {
		return msgLevel;
	}

	/**
	 * @param msgLevel the msgLevel to set
	 */
	public void setMsgLevel(byte msgLevel) {
		this.msgLevel = msgLevel;
	}

	/**
	 * @return the serviceId
	 */
	public String getServiceId() {
		return serviceId;
	}

	/**
	 * @param serviceId the serviceId to set
	 */
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * @return the feeUserType
	 */
	public byte getFeeUserType() {
		return feeUserType;
	}

	/**
	 * @param feeUserType the feeUserType to set
	 */
	public void setFeeUserType(byte feeUserType) {
		this.feeUserType = feeUserType;
	}

	/**
	 * @return the feeTerminalId
	 */
	public String getFeeTerminalId() {
		return feeTerminalId;
	}

	/**
	 * @param feeTerminalId the feeTerminalId to set
	 */
	public void setFeeTerminalId(String feeTerminalId) {
		this.feeTerminalId = feeTerminalId;
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
	 * @return the msgFmt
	 */
	public byte getMsgFmt() {
		return msgFmt;
	}

	/**
	 * @param msgFmt the msgFmt to set
	 */
	public void setMsgFmt(byte msgFmt) {
		this.msgFmt = msgFmt;
	}

	/**
	 * @return the msgSrc
	 */
	public String getMsgSrc() {
		return msgSrc;
	}

	/**
	 * @param msgSrc the msgSrc to set
	 */
	public void setMsgSrc(String msgSrc) {
		this.msgSrc = msgSrc;
	}

	/**
	 * @return the feeType
	 */
	public String getFeeType() {
		return feeType;
	}

	/**
	 * @param feeType the feeType to set
	 */
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	/**
	 * @return the feeCode
	 */
	public String getFeeCode() {
		return feeCode;
	}

	/**
	 * @param feeCode the feeCode to set
	 */
	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}

	/**
	 * @return the vaildTime
	 */
	public String getVaildTime() {
		return vaildTime;
	}

	/**
	 * @param vaildTime the vaildTime to set
	 */
	public void setVaildTime(String vaildTime) {
		this.vaildTime = vaildTime;
	}

	/**
	 * @return the atTime
	 */
	public String getAtTime() {
		return atTime;
	}

	/**
	 * @param atTime the atTime to set
	 */
	public void setAtTime(String atTime) {
		this.atTime = atTime;
	}

	/**
	 * @return the srcId
	 */
	public String getSrcId() {
		return srcId;
	}

	/**
	 * @param srcId the srcId to set
	 */
	public void setSrcId(String srcId) {
		this.srcId = srcId;
	}

	/**
	 * @return the destUsrTl
	 */
	public byte getDestUsrTl() {
		return (byte)this.destTerminalId.size();
	}


	/**
	 * @return the destTerminalId
	 */
	public Collection<String> getDestTerminalId() {
		return destTerminalId;
	}

	/**
	 * @param destTerminalId the destTerminalId to set
	 */
	public void setDestTerminalId(Collection<String> destTerminalId) {
		this.destTerminalId = destTerminalId;
	}

	

	/**
	 * @return the msgContent
	 */
	public byte[] getMsgContent() {
		return msgContent;
	}
	public String getMsgContentString() throws CmppException{
		try{
		return new String(this.msgContent, 0, this.msgContent.length, 
				Charset.forName(mapMsgFmt.get(this.msgFmt)));
		}catch (Exception e) {
			throw new CmppException("msgContent hex:"+Arrays.toString(this.msgContent)+
					" msgFmt:"+this.msgFmt, e);
		}
	}
	/**
	 * @param msgContent the msgContent to set
	 */
	public void setMsgContent(byte[] msgContent) {
		this.msgContent = msgContent;
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
