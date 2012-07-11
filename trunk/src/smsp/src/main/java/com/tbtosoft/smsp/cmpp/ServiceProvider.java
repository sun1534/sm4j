/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smsp.cmpp;

import java.net.SocketAddress;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import org.jboss.netty.channel.Channel;

import com.tbtosoft.cmpp.ActiveTestReqPkg;
import com.tbtosoft.cmpp.DeliverReqPkg;
import com.tbtosoft.cmpp.DeliverRspPkg;
import com.tbtosoft.cmpp.SubmitReqPkg;
import com.tbtosoft.smio.ILink;
import com.tbtosoft.smio.IoHandler;
import com.tbtosoft.smio.LongLink;
import com.tbtosoft.smio.ShortLink;
import com.tbtosoft.smsp.AbstractSP;

/**
 * @author chengchun
 *
 */
public final class ServiceProvider extends AbstractSP{
	private InnerLink link;
	public ServiceProvider(SocketAddress serverAddress){
		this.link = new InnerLink(serverAddress);
	}
	public ServiceProvider(SocketAddress serverAddress, SocketAddress localAddress){
		this.link = new InnerLink(serverAddress, localAddress);
	}
	private void receiveImpl(Channel channel, Object obj){
		if(obj instanceof DeliverReqPkg){
			receiveDeliverReq(channel, (DeliverReqPkg)obj);
		}
	}
	private void receiveDeliverReq(Channel channel, DeliverReqPkg deliverReqPkg){
		DeliverRspPkg deliverRspPkg = new DeliverRspPkg(deliverReqPkg.getMsgId());
		deliverRspPkg.setResult((byte)0x00);
		deliverRspPkg.setSequence(deliverReqPkg.getSequence());
		channel.write(deliverRspPkg);
		
	}
	
	/* (non-Javadoc)
	 * @see com.tbtosoft.smsp.ISP#send(java.lang.String, java.util.Collection)
	 */
	@Override
	public boolean send(String message, Collection<String> terminals) {
		SubmitReqPkg submitReqPkg = new SubmitReqPkg();
		submitReqPkg.setDestTerminalId(terminals);		
		return link.write(submitReqPkg);
	}
	private boolean isClientToServerMsgImpl(Object obj){
		return false;
	}
	private Object getActiveTestRequestPackage(){
		return new ActiveTestReqPkg();
	}
	class InnerLink implements ILink{
		private LongLink longLink;
		private ShortLink shortLink;
		private Timer longLinkTimer;
		private volatile boolean enableTimer;
		public InnerLink(SocketAddress serverAddress) {
			if (null != shortLink) {
				shortLink.close();
				shortLink = null;
			}
			longLink = new LongLink(serverAddress);
			enableTimer = true;
			longLinkTimer = new Timer("LONG-TIMER");	
			processActiveTest();
		}
		private void processActiveTest(){	
			if(!enableTimer){
				return;
			}
			doActiveTest();
			longLinkTimer.schedule(new TimerTask() {				
				@Override
				public void run() {
					processActiveTest();
				}
			}, 0, 30000);
		}
		private void doActiveTest(){
			if(null != longLink && longLink.isConnected()){
				longLink.write(getActiveTestRequestPackage());
			}
		}
		public InnerLink(SocketAddress serverAddress, SocketAddress localAddress) {
			if (null != longLink) {
				longLink.close();
				longLink = null;
			}
			shortLink = new ShortLink(serverAddress);
		}

		public ILink getActiveLink() {
			return null == longLink ? shortLink : longLink;
		}

		@Override
		public boolean open() {			
			return getActiveLink().open();
		}

		@Override
		public void close() {
			this.enableTimer = false;
			if(null != longLinkTimer){				
				longLinkTimer.cancel();				
			}
			getActiveLink().close();
		}

		@Override
		public boolean write(Object object) {			
			return getActiveLink().write(object);
		}

		@Override
		public boolean isConnected() {			
			return getActiveLink().isConnected();
		}
		
	}
	class InnerIOHanlder implements IoHandler{

		@Override
		public void receive(Channel channel, Object obj) {
			receiveImpl(channel, obj);
		}

		@Override
		public boolean isClientToServerMsg(Object obj) {			
			return isClientToServerMsgImpl(obj);
		}		
	}	
}
