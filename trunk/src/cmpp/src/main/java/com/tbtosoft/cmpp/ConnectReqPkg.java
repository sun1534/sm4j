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
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tbtosoft.cmpp.exception.CmppException;

/**
 * @author stephen
 *
 */
public final class ConnectReqPkg extends AbstractPackage {
	private String sourceAddress;
	private byte[] authenticatorSource;
	private byte version;
	private Integer timestamp;
	private String password;
	public ConnectReqPkg() {
		super(Command.CONNECT_REQ);		
	}
	public ConnectReqPkg(String password){
		this();
		this.password = password;
	}
	@Override
	protected int onToBuffer(ByteBuffer buffer) throws CmppException {
		this.timestamp = createTimestamp();		
		this.authenticatorSource = createAuthenticatorSource(
				this.sourceAddress, this.password, this.timestamp);
		
		int len = 0;
		len+=writeString(buffer, this.sourceAddress, 6);		
		len+=write(buffer, this.authenticatorSource);		
		len+=write(buffer, this.version);		
		len+=writeInt(buffer, this.timestamp);		
		return len;
	}

	@Override
	protected void onLoadBuffer(ByteBuffer buffer) {
		this.sourceAddress = readString(buffer, 6);
		this.authenticatorSource = new byte[16];		
		read(buffer, this.authenticatorSource);
		this.version = read(buffer);
		this.timestamp = readInt(buffer);
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
	
	public byte[] createAuthenticatorSource(String sourceAddr, String password,
			Integer timestamp) throws CmppException {
		try {
			ByteBuffer buffer = ByteBuffer.allocate(256);
			writeString(buffer, sourceAddr, 6);
			buffer.position(buffer.position() + 9);
			writeString(buffer, password, password.length());
			writeString(buffer, timestamp.toString(), 10);
			java.security.MessageDigest md5 = java.security.MessageDigest
					.getInstance("MD5");
			md5.update(buffer.array());
			return md5.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new CmppException(
					"Create authenticator source failure(SourceAddr:"
							+ sourceAddr + " Password:"
							+ password + " Timestamp:" + timestamp, e);
		}
	}
	
	private Integer createTimestamp(){
		return Integer.parseInt(new SimpleDateFormat("MMddHHmmss").format(new Date()));
	}
}
