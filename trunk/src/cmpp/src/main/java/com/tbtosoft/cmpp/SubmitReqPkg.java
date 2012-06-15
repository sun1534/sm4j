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

/**
 * @author stephen
 *
 */
public final class SubmitReqPkg extends AbstractPackage {
	private byte[] msgId;
	private byte pkTotal;
	private byte pkNumber;
	private byte registeredDelivery;
	private byte msgLevel;
	private String serviceId;//10bytes
	private byte feeUserType;
	private String feeTerminalId;//21bytes
	private byte tppId;
	private byte tpUdhi;
	private byte msgFmt;
	private String msgSrc;//6bytes
	private String feeType;//2bytes
	private String feeCode;//6bytes
	private String vaildTime;//17bytes
	private String atTime;//17bytes
	private String srcId;//21bytes
	private byte destUsrTl;
	private String destTerminalId;//21*destUsrTl bytes	
	private byte msgLength;
	private String msgContent;
	private byte[] reserve;//8bytes
	public SubmitReqPkg() {
		super(Command.SUBMIT_REQ);
		this.msgId = new byte[8];
		this.reserve = new byte[8];
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
		len+=write(buffer, this.destUsrTl);
		len+=writeString(buffer, this.destTerminalId, this.destTerminalId.length());
		len+=this.destTerminalId.length();		
		byte[] tmpMsg = this.msgContent.getBytes(Charset.forName(mapMsgFmt.get(this.msgFmt)));
		len+=write(buffer, (byte)tmpMsg.length);
		len+=write(buffer, tmpMsg);
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
		this.destUsrTl = read(buffer);
		this.destTerminalId = readString(buffer, this.destUsrTl*21);
		this.msgLength = read(buffer);
		this.msgContent = readString(buffer, this.msgLength, Charset.forName(mapMsgFmt.get(this.msgFmt)));
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
		return destUsrTl;
	}

	/**
	 * @param destUsrTl the destUsrTl to set
	 */
	public void setDestUsrTl(byte destUsrTl) {
		this.destUsrTl = destUsrTl;
	}

	/**
	 * @return the destTerminalId
	 */
	public String getDestTerminalId() {
		return destTerminalId;
	}

	/**
	 * @param destTerminalId the destTerminalId to set
	 */
	public void setDestTerminalId(String destTerminalId) {
		this.destTerminalId = destTerminalId;
	}

	/**
	 * @return the msgLength
	 */
	public byte getMsgLength() {
		return msgLength;
	}

	/**
	 * @param msgLength the msgLength to set
	 */
	public void setMsgLength(byte msgLength) {
		this.msgLength = msgLength;
	}

	/**
	 * @return the msgContent
	 */
	public String getMsgContent() {
		return msgContent;
	}

	/**
	 * @param msgContent the msgContent to set
	 */
	public void setMsgContent(String msgContent) {
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
