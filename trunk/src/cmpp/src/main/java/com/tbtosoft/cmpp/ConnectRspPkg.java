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

import com.tbtosoft.cmpp.exception.CmppException;

/**
 * @author stephen
 *
 */
public final class ConnectRspPkg extends AbstractPackage{
	/**
	 * 状态
	 * 0：正确
	 * 1：消息结构错
 	 * 2：非法源地址
 	 * 3：认证错
 	 * 4：版本太高
  	 * 5~ ：其他错误
	 */
	private byte status;
	/**
	 * ISMG认证码，用于鉴别ISMG。
	 * 其值通过单向MD5 hash计算得出，表示如下：
	 * AuthenticatorISMG =MD5（Status+AuthenticatorSource+shared secret），Shared secret 由中国移动与源地址实体事先商定，AuthenticatorSource为源地址实体发送给ISMG的对应消息CMPP_Connect中的值。
 	 * 认证出错时，此项为空。
	 */
	private byte[] authenticatorISMG;
	/**
	 * 服务器支持的最高版本号
	 */
	private byte version;
	private String password;
	private byte[] authenticatorSource;
	public ConnectRspPkg() {
		super(Command.CONNECT_RSP);		
	}
	public ConnectRspPkg(String password, byte[] authenticatoreSource){
		this();
		this.password = password;
		this.authenticatorSource = authenticatoreSource;
	}

	@Override
	protected int onToBuffer(ByteBuffer buffer) throws CmppException  {		
		this.authenticatorISMG = createAuthenticatorISMG(this.status, this.authenticatorSource, this.password);		
		int len = 0;
		len+=write(buffer, this.status);		
		len+=write(buffer, this.authenticatorISMG);
		len+=write(buffer, this.version);
		return len;
	}

	@Override
	protected void onLoadBuffer(ByteBuffer buffer) {
		this.status = read(buffer);
		this.authenticatorISMG = new byte[16];
		read(buffer, this.authenticatorISMG);
		this.version = read(buffer);
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
	public byte[] createAuthenticatorISMG(byte status, byte[] authenticatorSource, String password) throws CmppException{
		try {
			ByteBuffer buffer = ByteBuffer.allocate(256);
			write(buffer, status);
			write(buffer, authenticatorSource);
			writeString(buffer, password, password.length());
			java.security.MessageDigest md5 = java.security.MessageDigest.getInstance("MD5");
			md5.update(buffer.array());
			return md5.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new CmppException(
					"Create authenticator ISMG failure(Status:"
							+ status + " AuthenticatorSource:"
							+ authenticatorSource + " Password:" + this.password, e);
		}
	}
}
