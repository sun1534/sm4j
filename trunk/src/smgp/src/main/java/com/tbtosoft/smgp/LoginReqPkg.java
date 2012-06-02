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
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tbtosoft.smgp.exception.SmgpException;

/**
 * @author stephen
 *
 */
public final class LoginReqPkg extends AbstractPackage {
	private String clientId;//8bytes
	private String authenticatorClient;//16bytes
	private byte loginMode;
	private int timestamp;
	private byte clientVersion;
	
	public LoginReqPkg() {
		super(Command.LOGIN_REQ);		
	}
	
	@Override
	protected int onToBuffer(ByteBuffer buffer) throws SmgpException {
		this.timestamp = createTimestamp();		
		int len = 0;
		writeToBuffer(buffer, this.clientId, 8);
		len+=8;
		writeToBuffer(buffer, this.authenticatorClient, 16);
		len+=16;
		buffer.put(this.loginMode);
		len+=1;
		buffer.putInt(this.timestamp);
		len+=4;
		buffer.put(this.clientVersion);
		len+=1;
		return len;
	}

	@Override
	protected void onLoadBuffer(ByteBuffer buffer) {
		this.clientId = readFromBuffer(buffer, 8);
		this.authenticatorClient = readFromBuffer(buffer, 16);
		this.loginMode = buffer.get();
		this.timestamp = buffer.getInt();		
		this.clientVersion = buffer.get();
	}

	/**
	 * @return the timestamp
	 */
	public int getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}
		
	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return the authenticatorClient
	 */
	public String getAuthenticatorClient() {
		return authenticatorClient;
	}

	/**
	 * @param authenticatorClient the authenticatorClient to set
	 */
	public void setAuthenticatorClient(String authenticatorClient) {
		this.authenticatorClient = authenticatorClient;
	}

	/**
	 * @return the loginMode
	 */
	public byte getLoginMode() {
		return loginMode;
	}

	/**
	 * @param loginMode the loginMode to set
	 */
	public void setLoginMode(byte loginMode) {
		this.loginMode = loginMode;
	}

	/**
	 * @return the clientVersion
	 */
	public byte getClientVersion() {
		return clientVersion;
	}

	/**
	 * @param clientVersion the clientVersion to set
	 */
	public void setClientVersion(byte clientVersion) {
		this.clientVersion = clientVersion;
	}

	private Integer createTimestamp(){
		return Integer.parseInt(new SimpleDateFormat("MMddHHmmss").format(new Date()));
	}
}
