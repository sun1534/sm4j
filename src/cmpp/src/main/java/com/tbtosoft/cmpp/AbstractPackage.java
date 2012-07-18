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
import java.util.HashMap;
import java.util.Map;

import com.tbtosoft.cmpp.exception.CmppException;

/**
 * @author stephen
 *
 */
abstract class AbstractPackage implements IPackage{
	protected final static Map<Integer, String> mapMsgFmt = new HashMap<Integer, String>();
	static{
		mapMsgFmt.put(0, "gb2312");
		mapMsgFmt.put(3, "gb2312");
		mapMsgFmt.put(4, "gb2312");
		mapMsgFmt.put(8, "UnicodeBigUnmarked");
		mapMsgFmt.put(15, "gb2312");
	}	
	private int length;
	private final int commandId;
	private int sequence;
	private static ISequenceFactory sequenceFactory = new DefaultSequenceFactory();
	private int genNextGlobalSequenceNumber(){
		return sequenceFactory.next();
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
		buffer.putInt(0, this.length);
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
	/**
	 * @return the sequenceFactory
	 */
	protected final static ISequenceFactory getSequenceFactory() {
		return AbstractPackage.sequenceFactory;
	}
	/**
	 * @param sequenceFactory the sequenceFactory to set
	 */
	protected final static void setSequenceFactory(ISequenceFactory sequenceFactory) {
		AbstractPackage.sequenceFactory = sequenceFactory;
	}
	public final static String byteToHexString(byte[] data){
        StringBuffer buffer = new StringBuffer();
        for(int index = 0; index < data.length; index++){
            buffer.append(String.format("%02X", data[index]));
        }        
        return buffer.toString();
    }
    public final static byte[] hexStringToByte(String hexString){
        byte[] result = new byte[hexString.length()/2];
        for(int index = 0; index < result.length; index++){
            String str = hexString.substring(index*2, (index+1)*2);
            result[index] = Byte.parseByte(str, 16);
        }
        return result;
    }
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AbstractPackage [length=" + length + ", commandId=" + commandId
				+ ", sequence=" + sequence + "]";
	}    
}
