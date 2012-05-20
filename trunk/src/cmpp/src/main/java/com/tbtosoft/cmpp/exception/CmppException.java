/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.cmpp.exception;

/**
 * @author stephen
 *
 */
public class CmppException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7685035728519739359L;
	public CmppException(String msg){
		super(msg);
	}
	public CmppException(String msg, Throwable throwable){
		super(msg, throwable);
	}
	public CmppException(Throwable throwable){
		super(throwable);
	}
}
