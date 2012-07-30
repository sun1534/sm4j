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
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.tbtosoft.sgip.exception.SgipException;

/**
 * @author stephen
 *
 */
abstract class AbstractPackage implements IPackage{
	protected final static Map<Integer, String> mapMsgFmt = new HashMap<Integer, String>();
	static{
		mapMsgFmt.put(0, "gb2312");
//		mapMsgFmt.put(3, "");
//		mapMsgFmt.put(4, "");
		mapMsgFmt.put(8, "UnicodeBigUnmarked");
		mapMsgFmt.put(15, "gb2312");
	}

	private int length;
	private final int commandId;
	private byte[] sequence;
	private static ISequenceFactory sequenceFactory = new DefaultSequenceFactory(301012345);
	private byte[] genNextGlobalSequenceNumber(){
		return sequenceFactory.next();
	}
	
	protected AbstractPackage(int cmd){
		commandId = cmd;
		this.sequence = genNextGlobalSequenceNumber();
	}
	
	@Override
	public int toBuffer(ByteBuffer buffer) throws SgipException {
		try{
			return toBufferImpl(buffer);
		}catch (Exception e) {
			throw new SgipException(Arrays.toString(buffer.array()), e);
		}
	}
	private int toBufferImpl(ByteBuffer buffer) throws SgipException{		
		this.length = 0;
		buffer.putInt(0);
		this.length += 4;
		buffer.putInt(this.commandId);
		this.length += 4;
		buffer.put(this.sequence);
		this.length += this.sequence.length;
		this.length += onToBuffer(buffer);
		buffer.putInt(0, this.length);
		return this.length;
	}
	protected abstract int onToBuffer(ByteBuffer buffer) throws SgipException;
	@Override
	public void loadBuffer(ByteBuffer buffer) throws SgipException {
		try{
			loadBufferImpl(buffer);
		}catch (Exception e) {
			throw new SgipException(Arrays.toString(buffer.array()), e);
		}
	}
	private void loadBufferImpl(ByteBuffer buffer){		
		this.length = buffer.getInt();
		buffer.position(buffer.position()+4);
		buffer.get(this.sequence);
		onLoadBuffer(buffer);
	}
	protected abstract void onLoadBuffer(ByteBuffer buffer);

	/**
	 * @return the sequence
	 */
	public byte[] getSequence() {
		return sequence;
	}

	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(byte[] sequence) {
		this.sequence = sequence;
	}

	/**
	 * @return the commandId
	 */
	public int getCommandId() {
		return commandId;
	}
	protected int writeToBuffer(ByteBuffer buffer, String octetString, int len, Charset charset){
		byte[] tmp = octetString.getBytes(charset);
		int size = tmp.length > len?len:tmp.length;
		buffer.put(tmp, 0, size);
		while(len > size){
			buffer.put((byte)0x00);
			size++;
		}
		return size;
	}
	protected int writeToBuffer(ByteBuffer buffer, String octetString, int len){
		return writeToBuffer(buffer, octetString, len, Charset.defaultCharset());
	}
	protected String readFromBuffer(ByteBuffer buffer, int len){
		return readFromBuffer(buffer, len, Charset.defaultCharset());
	}
	protected String readFromBuffer(ByteBuffer buffer, int len, Charset charset){		
		byte[] tmp = new byte[len];
		buffer.get(tmp);
		int pos = 0;
		for (byte b : tmp) {
			if(0x00 == b){
				break;
			}
			pos++;
		}
		return new String(tmp, 0, pos, charset);
	}
}
