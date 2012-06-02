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
public final class ReportReqPkg extends AbstractPackage {
	/**
	 * 该命令所涉及的Submit或deliver命令的序列号
	 */
	private byte[] submitSequenceNumber;//12bytes
	/**
	 * Report命令类型
	 * 0：对先前一条Submit命令的状态报告
	 * 1：对先前一条前转Deliver命令的状态报告
	 */
	private byte reportType;
	/**
	 * 接收短消息的手机号，手机号码前加“86”国别标志
	 */
	private String userNumber;//21bytes
	/**
	 * 该命令所涉及的短消息的当前执行状态
	 * 0：发送成功
	 * 1：等待发送
	 * 2：发送失败
	 */
	private byte state;
	/**
	 * 当State=2时为错误码值，否则为0
	 */
	private byte errorCode;
	/**
	 * 保留，扩展用
	 */
	private byte[] reserve;//8bytes
	protected ReportReqPkg() {
		super(Command.REPORT_REQ);
		this.submitSequenceNumber = new byte[12];
		this.reserve = new byte[8];
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.cmpp.AbstractPackage#onToBuffer(java.nio.ByteBuffer)
	 */
	@Override
	protected int onToBuffer(ByteBuffer buffer) {
		int len = 0;
		buffer.put(this.submitSequenceNumber);
		len+=this.submitSequenceNumber.length;
		buffer.put(this.reportType);
		len+=1;
		writeToBuffer(buffer, this.userNumber, 21);
		len+=21;
		buffer.put(this.state);
		len+=1;
		buffer.put(this.errorCode);
		len+=1;
		buffer.put(this.reserve);
		len+=this.reserve.length;
		return len;
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.cmpp.AbstractPackage#onLoadBuffer(java.nio.ByteBuffer)
	 */
	@Override
	protected void onLoadBuffer(ByteBuffer buffer) {
		buffer.get(this.submitSequenceNumber);
		this.reportType = buffer.get();
		this.userNumber = readFromBuffer(buffer, 21);
		this.state = buffer.get();
		this.errorCode = buffer.get();
		buffer.get(this.reserve);
	}

	/**
	 * @return the submitSequenceNumber
	 */
	public byte[] getSubmitSequenceNumber() {
		return submitSequenceNumber;
	}

	/**
	 * @param submitSequenceNumber the submitSequenceNumber to set
	 */
	public void setSubmitSequenceNumber(byte[] submitSequenceNumber) {
		this.submitSequenceNumber = submitSequenceNumber;
	}

	/**
	 * @return the reportType
	 */
	public byte getReportType() {
		return reportType;
	}

	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(byte reportType) {
		this.reportType = reportType;
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
	 * @return the state
	 */
	public byte getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(byte state) {
		this.state = state;
	}

	/**
	 * @return the errorCode
	 */
	public byte getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(byte errorCode) {
		this.errorCode = errorCode;
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
