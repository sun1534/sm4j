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
public interface ISPListener {
	public void deliver(Object obj);
	public void report(Object obj);
}
