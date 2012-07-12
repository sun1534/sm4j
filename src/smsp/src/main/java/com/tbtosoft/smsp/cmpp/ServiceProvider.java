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
import java.nio.ByteBuffer;
import java.util.Collection;

import org.jboss.netty.channel.ChannelHandlerContext;

import com.tbtosoft.cmpp.ConnectReqPkg;
import com.tbtosoft.cmpp.ConnectRspPkg;
import com.tbtosoft.cmpp.IPackage;
import com.tbtosoft.cmpp.Packages;
import com.tbtosoft.cmpp.exception.CmppException;
import com.tbtosoft.smio.IChain;
import com.tbtosoft.smio.ICoder;
import com.tbtosoft.smio.LongChain;
import com.tbtosoft.smio.ShortChain;
import com.tbtosoft.smio.handlers.SimpleCmppHandler;
import com.tbtosoft.smsp.AbstractSP;

/**
 * @author chengchun
 *
 */
public final class ServiceProvider extends AbstractSP{
	private IChain chain;
	public ServiceProvider(SocketAddress serverAddress){
		this.chain = new LongChain<IPackage, ICoder<IPackage>>(serverAddress, 30000, new InnerCoder());
		this.chain.addHandler("INNER-SMS-IO-HANDLER", new InnerSmsIoHandler());
	}
	public ServiceProvider(SocketAddress serverAddress, SocketAddress localAddress){
		this.chain = new ShortChain(serverAddress, localAddress);
	}
	/* (non-Javadoc)
	 * @see com.tbtosoft.smsp.ISP#send(java.lang.String, java.util.Collection)
	 */
	@Override
	public boolean send(String message, Collection<String> terminals) {
//		SubmitReqPkg submitReqPkg = new SubmitReqPkg();
//		submitReqPkg.setDestTerminalId(terminals);		
//		return link.write(submitReqPkg);
		return true;
	}
	
	class InnerSmsIoHandler extends SimpleCmppHandler{

		@Override
		public void received(ChannelHandlerContext ctx,
				ConnectReqPkg connectReqPkg) {
			
		}

		@Override
		public void received(ChannelHandlerContext ctx,
				ConnectRspPkg connectRspPkg) {
			
		}		
	}
	class InnerCoder implements ICoder<IPackage>{

		@Override
		public IPackage decode(ByteBuffer buffer) {
			try {
				return Packages.parse(buffer);
			} catch (CmppException e) {				
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public ByteBuffer encode(IPackage t) {
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			try {
				t.toBuffer(buffer);
			} catch (CmppException e) {				
				e.printStackTrace();
			}
			return buffer;
		}

		@Override
		public int getMinBytes() {			
			return 4;
		}
		
	}
	
}
