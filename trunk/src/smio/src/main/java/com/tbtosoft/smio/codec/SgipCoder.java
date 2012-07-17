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

import com.tbtosoft.sgip.IPackage;
import com.tbtosoft.sgip.Packages;
import com.tbtosoft.sgip.exception.SgipException;
import com.tbtosoft.smio.ICoder;

/**
 * @author chengchun
 *
 */
public class SgipCoder implements ICoder<IPackage> {

	@Override
	public IPackage decode(ByteBuffer buffer) {		
		try {
			return Packages.parse(buffer);
		} catch (SgipException e) {				
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ByteBuffer encode(IPackage t) {		
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		try {
			t.toBuffer(buffer);
		} catch (SgipException e) {				
			e.printStackTrace();
		}
		return buffer;
	}

	@Override
	public int getMinBytes() {
		return 4;
	}

}
