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
public final class DeliverReqPkg extends AbstractPackage {
	/**
	 * 信息标识
	 * 生成算法如下：
	 * 采用64位（8字节）的整数：
	 * （1）	时间（格式为MMDDHHMMSS，即月日时分秒）：bit64~bit39，其中
	 * bit64~bit61：月份的二进制表示；
	 * bit60~bit56：日的二进制表示；
	 * bit55~bit51：小时的二进制表示；
	 * bit50~bit45：分的二进制表示；
	 * bit44~bit39：秒的二进制表示；
	 * （2）	短信网关代码：bit38~bit17，把短信网关的代码转换为整数填写到该字段中。
	 * （3）	序列号：bit16~bit1，顺序增加，步长为1，循环使用。
	 * 各部分如不能填满，左补零，右对齐。
	 */
	private byte[] msgId;//8bytes
	/**
	 * 目的号码 
	 * SP的服务代码，一般4--6位，或者是前缀为服务代码的长号码；该号码是手机用户短消息的被叫号码。
	 */
	private String destId;//21bytes
	/**
	 * 业务类型，是数字、字母和符号的组合。
	 */
	private String serviceId;//10bytes
	/**
	 * GSM协议类型。详细解释请参考GSM03.40中的9.2.3.9
	 */
	private byte tppId;
	/**
	 * GSM协议类型。详细解释请参考GSM03.40中的9.2.3.23，仅使用1位，右对齐
	 */
	private byte tpUdhi;
	/**
	 * 信息格式
  	 * 0：ASCII串
  	 * 3：短信写卡操作
  	 * 4：二进制信息
  	 * 8：UCS2编码
	 * 15：含GB汉字 
	 */
	private byte msgFmt;
	/**
	 * 源终端MSISDN号码（状态报告时填为CMPP_SUBMIT消息的目的终端号码）
	 */
	private String srcTerminalId;//21bytes
	/**
	 * 是否为状态报告
	 * 0：非状态报告
	 * 1：状态报告
	 */
	private byte registeredDelivery;
	/**
	 * 消息内容
	 */
	private byte[] msgContent;
	/**
	 * 保留
	 */
	private byte[] reserve;//8bytes
	public DeliverReqPkg() {
		this(new byte[8]);		
	}
	public DeliverReqPkg(byte[] msgId){
		super(Command.DELIVER_REQ);	
		this.msgId = msgId;
		this.reserve = new byte[8];
	}
	public DeliverReqPkg(byte[] msgId, byte msgFmt, String content){
		this(msgId);
		this.registeredDelivery = 0;
		this.msgFmt = msgFmt;
		msgContent = content.getBytes(Charset.forName(mapMsgFmt.get(this.msgFmt)));
	}
	public DeliverReqPkg(byte[] msgId, StatusReport statusReport){
		this(msgId);
		this.registeredDelivery = 1;
		this.msgContent = new byte[StatusReport.LENGTH];
		statusReport.toBuffer(ByteBuffer.wrap(this.msgContent));
	}
	/* (non-Javadoc)
	 * @see com.tbtosoft.cmpp.AbstractPackage#onToBuffer(java.nio.ByteBuffer)
	 */
	@Override
	protected int onToBuffer(ByteBuffer buffer) {
		int len = 0;
		len+=write(buffer, this.msgId);
		len+=writeString(buffer, this.destId, 21);	
		len+=writeString(buffer, this.serviceId, 10);	
		len+=write(buffer, this.tppId);		
		len+=write(buffer, this.tpUdhi);
		len+=write(buffer, this.msgFmt);
		len+=writeString(buffer, this.srcTerminalId, 21);		
		len+=write(buffer, this.registeredDelivery);		
		len+=write(buffer, (byte)this.msgContent.length);		
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
		this.destId = readString(buffer, 21);
		this.serviceId = readString(buffer, 10);
		this.tppId = read(buffer);
		this.tpUdhi = read(buffer);
		this.msgFmt = read(buffer);
		this.srcTerminalId = readString(buffer, 21);
		this.registeredDelivery = read(buffer);
		byte msgLength = read(buffer);
		this.msgContent = new byte[msgLength];
		read(buffer, this.msgContent);
		read(buffer, this.reserve);
	}
	
	public class StatusReport{
		private byte[] msgId;//8bytes
		private String stat;//7bytes;
		private String submitTime;//10bytes
		private String doneTime;//10byte;
		private String destTerminalId;//21bytes
		private int smscSequnce;//4bytes
		private final static int LENGTH=8+7+10+10+21+4;
		public StatusReport(){
			this.msgId = new byte[8];
		}
		public StatusReport(byte[] msgId){
			this.msgId = msgId;
		}
		public int toBuffer(ByteBuffer buffer){
			int len = 0;
			len+=write(buffer, this.msgId);
			len+=writeString(buffer, this.stat, 7);
			len+=writeString(buffer, this.submitTime, 10);
			len+=writeString(buffer, this.doneTime, 10);
			len+=writeString(buffer, this.destTerminalId, 21);
			len+=writeInt(buffer, this.smscSequnce);
			return len;
		}
				
		public void loadBuffer(ByteBuffer buffer){
			read(buffer, this.msgId);
			this.stat = readString(buffer, 7);
			this.submitTime = readString(buffer, 10);
			this.doneTime = readString(buffer, 10);
			this.destTerminalId = readString(buffer, 21);
			this.smscSequnce = read(buffer);
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
	public String getMsgContentString() throws CmppException{
		try{
		return new String(this.msgContent, 0, this.msgContent.length, 
				Charset.forName(mapMsgFmt.get(this.msgFmt)));
		}catch (Exception e) {
			throw new CmppException("msgContent hex:"+Arrays.toString(this.msgContent)+
					" msgFmt:"+this.msgFmt, e);
		}
	}
	public StatusReport getStatusReport(){
		StatusReport statusReport = new StatusReport();
		statusReport.loadBuffer(ByteBuffer.wrap(this.msgContent));
		return statusReport;
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
