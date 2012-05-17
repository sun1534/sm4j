/**
 * Copyright(C) 2011-2012 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio.impl;

import java.util.Collection;

import com.tbtosoft.smio.ISmListener;
import com.tbtosoft.smio.IoSmService;

/**
 * @author chun.cheng
 *
 */
class AbstractSmService implements IoSmService {
	
	private ISmListener smListener;
	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.IoSmHandler#submit(java.util.Collection, java.lang.String)
	 */
	@Override
	public boolean submit(Collection<String> mobiles, String content) {
		// TODO Auto-generated method stub
		return false;
	}
	public void setSmListener(ISmListener smListener) {
		this.smListener = smListener;
	}
	enum SmsStat{
		SS_NONE,
		SS_WAIT_FOR_SUBMIT_RESPONSE,
		SS_WARI_FOR_DELIVER_REPORT
	}
	class SmsItem{
		public String content;
		public Collection<String> mobiles;
		public SmsStat smsStat = SmsStat.SS_NONE;
		public byte[] msgid;
	}
}
