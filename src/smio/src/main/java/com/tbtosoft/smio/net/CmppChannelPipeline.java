/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio.net;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

/**
 * @author chengchun
 *
 */
public final class CmppChannelPipeline implements ChannelPipelineFactory {	
	private Map<String, ChannelHandler> name2handlers = new HashMap<String, ChannelHandler>();
	
	public void addChannelHander(String name, ChannelHandler handler){
		name2handlers.put(name, handler);
	}
	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.ChannelPipelineFactory#getPipeline()
	 */
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();		
		if(null != name2handlers){
			Iterator<Map.Entry<String, ChannelHandler>> iterator = name2handlers.entrySet().iterator();
			while(iterator.hasNext()){
				Entry<String, ChannelHandler> entry = iterator.next();
				pipeline.addLast(entry.getKey(), entry.getValue());
			}
		}
		pipeline.addLast("cmpp-encoder", new CmppEncoder());
		pipeline.addLast("cmpp-decoder", new CmppDecoder());		
		return pipeline;
	}

}
