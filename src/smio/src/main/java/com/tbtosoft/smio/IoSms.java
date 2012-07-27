/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio;

import org.jboss.netty.util.Timer;

import com.tbtosoft.smio.handlers.DefaultIoChannelHandler;
import com.tbtosoft.smio.handlers.DefaultSmsHandler;

/**
 * @author chengchun
 *
 */
class IoSms {
	private DefaultSmsHandler smsHandler;
	private DefaultIoChannelHandler ioChannelHandler;
	private Timer timer;
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
	 * @return the ioChannelHandler
	 */
	public final DefaultIoChannelHandler getIoChannelHandler() {
		return ioChannelHandler;
	}
	/**
	 * @param ioChannelHandler the ioChannelHandler to set
	 */
	public final void setIoChannelHandler(DefaultIoChannelHandler ioChannelHandler) {
		this.ioChannelHandler = ioChannelHandler;
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
	/**
	 * @return the timer
	 */
	public final Timer getTimer() {
		return timer;
	}
	/**
	 * @param timer the timer to set
	 */
	public final void setTimer(Timer timer) {
		this.timer = timer;
	}	
}
