/**
 * 
 */
package com.tbtosoft.smgw.cmpp;

import java.net.SocketAddress;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;

import com.tbtosoft.cmpp.ConnectReqPkg;
import com.tbtosoft.cmpp.ConnectRspPkg;
import com.tbtosoft.cmpp.IPackage;
import com.tbtosoft.smgw.IGateWay;
import com.tbtosoft.smio.IChain;
import com.tbtosoft.smio.ISmsHandlerFactory;
import com.tbtosoft.smio.LongServerChain;
import com.tbtosoft.smio.ShortChain;
import com.tbtosoft.smio.codec.CmppCoder;
import com.tbtosoft.smio.handlers.ActiveEvent;
import com.tbtosoft.smio.handlers.KeepConnectionEvent;
import com.tbtosoft.smio.handlers.SimpleCmppHandler;

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
