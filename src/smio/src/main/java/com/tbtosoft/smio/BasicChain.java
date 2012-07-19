/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio;


/**
 * @author chengchun
 *
 */
public class BasicChain implements IChain{	
	private ISmsHandlerFactory smsHandlerFactory;
	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.IChain#open()
	 */
	@Override
	public boolean open() {
		
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.IChain#close()
	 */
	@Override
	public void close() {
	
	}

	/**
	 * @param smsHandlerFactory the smsHandlerFactory to set
	 */
	public final void setSmsHandlerFactory(ISmsHandlerFactory smsHandlerFactory) {
		this.smsHandlerFactory = smsHandlerFactory;
	}
	/**
	 * @return the smsHandlerFactory
	 */
	protected final ISmsHandlerFactory getSmsHandlerFactory() {
		return smsHandlerFactory;
	}	
}
