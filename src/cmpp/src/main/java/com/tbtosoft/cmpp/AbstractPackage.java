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
import java.nio.charset.Charset;
import java.util.Arrays;

import com.tbtosoft.cmpp.exception.CmppException;

/**
 * @author stephen
 *
 */
abstract class AbstractPackage implements IPackage{
	private static int globalSequenceNumber = 0;
	private int length;
	private final int commandId;
	private int sequence;
	private static synchronized int genNextGlobalSequenceNumber(){
		return globalSequenceNumber++;
	}
	protected AbstractPackage(int cmd){
		commandId = cmd;
	}
	
	@Override
	public int toBuffer(ByteBuffer buffer) throws CmppException {
		try{
			return toBufferImpl(buffer);
		}catch (Exception e) {
			throw new CmppException(Arrays.toString(buffer.array()), e);
		}
	}
	private int toBufferImpl(ByteBuffer buffer) throws CmppException{		
		this.sequence = genNextGlobalSequenceNumber();
		this.length = 0;
		buffer.putInt(0);
		this.length += 4;
		buffer.putInt(commandId);
		this.length += 4;
		buffer.putInt(sequence);
		this.length += 4;
		this.length += onToBuffer(buffer);
		return this.length;
	}
	protected abstract int onToBuffer(ByteBuffer buffer) throws CmppException;
	@Override
	public void loadBuffer(ByteBuffer buffer) throws CmppException {
		try{
			loadBufferImpl(buffer);
		}catch (Exception e) {
			throw new CmppException(Arrays.toString(buffer.array()), e);
		}
	}
	private void loadBufferImpl(ByteBuffer buffer){		
		this.length = buffer.getInt();
		buffer.position(buffer.position()+4);
		this.sequence = buffer.getInt();
		onLoadBuffer(buffer);
	}
	protected abstract void onLoadBuffer(ByteBuffer buffer);
	/**
	 * @return the sequence
	 */
	public int getSequence() {
		return sequence;
	}
	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	/**
	 * @return the commandId
	 */
	public int getCommandId() {
		return commandId;
	}
	protected void writeToBuffer(ByteBuffer buffer, String octetString, int len, Charset charset){
		byte[] tmp = octetString.getBytes(charset);
		int size = tmp.length > len?len:tmp.length;
		buffer.put(tmp, 0, size);
		while(len > size){
			buffer.put((byte)0x00);
			size++;
		}
	}
	protected void writeToBuffer(ByteBuffer buffer, String octetString, int len){
		writeToBuffer(buffer, octetString, len, Charset.defaultCharset());
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
