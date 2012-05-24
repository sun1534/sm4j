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
import java.security.NoSuchAlgorithmException;

/**
 * @author stephen
 *
 */
public final class ConnectRspPkg extends AbstractPackage{
	private byte status;
	private byte[] authenticatorISMG;
	private byte version;
	private String password;
	private byte[] authenticatorSource;
	public ConnectRspPkg() {
		super(Command.CONNECT_RSP);		
	}
	public ConnectRspPkg(String password){
		this();
		this.password = password;
	}

	@Override
	protected int onToBuffer(ByteBuffer buffer)  {
		try {
			this.authenticatorISMG = createAuthenticatorISMG(this.status, this.authenticatorSource, this.password);
		} catch (NoSuchAlgorithmException e) {			
			e.printStackTrace();
		}
		int len = 0;
		buffer.put(this.status);
		len+=1;
		buffer.put(this.authenticatorISMG);
		len+=16;
		buffer.put(this.version);
		len+=1;
		return len;
	}

	@Override
	protected void onLoadBuffer(ByteBuffer buffer) {
		this.status = buffer.get();
		this.authenticatorISMG = new byte[16];
		buffer.get(this.authenticatorISMG);
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
	public byte[] getAuthenticatorISMG() {
		return authenticatorISMG;
	}

	/**
	 * @param authenticatorISMG the authenticatorISMG to set
	 */
	public void setAuthenticatorISMG(byte[] authenticatorISMG) {
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
	public byte[] createAuthenticatorISMG(byte status, byte[] authenticatorSource, String password) throws NoSuchAlgorithmException{
		ByteBuffer buffer = ByteBuffer.allocate(256);
		buffer.put(status);
		buffer.put(authenticatorSource);			
		writeToBuffer(buffer, password, password.length());
		java.security.MessageDigest md5 = java.security.MessageDigest.getInstance("MD5");
        md5.update(buffer.array());
        return md5.digest();		
	}
}
