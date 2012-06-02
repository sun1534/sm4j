/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.sgip;

/**
 * @author stephen
 *
 */
final class Command {
	public static final int BIND_REQ = 0x00000001;
	public static final int BIND_RSP = 0x80000001;
	public static final int UNBIND_REQ = 0x00000002;
	public static final int UNBIND_RSP = 0x80000002;
	public static final int SUBMIT_REQ = 0x00000003;
	public static final int SUBMIT_RSP = 0x80000003;
	public static final int DELIVER_REQ = 0x00000004;
	public static final int DELIVER_RSP = 0x80000004;
	public static final int REPORT_REQ = 0x00000005;
	public static final int REPORT_RSP = 0x80000005;
}
