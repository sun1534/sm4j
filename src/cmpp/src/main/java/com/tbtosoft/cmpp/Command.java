/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.cmpp;

/**
 * @author stephen
 *
 */
final class Command {
	public static final int CONNECT_REQ = 0x00000001;
	public static final int CONNECT_RSP = 0x80000001;
	public static final int TERMINATE_REQ = 0x00000002;
	public static final int TERMINATE_RSP = 0x80000002;
	public static final int SUBMIT_REQ = 0x00000004;
	public static final int SUBMIT_RSP = 0x80000004;
	public static final int DELIVER_REQ = 0x00000005;
	public static final int DELIVER_RSP = 0x80000005;
	public static final int QUERY_REQ = 0x00000006;
	public static final int QUERY_RSP = 0x80000006;
	public static final int CANCEL_REQ = 0x00000007;
	public static final int CANCEL_RSP = 0x80000007;
	public static final int ACTIVE_TEST_REQ = 0x00000008;
	public static final int ACTIVE_TEST_RSP = 0x80000008;
	public static final int FWD_REQ = 0x00000009;
	public static final int FWD_RSP = 0x80000009;
	public static final int MT_ROUTE_REQ = 0x00000010;
	public static final int MT_ROUTE_RSP = 0x80000010;
	public static final int MO_ROUTE_REQ = 0x00000011;
	public static final int MO_ROUTE_RSP = 0x80000011;
	public static final int GET_ROUTE_REQ = 0x00000012;
	public static final int GET_ROUTE_RSP = 0x80000012;
	public static final int MT_ROUTE_UPDATE_REQ = 0x00000013;
	public static final int MT_ROUTE_UPDATE_RSP = 0x80000013;
	public static final int MO_ROUTE_UPDATE_REQ = 0x00000014;
	public static final int MO_ROUTE_UPDATE_RSP = 0x80000014;
	public static final int PUSH_MT_ROUTE_UPDATE_REQ = 0x00000015;
	public static final int PUSH_MT_ROUTE_UPDATE_RSP = 0x80000015;
	public static final int PUSH_MO_ROUTE_UPDATE_REQ = 0x00000016;
	public static final int PUSH_MO_ROUTE_UPDATE_RSP = 0x80000016;
}
