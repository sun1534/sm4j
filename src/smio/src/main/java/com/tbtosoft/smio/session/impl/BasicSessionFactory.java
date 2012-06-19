/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio.session.impl;

import com.tbtosoft.smio.session.CmppConfig;
import com.tbtosoft.smio.session.IConfig;
import com.tbtosoft.smio.session.ISession;
import com.tbtosoft.smio.session.ISessionFactory;
import com.tbtosoft.smio.session.SgipConfig;
import com.tbtosoft.smio.session.SmgpConfig;

/**
 * @author chengchun
 *
 */
abstract class BasicSessionFactory implements ISessionFactory{

	/* (non-Javadoc)
	 * @see com.tbtosoft.smio.session.ISessionFactory#create(com.tbtosoft.smio.session.IConfig)
	 */
	@Override
	public ISession create(IConfig config) {
		if(config instanceof CmppConfig){
			return createCmppSession(config);
		} else if(config instanceof SgipConfig){
			return createSgipSession(config);
		} else if(config instanceof SmgpConfig){
			return createSmgpSession(config);
		}
		return null;
	}
	protected abstract ISession createCmppSession(IConfig config);
	protected abstract ISession createSgipSession(IConfig config);
	protected abstract ISession createSmgpSession(IConfig config);
}
