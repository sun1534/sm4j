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

import com.tbtosoft.smio.session.ISession;

/**
 * @author chengchun
 *
 */
abstract class BasicSession implements ISession {
	private String name;
	private Object attachment;
	private SocketAddress socketAddress;
	
	private Thread thread;
	private volatile boolean threadRunable;
	
	protected BasicSession(){
		
	}
	protected BasicSession(SocketAddress socketAddress){
		setSocketAddress(socketAddress);
	}
	
	/**
	 * @return the socketAddress
	 */
	public final SocketAddress getSocketAddress() {
		return socketAddress;
	}
	/**
	 * @param socketAddress the socketAddress to set
	 */
	public final void setSocketAddress(SocketAddress socketAddress) {
		this.socketAddress = socketAddress;
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
	
	
	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.session.ISession#open()
	 */
	@Override
	public boolean open() {
		createThreadProc();
		return true;
	}
	private void createThreadProc(){
		if(null != thread){
			closeThreadProc();
		}		
		threadRunable = true;
		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				threadProc();
			}
		});
		thread.setName("Session:" + this.name);
		thread.start();
	}
	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.session.ISession#close()
	 */
	@Override
	public void close() {
		closeThreadProc();
	}
	private void closeThreadProc(){
		threadRunable = false;
		if(null != thread){
			try {
				thread.join(1000);
			} catch (InterruptedException e) { 
				e.printStackTrace();
			}
			if(thread.isAlive()){
				thread.interrupt();
			}
			thread = null;
		}
	}
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
	 * @return the attachment
	 */
	public final Object getAttachment() {
		return attachment;
	}
	/**
	 * @param attachment the attachment to set
	 */
	public final void setAttachment(Object attachment) {
		this.attachment = attachment;
	}	
}
