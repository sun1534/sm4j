/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smsp;

/**
 * @author chengchun
 *
 */
public abstract class AbstractSP implements ISP {
	private ISPListener listener;

	/* (non-Javadoc)
	 * @see com.tbtosoft.smsp.ISP#setListener(com.tbtosoft.smsp.ISPListener)
	 */
	@Override
	public void setListener(ISPListener listener) {
		this.listener = listener;		
	}
	protected void deliver(Object obj){
		this.listener.deliver(obj);
	}
	protected void report(Object obj){
		this.listener.report(obj);
	}
}
