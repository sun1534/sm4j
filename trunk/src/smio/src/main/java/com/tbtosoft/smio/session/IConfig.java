/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio.session;


/**
 * @author chengchun
 *
 */
public interface IConfig {
	public String getString(String key);
	public String putString(String key, String value);
	public Integer getInteger(String key);
	public Integer putInteger(String key, Integer value);
}
