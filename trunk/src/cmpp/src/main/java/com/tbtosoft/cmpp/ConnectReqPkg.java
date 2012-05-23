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
public final class ConnectReqPkg extends AbstractPackage {
	private String sourceAddress;
	private String authenticatorSource;
	private byte version;
	private Integer timestamp;
	public ConnectReqPkg() {
		super(Command.CONNECT_REQ);		
	}

	@Override
	protected int onToBuffer(ByteBuffer buffer) {
		int len = 0;
		writeToBuffer(buffer, this.sourceAddress, 6);
		len+=6;
		writeToBuffer(buffer, this.authenticatorSource, 16);
		len+=16;
		buffer.put(this.version);
		len+=1;
		buffer.putInt(this.timestamp);
		len+=4;
		return len;
	}

	@Override
	protected void onLoadBuffer(ByteBuffer buffer) {
		this.sourceAddress = readFromBuffer(buffer, 6);
		this.authenticatorSource = readFromBuffer(buffer, 16);
		this.version = buffer.get();
		this.timestamp = buffer.getInt();
	}

	/**
	 * @return the sourceAddress
	 */
	public String getSourceAddress() {
		return sourceAddress;
	}

	/**
	 * @param sourceAddress the sourceAddress to set
	 */
	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}

	/**
	 * @return the authenticatorSource
	 */
	public String getAuthenticatorSource() {
		return authenticatorSource;
	}

	/**
	 * @param authenticatorSource the authenticatorSource to set
	 */
	public void setAuthenticatorSource(String authenticatorSource) {
		this.authenticatorSource = authenticatorSource;
	}

	/**
	 * @return the version
	 */
	public byte getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(byte version) {
		this.version = version;
	}

	/**
	 * @return the timestamp
	 */
	public Integer getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Integer timestamp) {
		this.timestamp = timestamp;
	}
	
}
