/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio.utils;

import java.util.Iterator;
import java.util.Map.Entry;

import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;

/**
 * @author chengchun
 *
 */
public final class ChannelPipeHelper {
	public interface IBlock{
		public void loop(String name, ChannelHandler channelHandler);
	}
	public final static void lookup(ChannelPipeline channelPipeline, IBlock block){
		Iterator<Entry<String, ChannelHandler>> iter = channelPipeline.toMap().entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, ChannelHandler> entry = iter.next();
			block.loop(entry.getKey(), entry.getValue());					
		}
	}
	public final static void addLast(ChannelPipeline src, final ChannelPipeline dst){
		lookup(dst, new IBlock() {
			
			@Override
			public void loop(String name, ChannelHandler channelHandler) {
				dst.addLast(name, channelHandler);
			}
		});
	}
	public final static void getUpHandlers(ChannelPipeline channelPipeline){
		
	}
	public final static void getDownHandlers(ChannelPipeline channelPipeline){
		
	}
}
