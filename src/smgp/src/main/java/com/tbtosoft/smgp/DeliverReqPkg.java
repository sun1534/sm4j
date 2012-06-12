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

/**
 * @author stephen
 *
 */
public final class DeliverReqPkg extends AbstractPackage {	
	private byte[] msgId;//10bytes
	private byte isReport;
	private byte msgFormat;
	private String recvTime;//14bytes
	private String srcTermID;//21bytes
	private String destTermID;//21bytes
	private byte msgLength;
	private byte[] msgContent;
	private byte[] reserve;//8bytes
	
	
	protected DeliverReqPkg() {
		super(Command.DELIVER_REQ);	
		this.msgId = new byte[10];
		this.reserve = new byte[8];
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.cmpp.AbstractPackage#onToBuffer(java.nio.ByteBuffer)
	 */
	@Override
	protected int onToBuffer(ByteBuffer buffer) {
		int len = 0;
		len+=write(buffer, this.msgId);
		len+=write(buffer, this.isReport);
		len+=write(buffer, this.msgFormat);
		len+=writeString(buffer, this.recvTime, 14);
		len+=writeString(buffer, this.srcTermID, 21);
		len+=writeString(buffer, this.destTermID, 21);
		len+=write(buffer, this.msgLength);
		len+=write(buffer, this.msgContent);
		len+=write(buffer, this.reserve);
		return len;
	}

	/* (non-Javadoc)
	 * @see com.tbtosoft.cmpp.AbstractPackage#onLoadBuffer(java.nio.ByteBuffer)
	 */
	@Override
	protected void onLoadBuffer(ByteBuffer buffer) {
		read(buffer, this.msgId);
		this.isReport = read(buffer);
		this.msgFormat = read(buffer);
		this.recvTime = readString(buffer, 14);
		this.srcTermID = readString(buffer, 21);
		this.destTermID = readString(buffer, 21);
		this.msgLength = read(buffer);
		this.msgContent = new byte[this.msgLength];
		read(buffer, this.msgContent);
		read(buffer, this.reserve);
	}
	
	public class StatusReport{
		private byte[] msgId;//10bytes
		private String sub;//3bytes
		private String dlvrd;//3bytes
		private String submitDate;//10bytes
		private String doneDate;//10bytes		
		private String stat;//7bytes
		private String err;//3bytes
		private String txt;//20bytes;
		public StatusReport(){
			msgId = new byte[10];
		}
		public int toBuffer(ByteBuffer buffer){
			int len = 0;
			len+=write(buffer, this.msgId);
			len+=writeString(buffer, this.sub, 3);
			len+=writeString(buffer, this.dlvrd, 3);
			len+=writeString(buffer, this.submitDate, 10);
			len+=writeString(buffer, this.doneDate, 10);
			len+=writeString(buffer, this.stat, 7);
			len+=writeString(buffer, this.err, 3);
			len+=writeString(buffer, this.txt, 20);
			return len;
		}
				
		public void loadBuffer(ByteBuffer buffer){
			read(buffer, this.msgId);
			this.sub = readString(buffer, 3);
			this.dlvrd = readString(buffer, 3);
			this.submitDate = readString(buffer, 10);
			this.doneDate = readString(buffer, 10);
			this.stat = readString(buffer, 7);
			this.err = readString(buffer, 3);
			this.txt = readString(buffer, 20);
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
		 * @return the sub
		 */
		public String getSub() {
			return sub;
		}

		/**
		 * @param sub the sub to set
		 */
		public void setSub(String sub) {
			this.sub = sub;
		}

		/**
		 * @return the dlvrd
		 */
		public String getDlvrd() {
			return dlvrd;
		}

		/**
		 * @param dlvrd the dlvrd to set
		 */
		public void setDlvrd(String dlvrd) {
			this.dlvrd = dlvrd;
		}

		/**
		 * @return the submitDate
		 */
		public String getSubmitDate() {
			return submitDate;
		}

		/**
		 * @param submitDate the submitDate to set
		 */
		public void setSubmitDate(String submitDate) {
			this.submitDate = submitDate;
		}

		/**
		 * @return the doneDate
		 */
		public String getDoneDate() {
			return doneDate;
		}

		/**
		 * @param doneDate the doneDate to set
		 */
		public void setDoneDate(String doneDate) {
			this.doneDate = doneDate;
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
		 * @return the err
		 */
		public String getErr() {
			return err;
		}

		/**
		 * @param err the err to set
		 */
		public void setErr(String err) {
			this.err = err;
		}

		/**
		 * @return the txt
		 */
		public String getTxt() {
			return txt;
		}

		/**
		 * @param txt the txt to set
		 */
		public void setTxt(String txt) {
			this.txt = txt;
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
