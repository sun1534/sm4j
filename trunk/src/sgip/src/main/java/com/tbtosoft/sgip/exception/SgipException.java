/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.sgip.exception;

/**
 * @author stephen
 *
 */
public class SgipException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3429819586539638572L;
	public SgipException(String msg){
		super(msg);
	}
	public SgipException(String msg, Throwable throwable){
		super(msg, throwable);
	}
	public SgipException(Throwable throwable){
		super(throwable);
	}
}
