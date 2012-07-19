/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio;


/**
 * @author chengchun
 * 不需要写操作，在端连接中有新数据发送的时候，先放入缓存，
 * 然后连接服务端，成功后发送登录数据，登录成功后，再将刚才缓存的数据发送出去。
 */
public interface IChain {
	public void setSmsHandlerFactory(ISmsHandlerFactory smsHandlerFactory);	
	public boolean open();
	public void close();
}
