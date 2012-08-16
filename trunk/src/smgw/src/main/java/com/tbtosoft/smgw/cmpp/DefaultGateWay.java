/**
 * 
 */
package com.tbtosoft.smgw.cmpp;

import java.net.SocketAddress;

import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;

import com.tbtosoft.smgw.IGateWay;
import com.tbtosoft.smio.IChain;
import com.tbtosoft.smio.LongServerChain;
import com.tbtosoft.smio.ShortChain;
import com.tbtosoft.smio.codec.CmppCoder;

/**
 * @author chengchun
 *
 */
public class DefaultGateWay implements IGateWay {
	private static final InternalLogger logger =
	        InternalLoggerFactory.getInstance(DefaultGateWay.class);
	private IChain chain;
	public DefaultGateWay(SocketAddress localAddress){
		this.chain = new LongServerChain(localAddress, 10000, new CmppCoder());
		initialize();
	}
	public DefaultGateWay(SocketAddress localAddress, SocketAddress serverAddress){
		this.chain = new ShortChain(serverAddress, localAddress, 10000, new CmppCoder());
		initialize();
	}
	private void initialize(){
		
	}
	@Override
	public boolean open() {		
		return this.chain.open();
	}
	@Override
	public void close() {
		this.chain.close();
	}
	
}
