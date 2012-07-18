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

import com.tbtosoft.cmpp.exception.CmppException;

/**
 * @author chengchun
 *
 */
public class TerminateReqPkg extends AbstractPackage {

	public TerminateReqPkg() {
		super(Command.TERMINATE_REQ);		
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.cmpp.AbstractPackage#onToBuffer(java.nio.ByteBuffer)
	 */
	@Override
	protected int onToBuffer(ByteBuffer buffer) throws CmppException {
		
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.cmpp.AbstractPackage#onLoadBuffer(java.nio.ByteBuffer)
	 */
	@Override
	protected void onLoadBuffer(ByteBuffer buffer) {
		
	}
}
