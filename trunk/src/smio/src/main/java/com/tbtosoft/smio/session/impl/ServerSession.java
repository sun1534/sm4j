/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio.session.impl;

import java.net.SocketAddress;

/**
 * @author chengchun
 *
 */
class ServerSession extends BasicSession {
	
	public ServerSession(SocketAddress socketAddress){
		super(socketAddress);
	}
	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.session.impl.BasicSession#loop()
	 */
	@Override
	protected void loop() throws InterruptedException {
		
	}

}
