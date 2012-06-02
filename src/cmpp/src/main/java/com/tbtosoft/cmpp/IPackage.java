/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.cmpp;

import java.nio.ByteBuffer;

import com.tbtosoft.cmpp.exception.CmppException;

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
	 * @throws CmppException
	 */
	public int toBuffer(ByteBuffer buffer) throws CmppException;
	
	/**
	 * 
	 * @param buffer
	 * @throws CmppException
	 */
	public void loadBuffer(ByteBuffer buffer) throws CmppException;

}
