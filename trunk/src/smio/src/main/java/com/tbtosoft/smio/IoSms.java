/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio;

import com.tbtosoft.smio.handlers.DefaultSmsHandler;

/**
 * @author chengchun
 *
 */
class IoSms {
	private DefaultSmsHandler smsHandler;	
	private long activeTimeMillis;
	
	/**
	 * @return the smsHandler
	 */
	public final DefaultSmsHandler getSmsHandler() {
		return smsHandler;
	}
	/**
	 * @param smsHandler the smsHandler to set
	 */
	public final void setSmsHandler(DefaultSmsHandler smsHandler) {
		this.smsHandler = smsHandler;
	}
	/**
	 * @return the activeTimeMillis
	 */
	public final long getActiveTimeMillis() {
		return activeTimeMillis;
	}
	/**
	 * @param activeTimeMillis the activeTimeMillis to set
	 */
	public final void setActiveTimeMillis(long activeTimeMillis) {
		this.activeTimeMillis = activeTimeMillis;
	}	
}
