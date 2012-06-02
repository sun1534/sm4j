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
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tbtosoft.smgp.exception.SmgpException;

/**
 * @author stephen
 *
 */
public final class LoginReqPkg extends AbstractPackage {
	private String sourceAddress;
	private byte[] authenticatorSource;
	private byte version;
	private Integer timestamp;
	private String password;
	public LoginReqPkg() {
		super(Command.LOGIN_REQ);		
	}
	public LoginReqPkg(String password){
		this();
		this.password = password;
	}
	@Override
	protected int onToBuffer(ByteBuffer buffer) throws SmgpException {
		this.timestamp = createTimestamp();
		try {
			this.authenticatorSource = createAuthenticatorSource(
					this.sourceAddress, this.password, this.timestamp);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new SmgpException(
					"Create authenticator source failure(SourceAddr:"
							+ this.authenticatorSource + " Password:"
							+ this.password + " Timestamp:" + this.timestamp, e);
		}
		int len = 0;
		writeToBuffer(buffer, this.sourceAddress, 6);
		len+=6;
		buffer.put(this.authenticatorSource);
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
		this.authenticatorSource = new byte[16];
		buffer.get(this.authenticatorSource);
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
	public byte[] getAuthenticatorSource() {
		return authenticatorSource;
	}

	/**
	 * @param authenticatorSource the authenticatorSource to set
	 */
	public void setAuthenticatorSource(byte[] authenticatorSource) {
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
	
	public byte[] createAuthenticatorSource(String sourceAddr, String password, Integer timestamp) throws NoSuchAlgorithmException{
		ByteBuffer buffer = ByteBuffer.allocate(256);
		writeToBuffer(buffer, sourceAddr, 6);
		buffer.position(buffer.position()+9);
		writeToBuffer(buffer, password, password.length());
		writeToBuffer(buffer, timestamp.toString(), 10);
		java.security.MessageDigest md5 = java.security.MessageDigest.getInstance("MD5");
        md5.update(buffer.array());
        return md5.digest();		
	}
	
	private Integer createTimestamp(){
		return Integer.parseInt(new SimpleDateFormat("MMddHHmmss").format(new Date()));
	}
}
