/**
 * Copyright(C) 2011-2012 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio.impl;

import com.tbtosoft.smio.ISmListener;
import com.tbtosoft.smio.IoSmService;

/**
 * @author chun.cheng
 *
 */
abstract class AbstractSmService<T> implements IoSmService<T> {
	
	private ISmListener<T> smListener;

	public void setSmListener(ISmListener<T> smListener) {
		this.smListener = smListener;
	}

	@Override
	public boolean write(T t) {
		smListener.onReceive(t, this);
		return false;
	}

	@Override
	public boolean start() {
		return onStart();
	}
	protected abstract boolean onStart();
	@Override
	public void stop() {
		onStop();
	}
	protected abstract void onStop();
}
