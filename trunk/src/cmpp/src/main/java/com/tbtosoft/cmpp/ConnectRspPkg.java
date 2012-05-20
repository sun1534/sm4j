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

/**
 * @author stephen
 *
 */
public final class ConnectRspPkg extends AbstractPackage{

	public ConnectRspPkg() {
		super(Command.CONNECT_RSP);		
	}

	@Override
	protected int onToBuffer(ByteBuffer buffer) {
		return 0;
	}

	@Override
	protected void onLoadBuffer(ByteBuffer buffer) {
		
	}

}
