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

/**
 * @author stephen
 *
 */
public final class LoginRspPkg extends AbstractPackage{
	private int status;
	private String authenticatorServer;//16bytes
	private byte serverVersion;

	public LoginRspPkg() {
		super(Command.LOGIN_RSP);		
	}
	public LoginRspPkg(String authenticatorServer, byte serverVersion){
		this();		
		this.authenticatorServer = authenticatorServer;
		this.serverVersion = serverVersion;
	}

	@Override
	protected int onToBuffer(ByteBuffer buffer)  {		
		int len = 0;
		len+=writeInt(buffer, this.status);
		len+=writeString(buffer, this.authenticatorServer, 16);
		len+=write(buffer, this.serverVersion);
		return len;
	}

	@Override
	protected void onLoadBuffer(ByteBuffer buffer) {
		this.status = readInt(buffer);		
		this.authenticatorServer = readString(buffer, 16);
		this.serverVersion = read(buffer);
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the authenticatorServer
	 */
	public String getAuthenticatorServer() {
		return authenticatorServer;
	}
	/**
	 * @param authenticatorServer the authenticatorServer to set
	 */
	public void setAuthenticatorServer(String authenticatorServer) {
		this.authenticatorServer = authenticatorServer;
	}
	/**
	 * @return the serverVersion
	 */
	public byte getServerVersion() {
		return serverVersion;
	}
	/**
	 * @param serverVersion the serverVersion to set
	 */
	public void setServerVersion(byte serverVersion) {
		this.serverVersion = serverVersion;
	}	
}
