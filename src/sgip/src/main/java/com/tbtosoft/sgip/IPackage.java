/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.sgip;

import java.nio.ByteBuffer;

import com.tbtosoft.sgip.exception.SgipException;

/**
 * @author stephen
 *
 */
public interface IPackage {
	public int getCommandId();
	public byte[] getSequence();	
	/**
	 * 
	 * @param buffer
	 * @return
	 * @throws SgipException
	 */
	public int toBuffer(ByteBuffer buffer) throws SgipException;
	
	/**
	 * 
	 * @param buffer
	 * @throws SgipException
	 */
	public void loadBuffer(ByteBuffer buffer) throws SgipException;

}
