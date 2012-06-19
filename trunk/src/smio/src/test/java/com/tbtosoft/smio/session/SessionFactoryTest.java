/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio.session;

import junit.framework.TestCase;

/**
 * @author chengchun
 *
 */
public class SessionFactoryTest extends TestCase {
	public void testCreateSession(){
		IConfig config = new CmppConfig();
		config.putString(CmppConfig.MT_MO_HOST_IP, "221.180.145.44");
		config.putInteger(CmppConfig.MT_MO_HOST_PORT, 4410);
		config.putString(CmppConfig.SOURCE_ADDR, "437845");
		config.putString(CmppConfig.SHARED_SECRET, "");
	}
}
