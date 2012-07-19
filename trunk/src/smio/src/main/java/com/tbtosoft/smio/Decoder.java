/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * @author chengchun
 *
 */
public class Decoder extends FrameDecoder{		
	private ICoder coder;
	public Decoder(ICoder coder){
		this.coder = coder;
	}
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buffer) throws Exception {
		int minSize = coder.getMinBytes();
		if(buffer.readable() && buffer.readableBytes()>=minSize){
            int length = buffer.getInt(buffer.readerIndex());
            if(length > buffer.readableBytes()){
                return null;
            }else{
                ChannelBuffer channelbuffer = null;
                try{
                    channelbuffer = ChannelBuffers.buffer(length);
                    buffer.readBytes(channelbuffer, length);
                    return coder.decode(channelbuffer.toByteBuffer());
                }catch (Exception e) {
					e.printStackTrace();
				}
                
            }
		}		
		return null;
	}
	
}
