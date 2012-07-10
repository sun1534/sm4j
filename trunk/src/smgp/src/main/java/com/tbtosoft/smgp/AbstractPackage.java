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
	protected int writeShort(ByteBuffer buffer, short value){
		buffer.putShort(value);
		return 2;
	}
	protected short readShort(ByteBuffer buffer){
		return buffer.getShort();		
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
	protected int writeTlv(ByteBuffer buffer, short tag, byte value){
		int size = 0;
		size+=writeShort(buffer, tag);
		size+=writeShort(buffer, (short)1);
		size+=write(buffer, value);		
		return size;		
	}	
//	class Tlvs {
//		class Tlv<T extends Object>{
//			public Short tag;
//			public Short len;
//			public T value;
//		}
//		public final static short TPPID = 0X0001;
//		public final static short TPUDHI  = 0X0002;
//		public final static short LINKID = 0X0003;
//		public final static short CHARGEUSERTYPE = 0X0004;
//		public final static short CHARGETERMTYPE = 0X0005;
//		public final static short CHARGETERMPSEUDO = 0X0006;
//		public final static short DESTTERMTYPE = 0X0007;
//		public final static short DESTTERMPSEUDO = 0X0008;
//		public final static short PKTOTAL = 0X0009;
//		public final static short PKNUMBER = 0X000A;
//		public final static short SUBMITMSGTYPE = 0X000B;
//		public final static short SPDEALRESLT = 0X000C;
//		public final static short SRCTERMTYPE = 0X000D;
//		public final static short SRCTERMPSEUDO = 0X000E;
//		public final static short NODESCOUNT = 0X000F;
//		public final static short MSGSRC = 0X0010;
//		public final static short SRCTYPE = 0X0011;
//		public final static short MSERVICEID = 0X0012;
//		private Map<Short, Tlv<Object>> tlvs = new HashMap<Short, Tlv<Object>>();
//		
//		@SuppressWarnings("unchecked")
//		public <T> T getValue(short tag){			
//			Tlv<Object> tlv = tlvs.get(tag);
//			if(null != tlv){
//				return (T)(tlv.value);
//			}
//			return null;
//		}
//		@SuppressWarnings("unchecked")
//		public <T> void setValue(short tag, T value){
//			Object obj = tlvs.get(tag);
//			
//		}
//		public int toBuffer(ByteBuffer buffer){
//			
//			return 0;
//		}
//		public void loadBuffer(ByteBuffer buffer){
//			while(buffer.remaining()>=4){
//				short tag = buffer.getShort();
//				short len = buffer.getShort();
//				switch(tag){
//					case TPPID:
//						
//						break;
//					default:
//						;
//				}
//			}
//		}
//		protected void addTlv(Short tag, Short len, byte[] buffer){
//			
//		}
//		protected void addTppid(Byte value){
//			this.addTlv(TPPID, (short)1, value);			
//		}
//		@SuppressWarnings("unchecked")
//		private <E extends Object> void addTlv(Short tag, Short len, E value){
//			Tlv<E> tlv = new Tlv<E>();
//			tlv.tag = tag;
//			tlv.len = len;
//			tlv.value = value;
//			tlvs.put(tag, (Tlv<Object>) tlv);
//		}
//	}
}
