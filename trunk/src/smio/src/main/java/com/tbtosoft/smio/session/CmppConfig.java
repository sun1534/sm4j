/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.smio.session;

/**
 * @author chengchun
 *
 */
public class CmppConfig extends Config{
	/**
	 * 设置选择使用双链路还是单链路
	 */
	public final static String LINK_FLAG = "link_flag";
	public final static String MT_HOST_IP = "mt_host_ip";
	public final static String MT_HOST_PORT = "mt_host_port";
	public final static String MO_HOST_IP = "mo_host_ip";
	public final static String MO_HOST_PORT = "mo_host_port";
	public final static String MT_MO_HOST_IP = "mt_mo_host_ip";
	public final static String MT_MO_HOST_PORT = "mt_mo_host_port";
	/**
	 * 源地址，此处为SP_Id，即SP的企业代码
	 */
	public final static String SOURCE_ADDR ="Source_Addr";
	public final static String SHARED_SECRET = "shared_secret";
}
