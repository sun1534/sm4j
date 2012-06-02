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
public final class UnBindReqPkg extends AbstractPackage {

	public UnBindReqPkg() {
		super(Command.UNBIND_REQ);
		
	}
	public UnBindReqPkg(String password){
		this();
		
	}
	@Override
	protected int onToBuffer(ByteBuffer buffer) throws SgipException {
		int len = 0;		
		return len;
	}

	@Override
	protected void onLoadBuffer(ByteBuffer buffer) {

	}	
}
