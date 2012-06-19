/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio.session;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chengchun
 *
 */
class Config implements IConfig {
	private Map<String, String> stringMap = new HashMap<String, String>();
	private Map<String, Integer> integerMap = new HashMap<String, Integer>();

	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.session.IConfig#getString(java.lang.String)
	 */
	@Override
	public String getString(String key) {
		return stringMap.get(key);
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.session.IConfig#putString(java.lang.String, java.lang.String)
	 */
	@Override
	public String putString(String key, String value) {
		return stringMap.put(key, value);
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.session.IConfig#getInteger(java.lang.String)
	 */
	@Override
	public Integer getInteger(String key) {
		return integerMap.get(key);
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.session.IConfig#putInteger(java.lang.String)
	 */
	@Override
	public Integer putInteger(String key, Integer value) {		
		return integerMap.put(key, value);
	}

}
