/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * @author chengchun
 *
 */
public class Encoder<E, T extends ICoder<E>> extends OneToOneEncoder {
	private Type msgType;
	private T coder;
	public Encoder(T coder){
		this.coder = coder;
		this.msgType = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	/* (non-Javadoc)
	 * @see org.jboss.netty.handler.codec.oneone.OneToOneEncoder#encode(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {
		if(null != this.msgType && this.msgType.equals(msg.getClass())){
			coder.encode((E)msg);
		}
		return null;
	}

}
