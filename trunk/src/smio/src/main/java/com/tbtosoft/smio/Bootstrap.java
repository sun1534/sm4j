/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio;

/**
 * @author chengchun
 *
 */
public class Bootstrap {

	private volatile SmioPipelineFactory smioPipelineFactory;
	protected Bootstrap(){
		
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
