/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio.session.impl;

import com.tbtosoft.smio.session.IConfig;
import com.tbtosoft.smio.session.ISession;

/**
 * @author chengchun
 *
 */
abstract class BasicSession implements ISession {
	private String name;
	
	private IConfig config;
	
	private Thread thread;
	private boolean threadRunable;
	
	protected BasicSession(){
		threadRunable = true;
		thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				threadProc();			
			}
		});
		thread.setName("Session:"+this.name);
		thread.start();
	}
	private final void threadProc(){
//		long lastTimestamp = System.currentTimeMillis();
		while(threadRunable){
			try {
				loop();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	protected abstract void loop() throws InterruptedException;
	/**
	 * @return the name
	 */
	protected final String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	protected final void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the config
	 */
	public final IConfig getConfig() {
		return config;
	}

	/**
	 * @param config the config to set
	 */
	public final void setConfig(IConfig config) {
		this.config = config;
	}
	
}
