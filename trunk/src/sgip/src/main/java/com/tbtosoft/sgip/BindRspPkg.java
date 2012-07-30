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

/**
 * @author stephen
 *
 */
public final class BindRspPkg extends AbstractPackage{
	/**
	 * Bind执行命令是否成功。
	 * 0：执行成功
	 * 其它：错误码
	 */
	private byte result;
	/**
	 * 保留，扩展用
	 */
	private byte[] reserve;	
	public BindRspPkg() {
		super(Command.BIND_RSP);
		this.reserve = new byte[8];
	}
	
	@Override
	protected int onToBuffer(ByteBuffer buffer)  {		
		int len = 0;		
		buffer.put(this.result);
		len+=1;
		buffer.put(this.reserve);
		len+=this.reserve.length;
		return len;
	}

	@Override
	protected void onLoadBuffer(ByteBuffer buffer) {		
		this.result = buffer.get();
		buffer.get(this.reserve);
	}
	
	/**
	 * @return the result
	 */
	public byte getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(byte result) {
		this.result = result;
	}
	/**
	 * @return the reserve
	 */
	public final byte[] getReserve() {
		return reserve;
	}
	/**
	 * @param reserve the reserve to set
	 */
	public final void setReserve(byte[] reserve) {
		this.reserve = reserve;
	}		
}
