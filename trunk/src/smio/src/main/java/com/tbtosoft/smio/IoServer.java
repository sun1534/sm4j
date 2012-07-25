/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio;

import java.net.SocketAddress;

import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.tbtosoft.smio.impl.IOServerBootStrap;

/**
 * @author chengchun
 *
 */
public class IoServer {
	private IOServerBootStrap serverBootStrap;	
	public IoServer(ICoder coder, SocketAddress localAddress){
		this.serverBootStrap = new IOServerBootStrap(new NioServerSocketChannelFactory());
		this.serverBootStrap.setCoder(coder);
		this.serverBootStrap.setOption("localAddress", localAddress);
	}
	
}
