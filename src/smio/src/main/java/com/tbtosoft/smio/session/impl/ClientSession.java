/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio.session.impl;


/**
 * @author chengchun
 *
 */
class ClientSession extends BasicSession {
	private ClientSessionFactory clientSessionFactory;
	
	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.session.impl.BasicSession#loop()
	 */
	@Override
	protected void loop() throws InterruptedException {
		
	}
	/**
	 * @return the clientSessionFactory
	 */
	public final ClientSessionFactory getClientSessionFactory() {
		return clientSessionFactory;
	}
	/**
	 * @param clientSessionFactory the clientSessionFactory to set
	 */
	public final void setClientSessionFactory(
			ClientSessionFactory clientSessionFactory) {
		this.clientSessionFactory = clientSessionFactory;
	}	
}
