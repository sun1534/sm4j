/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smgp;

import java.nio.ByteBuffer;

import com.tbtosoft.smgp.exception.SmgpException;

/**
 * @author stephen
 *
 */
public interface IPackage {
	public int getCommandId();
	public int getSequence();	
	/**
	 * 
	 * @param buffer
	 * @return
	 * @throws SmgpException
	 */
	public int toBuffer(ByteBuffer buffer) throws SmgpException;
	
	/**
	 * 
	 * @param buffer
	 * @throws SmgpException
	 */
	public void loadBuffer(ByteBuffer buffer) throws SmgpException;

}
