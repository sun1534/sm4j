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

/**
 * @author stephen
 *
 */
public final class DeliverReqPkg extends AbstractPackage {
	private byte[] msgId;//8bytes
	private String destId;//21bytes
	private String serviceId;//10bytes
	private byte tppId;
	private byte tpUdhi;
	private byte msgFmt;
	private String srcTerminalId;//21bytes
	private byte registeredDelivery;
	private byte msgLength;
	private byte[] msgContent;
	private byte[] reserve;//8bytes
	protected DeliverReqPkg() {
		super(Command.DELIVER_REQ);	
		this.msgId = new byte[8];
		this.reserve = new byte[8];
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.cmpp.AbstractPackage#onToBuffer(java.nio.ByteBuffer)
	 */
	@Override
	protected int onToBuffer(ByteBuffer buffer) {
		int len = 0;
		buffer.put(this.msgId);
		len+=this.msgId.length;
		writeToBuffer(buffer, this.destId, 21);
		len+=21;
		writeToBuffer(buffer, this.serviceId, 10);
		len+=10;
		buffer.put(this.tppId);
		len+=1;
		buffer.put(this.tpUdhi);
		len+=1;
		buffer.put(this.msgFmt);
		len+=1;
		writeToBuffer(buffer, this.srcTerminalId, 21);
		len+=21;
		buffer.put(this.registeredDelivery);
		len+=1;
		buffer.put(this.msgLength);
		len+=1;
		buffer.put(this.msgContent);
		len+=this.msgContent.length;
		buffer.put(this.reserve);
		len+=this.reserve.length;
		return len;
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.cmpp.AbstractPackage#onLoadBuffer(java.nio.ByteBuffer)
	 */
	@Override
	protected void onLoadBuffer(ByteBuffer buffer) {
		buffer.get(this.msgId);
		this.destId = readFromBuffer(buffer, 21);
		this.serviceId = readFromBuffer(buffer, 10);
		this.tppId = buffer.get();
		this.tpUdhi = buffer.get();
		this.msgFmt = buffer.get();
		this.srcTerminalId = readFromBuffer(buffer, 21);
		this.registeredDelivery = buffer.get();
		this.msgLength = buffer.get();
		this.msgContent = new byte[this.msgLength];
		buffer.get(this.msgContent);
		buffer.get(this.reserve);
	}
	
	public class StatusReport{
		private byte[] msgId;//8bytes
		private String stat;//7bytes;
		private String submitTime;//10bytes
		private String doneTime;//10byte;
		private int smscSequnce;//4bytes
		public StatusReport(){
			msgId = new byte[8];
		}
		public int toBuffer(ByteBuffer buffer){
			int len = 0;
			buffer.put(this.msgId);
			len+=this.msgId.length;
			writeToBuffer(buffer, this.stat, 7);
			len+=7;
			writeToBuffer(buffer, this.submitTime, 10);
			len+=10;
			writeToBuffer(buffer, this.doneTime, 10);
			len+=10;
			buffer.putInt(this.smscSequnce);
			len+=4;
			return len;
		}
				
		public void loadBuffer(ByteBuffer buffer){
			buffer.get(this.msgId);
			this.stat = readFromBuffer(buffer, 7);
			this.submitTime = readFromBuffer(buffer, 10);
			this.doneTime = readFromBuffer(buffer, 10);
			this.smscSequnce = buffer.getInt();
		}

		/**
		 * @return the msgId
		 */
		public byte[] getMsgId() {
			return msgId;
		}

		/**
		 * @param msgId the msgId to set
		 */
		public void setMsgId(byte[] msgId) {
			this.msgId = msgId;
		}

		/**
		 * @return the stat
		 */
		public String getStat() {
			return stat;
		}

		/**
		 * @param stat the stat to set
		 */
		public void setStat(String stat) {
			this.stat = stat;
		}

		/**
		 * @return the submitTime
		 */
		public String getSubmitTime() {
			return submitTime;
		}

		/**
		 * @param submitTime the submitTime to set
		 */
		public void setSubmitTime(String submitTime) {
			this.submitTime = submitTime;
		}

		/**
		 * @return the doneTime
		 */
		public String getDoneTime() {
			return doneTime;
		}

		/**
		 * @param doneTime the doneTime to set
		 */
		public void setDoneTime(String doneTime) {
			this.doneTime = doneTime;
		}

		/**
		 * @return the smscSequnce
		 */
		public int getSmscSequnce() {
			return smscSequnce;
		}

		/**
		 * @param smscSequnce the smscSequnce to set
		 */
		public void setSmscSequnce(int smscSequnce) {
			this.smscSequnce = smscSequnce;
		}
		
	}

	/**
	 * @return the msgId
	 */
	public byte[] getMsgId() {
		return msgId;
	}

	/**
	 * @param msgId the msgId to set
	 */
	public void setMsgId(byte[] msgId) {
		this.msgId = msgId;
	}

	/**
	 * @return the destId
	 */
	public String getDestId() {
		return destId;
	}

	/**
	 * @param destId the destId to set
	 */
	public void setDestId(String destId) {
		this.destId = destId;
	}

	/**
	 * @return the serviceId
	 */
	public String getServiceId() {
		return serviceId;
	}

	/**
	 * @param serviceId the serviceId to set
	 */
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * @return the tppId
	 */
	public byte getTppId() {
		return tppId;
	}

	/**
	 * @param tppId the tppId to set
	 */
	public void setTppId(byte tppId) {
		this.tppId = tppId;
	}

	/**
	 * @return the tpUdhi
	 */
	public byte getTpUdhi() {
		return tpUdhi;
	}

	/**
	 * @param tpUdhi the tpUdhi to set
	 */
	public void setTpUdhi(byte tpUdhi) {
		this.tpUdhi = tpUdhi;
	}

	/**
	 * @return the msgFmt
	 */
	public byte getMsgFmt() {
		return msgFmt;
	}

	/**
	 * @param msgFmt the msgFmt to set
	 */
	public void setMsgFmt(byte msgFmt) {
		this.msgFmt = msgFmt;
	}

	/**
	 * @return the srcTerminalId
	 */
	public String getSrcTerminalId() {
		return srcTerminalId;
	}

	/**
	 * @param srcTerminalId the srcTerminalId to set
	 */
	public void setSrcTerminalId(String srcTerminalId) {
		this.srcTerminalId = srcTerminalId;
	}

	/**
	 * @return the registeredDelivery
	 */
	public byte getRegisteredDelivery() {
		return registeredDelivery;
	}

	/**
	 * @param registeredDelivery the registeredDelivery to set
	 */
	public void setRegisteredDelivery(byte registeredDelivery) {
		this.registeredDelivery = registeredDelivery;
	}

	/**
	 * @return the msgLength
	 */
	public byte getMsgLength() {
		return msgLength;
	}

	/**
	 * @param msgLength the msgLength to set
	 */
	public void setMsgLength(byte msgLength) {
		this.msgLength = msgLength;
	}

	/**
	 * @return the msgContent
	 */
	public byte[] getMsgContent() {
		return msgContent;
	}

	/**
	 * @param msgContent the msgContent to set
	 */
	public void setMsgContent(byte[] msgContent) {
		this.msgContent = msgContent;
	}

	/**
	 * @return the reserve
	 */
	public byte[] getReserve() {
		return reserve;
	}

	/**
	 * @param reserve the reserve to set
	 */
	public void setReserve(byte[] reserve) {
		this.reserve = reserve;
	}
	
}
