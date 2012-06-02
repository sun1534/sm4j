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

import com.tbtosoft.sgip.exception.SgipException;

/**
 * @author stephen
 *
 */
public final class BindReqPkg extends AbstractPackage {
	private byte type;
	private String name;//16bytes;
	private String password;//16bytes;
	private byte[] reserve;
	

	public BindReqPkg() {
		super(Command.BIND_REQ);
		this.reserve = new byte[8];
	}
	public BindReqPkg(String password){
		this();
		this.password = password;
	}
	@Override
	protected int onToBuffer(ByteBuffer buffer) throws SgipException {
		int len = 0;
		buffer.put(this.type);
		len+=1;
		writeToBuffer(buffer, this.name, 16);
		len+=16;
		writeToBuffer(buffer, this.password, 16);
		len+=16;
		buffer.put(this.reserve);
		len+=8;
		return len;
	}

	@Override
	protected void onLoadBuffer(ByteBuffer buffer) {
		type = buffer.get();
		this.name = readFromBuffer(buffer, 16);
		this.password = readFromBuffer(buffer, 16);
		buffer.get(this.reserve);
	}
	/**
	 * @return the type
	 */
	public byte getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(byte type) {
		this.type = type;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
