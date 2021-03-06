/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio.codec;

import java.nio.ByteBuffer;

import com.tbtosoft.cmpp.IPackage;
import com.tbtosoft.cmpp.Packages;
import com.tbtosoft.cmpp.exception.CmppException;
import com.tbtosoft.smio.ICoder;

/**
 * @author chengchun
 *
 */
public class CmppCoder implements ICoder {

	@Override
	public Object decode(ByteBuffer buffer) {		
		try {
			return Packages.parse(buffer);
		} catch (CmppException e) {				
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ByteBuffer encode(Object t) {		
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		try {	
			if(t instanceof IPackage){
				((IPackage)t).toBuffer(buffer);			
			}
		} catch (CmppException e) {				
			e.printStackTrace();
		}
		return buffer;
	}

	@Override
	public int getMinBytes() {		
		return 4;
	}

	@Override
	public int encode(Object t, ByteBuffer buffer) {
		try {		
			if(t instanceof IPackage){
				return ((IPackage)t).toBuffer(buffer);
			}
		} catch (CmppException e) {				
			e.printStackTrace();
		}
		return 0;
	}

}
