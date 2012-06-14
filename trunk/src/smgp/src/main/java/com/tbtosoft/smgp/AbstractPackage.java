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
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.tbtosoft.smgp.exception.SmgpException;

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
	public int toBuffer(ByteBuffer buffer) throws SmgpException {
		try{
			return toBufferImpl(buffer);
		}catch (Exception e) {
			throw new SmgpException(Arrays.toString(buffer.array()), e);
		}
	}
	private int toBufferImpl(ByteBuffer buffer) throws SmgpException{		
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
	protected abstract int onToBuffer(ByteBuffer buffer) throws SmgpException;
	@Override
	public void loadBuffer(ByteBuffer buffer) throws SmgpException {
		try{
			loadBufferImpl(buffer);
		}catch (Exception e) {
			throw new SmgpException(Arrays.toString(buffer.array()), e);
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
	protected int write(ByteBuffer buffer, byte value){
		buffer.put(value);
		return 1;
	}
	protected byte read(ByteBuffer buffer){
		return buffer.get();
	}
	protected int write(ByteBuffer buffer, byte[] values){
		buffer.put(values);
		return values.length;
	}
	protected void read(ByteBuffer buffer, byte[] values){
		buffer.get(values);		
	}
	protected int writeInt(ByteBuffer buffer, int value){
		buffer.putInt(value);
		return 4;
	}
	protected int readInt(ByteBuffer buffer){		
		return buffer.getInt();
	}
	
	protected int writeString(ByteBuffer buffer, String octetString, int len, Charset charset){
		byte[] tmp = null==octetString?new byte[0]:octetString.getBytes(charset);
		int size = tmp.length > len?len:tmp.length;
		buffer.put(tmp, 0, size);
		while(len > size){
			buffer.put((byte)0x00);
			size++;
		}
		return size;
	}
	protected int writeString(ByteBuffer buffer, String octetString, int len){
		return writeString(buffer, octetString, len, Charset.defaultCharset());
	}
	protected String readString(ByteBuffer buffer, int len){
		return readString(buffer, len, Charset.defaultCharset());
	}	
	protected String readString(ByteBuffer buffer, int len, Charset charset){		
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