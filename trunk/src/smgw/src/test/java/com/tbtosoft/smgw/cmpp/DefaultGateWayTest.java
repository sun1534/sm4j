package com.tbtosoft.smgw.cmpp;

import java.io.IOException;
import java.net.InetSocketAddress;

import junit.framework.TestCase;

public class DefaultGateWayTest extends TestCase {
	public void testDefaultGateWay() throws IOException{
		DefaultGateWay defaultGateWay = new DefaultGateWay(new InetSocketAddress(6969));
		defaultGateWay.open();		
		System.in.read();
		defaultGateWay.close();
	}
}
