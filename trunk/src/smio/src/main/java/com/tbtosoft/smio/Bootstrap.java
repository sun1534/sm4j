/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio;

import com.tbtosoft.smio.session.ISessionFactory;

/**
 * @author chengchun
 *
 */
class Bootstrap {
	private volatile ISessionFactory sessionFactory;
	private volatile SmioPipelineFactory smioPipelineFactory;
	protected Bootstrap(){
		
	}
	protected Bootstrap(ISessionFactory sessionFactory){
		setSessionFactory(sessionFactory);
	}
	/**
	 * @return the sessionFactory
	 */
	public final ISessionFactory getSessionFactory() {
		ISessionFactory factory = this.sessionFactory;
		if(null == factory){
			throw new IllegalStateException(
                    "factory is not set yet.");
		}
		return factory;
	}

	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public final void setSessionFactory(ISessionFactory sessionFactory) {
		if (sessionFactory == null) {
            throw new NullPointerException("sessionFactory");
        }
        if (this.sessionFactory != null) {
            throw new IllegalStateException(
                    "factory can't change once set.");
        }
		this.sessionFactory = sessionFactory;
	}
	/**
	 * @return the smioPipelineFactory
	 */
	public final SmioPipelineFactory getSmioPipelineFactory() {
		return smioPipelineFactory;
	}
	/**
	 * @param smioPipelineFactory the smioPipelineFactory to set
	 */
	public final void setSmioPipelineFactory(SmioPipelineFactory smioPipelineFactory) {
		this.smioPipelineFactory = smioPipelineFactory;
	}	
}
