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
public final class ConnectRspPkg extends AbstractPackage{
	private byte status;
	private String authenticatorISMG;
	private byte version;
	public ConnectRspPkg() {
		super(Command.CONNECT_RSP);		
	}

	@Override
	protected int onToBuffer(ByteBuffer buffer) {
		int len = 0;
		buffer.put(this.status);
		len+=1;
		writeToBuffer(buffer, this.authenticatorISMG, 16);
		len+=16;
		buffer.put(this.version);
		len+=1;
		return len;
	}

	@Override
	protected void onLoadBuffer(ByteBuffer buffer) {
		this.status = buffer.get();
		this.authenticatorISMG = readFromBuffer(buffer, 16);
		this.version = buffer.get();
	}

	/**
	 * @return the status
	 */
	public byte getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(byte status) {
		this.status = status;
	}

	/**
	 * @return the authenticatorISMG
	 */
	public String getAuthenticatorISMG() {
		return authenticatorISMG;
	}

	/**
	 * @param authenticatorISMG the authenticatorISMG to set
	 */
	public void setAuthenticatorISMG(String authenticatorISMG) {
		this.authenticatorISMG = authenticatorISMG;
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
}
