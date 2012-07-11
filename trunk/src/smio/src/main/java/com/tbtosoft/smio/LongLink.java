/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio;

import java.net.SocketAddress;
import java.util.Timer;
import java.util.TimerTask;


/**
 * @author chengchun
 *
 */
public class LongLink extends Link{	
	private Timer timer;
	private boolean enableTimer = false;
	public LongLink(SocketAddress address){
		super(address);
	}
	public void startActiveTimer(int period){
		if(null != timer){
			timer.cancel();
			timer = null;
		}
		enableTimer = true;
		timer = new Timer("LONG-LINK-TIMER");	
		processActiveTest(period);
	}
	private void processActiveTest(final int delay){
		if(!enableTimer){
			return;
		}
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				doActiveTest();
				processActiveTest(delay);
			}
		},delay);
	}
	private void doActiveTest(){
		
	}
	public void stopActiveTimer(){
		enableTimer = false;
		if(null != timer){
			timer.cancel();
			timer = null;
		}
	}
	@Override
	protected void closeImpl() {
		stopActiveTimer();
	}
}
