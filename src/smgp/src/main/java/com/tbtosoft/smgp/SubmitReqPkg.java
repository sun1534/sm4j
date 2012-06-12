/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smgp;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * @author stephen
 *
 */
public final class SubmitReqPkg extends AbstractPackage {
	private byte msgType;
	private byte needReport;
	private byte priority;
	private String serviceID;//10bytes
	private String feeType;//2bytes
	private String feeCode;//6bytes
	private String fixedFee;//6bytes
	private byte msgFormat;
	private String vaildTime;//17bytes
	private String atTime;//17bytes
	private String srcTermID;//21bytes;
	private String chargeTermID;//21bytes;
	private byte destTermIDCount;
	private String destTermID;//21*destTermIDCount bytes
	private byte msgLength;
	private String msgContent;
	private byte[] reserve;//8bytes
	
	public SubmitReqPkg() {
		super(Command.SUBMIT_REQ);		
		this.reserve = new byte[8];
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.cmpp.AbstractPackage#onToBuffer(java.nio.ByteBuffer)
	 */
	@Override
	protected int onToBuffer(ByteBuffer buffer) {
		int len = 0;
		len+=write(buffer, this.msgType);
		len+=write(buffer, this.needReport);
		len+=write(buffer, this.priority);
		len+=writeString(buffer, this.serviceID, 10);
		len+=writeString(buffer, this.feeType, 2);
		len+=writeString(buffer, this.feeCode, 6);
		len+=writeString(buffer, this.fixedFee, 6);
		len+=write(buffer, this.msgFormat);
		len+=writeString(buffer, this.vaildTime, 17);
		len+=writeString(buffer, this.atTime, 17);
		len+=writeString(buffer, this.srcTermID, 2);
		len+=writeString(buffer, this.chargeTermID, 12);
		len+=write(buffer, this.destTermIDCount);
		len+=writeString(buffer, this.destTermID, 21*this.destTermIDCount);
		len+=write(buffer, this.msgLength);		
		len+=write(buffer, this.msgContent.getBytes(Charset.forName(mapMsgFmt.get(this.msgFormat))));		
		len+=write(buffer, this.reserve);		
		return len;
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.cmpp.AbstractPackage#onLoadBuffer(java.nio.ByteBuffer)
	 */
	@Override
	protected void onLoadBuffer(ByteBuffer buffer) {
		this.msgType = read(buffer);
		this.needReport = read(buffer);
		this.priority = read(buffer);
		this.serviceID = readString(buffer, 10);
		this.feeType = readString(buffer, 2);
		this.feeCode = readString(buffer, 6);
		this.fixedFee = readString(buffer, 6);
		this.msgFormat = read(buffer);
		this.vaildTime = readString(buffer, 17);
		this.atTime = readString(buffer, 17);
		this.srcTermID = readString(buffer, 2);
		this.chargeTermID = readString(buffer, 12);
		this.destTermIDCount = read(buffer);
		this.destTermID = readString(buffer, 21*this.destTermIDCount);
		this.msgLength = read(buffer);		
		this.msgContent = readString(buffer, this.msgLength, Charset.forName(mapMsgFmt.get(this.msgFormat)));
		buffer.get(this.reserve);
	}

	/**
	 * @return the msgType
	 */
	public byte getMsgType() {
		return msgType;
	}

	/**
	 * @param msgType the msgType to set
	 */
	public void setMsgType(byte msgType) {
		this.msgType = msgType;
	}

	/**
	 * @return the needReport
	 */
	public byte getNeedReport() {
		return needReport;
	}

	/**
	 * @param needReport the needReport to set
	 */
	public void setNeedReport(byte needReport) {
		this.needReport = needReport;
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
	 * @return the serviceID
	 */
	public String getServiceID() {
		return serviceID;
	}

	/**
	 * @param serviceID the serviceID to set
	 */
	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
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
	 * @return the fixedFee
	 */
	public String getFixedFee() {
		return fixedFee;
	}

	/**
	 * @param fixedFee the fixedFee to set
	 */
	public void setFixedFee(String fixedFee) {
		this.fixedFee = fixedFee;
	}

	/**
	 * @return the msgFormat
	 */
	public byte getMsgFormat() {
		return msgFormat;
	}

	/**
	 * @param msgFormat the msgFormat to set
	 */
	public void setMsgFormat(byte msgFormat) {
		this.msgFormat = msgFormat;
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
	 * @return the srcTermID
	 */
	public String getSrcTermID() {
		return srcTermID;
	}

	/**
	 * @param srcTermID the srcTermID to set
	 */
	public void setSrcTermID(String srcTermID) {
		this.srcTermID = srcTermID;
	}

	/**
	 * @return the chargeTermID
	 */
	public String getChargeTermID() {
		return chargeTermID;
	}

	/**
	 * @param chargeTermID the chargeTermID to set
	 */
	public void setChargeTermID(String chargeTermID) {
		this.chargeTermID = chargeTermID;
	}

	/**
	 * @return the destTermIDCount
	 */
	public byte getDestTermIDCount() {
		return destTermIDCount;
	}

	/**
	 * @param destTermIDCount the destTermIDCount to set
	 */
	public void setDestTermIDCount(byte destTermIDCount) {
		this.destTermIDCount = destTermIDCount;
	}

	/**
	 * @return the destTermID
	 */
	public String getDestTermID() {
		return destTermID;
	}

	/**
	 * @param destTermID the destTermID to set
	 */
	public void setDestTermID(String destTermID) {
		this.destTermID = destTermID;
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
