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
import java.util.Arrays;
import java.util.Date;

import com.tbtosoft.cmpp.exception.CmppException;

/**
 * @author stephen
 *
 */
public final class ConnectReqPkg extends AbstractPackage {
	/**
	 * 源地址，此处为SP_Id，即SP的企业代码。
	 */
	private String sourceAddress;
	/**
	 * 用于鉴别源地址。其值通过单向MD5 hash计算得出，表示如下：
	 * AuthenticatorSource =
	 * MD5（Source_Addr+9 字节的0 +shared secret+timestamp）
	 * Shared secret 由中国移动与源地址实体事先商定，timestamp格式为：MMDDHHMMSS，即月日时分秒，10位。
	 */
	private byte[] authenticatorSource;
	/**
	 * 双方协商的版本号(高位4bit表示主版本号,低位4bit表示次版本号)
	 */
	private byte version;
	/**
	 * 时间戳的明文,由客户端产生,格式为MMDDHHMMSS，即月日时分秒，10位数字的整型，右对齐 。
	 */
	private Integer timestamp;
	/**
	 * shared secret
	 */
	private String password;
	public ConnectReqPkg() {
		super(Command.CONNECT_REQ);		
	}
	public ConnectReqPkg(String sourceAddr, String password){
		this();
		this.sourceAddress = sourceAddr;
		this.password = password;
	}
	@Override
	protected int onToBuffer(ByteBuffer buffer) throws CmppException {
		String strTimestamp = createTimestamp();
		this.timestamp = Integer.parseInt(strTimestamp);
		this.authenticatorSource = createAuthenticatorSource(
				this.sourceAddress, this.password, strTimestamp);
		
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
			String timestamp) throws CmppException {
		try {
			ByteBuffer buffer = ByteBuffer.allocate(256);
			int size = 0;
			size+=writeString(buffer, sourceAddr, sourceAddr.length());			
			buffer.position(buffer.position() + 9);
			size+=9;
			size+=writeString(buffer, password, password.length());
			size+=writeString(buffer, timestamp, 10);
			java.security.MessageDigest md5 = java.security.MessageDigest
					.getInstance("MD5");
			md5.update(buffer.array(), 0, size);
			return md5.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new CmppException(
					"Create authenticator source failure(SourceAddr:"
							+ sourceAddr + " Password:"
							+ password + " Timestamp:" + timestamp, e);
		}
	}
	
	private String createTimestamp(){
		return new SimpleDateFormat("MMddHHmmss").format(new Date());
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ConnectReqPkg [sourceAddress=" + sourceAddress
				+ ", authenticatorSource="
				+ Arrays.toString(authenticatorSource) + ", version=" + version
				+ ", timestamp=" + timestamp + ", password=" + password + "]";
	}	
}
