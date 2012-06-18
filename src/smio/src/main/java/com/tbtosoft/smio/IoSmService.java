/**
 * Copyright(C) 2011-2012 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio;


/**
 * @author chun.cheng
 *
 */
public interface IoSmService<T> {
	public boolean start();
	public boolean write(T t);
	public void stop();
}