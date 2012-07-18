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
import com.tbtosoft.smio.ICoder;
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
		this.chain = new LongServerChain<IPackage, ICoder<IPackage>>(localAddress, 10000, new CmppCoder());
		initialize();
	}
	public DefaultGateWay(SocketAddress localAddress, SocketAddress serverAddress){
		this.chain = new ShortChain<IPackage, ICoder<IPackage>>(serverAddress, localAddress, 10000, new CmppCoder());
		initialize();
	}
	private void initialize(){
		this.chain.setSmsHandlerFactory(new ISmsHandlerFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				return  Channels.pipeline(new InnerHanlder().getChannelHandler());
			}
		});
	}
	@Override
	public boolean open() {		
		return this.chain.open();
	}
	@Override
	public void close() {
		this.chain.close();
	}
	class InnerHanlder extends SimpleCmppHandler{
		
		
		/* (non-Javadoc)
		 * @see com.tbtosoft.smio.SmsIoHandler#onChannelConnected(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
		 */
		@Override
		protected void onChannelConnected(ChannelHandlerContext ctx,
				ChannelStateEvent e) {
			logger.info(ctx.getChannel().toString()+" incoming...");		
		}

		/* (non-Javadoc)
		 * @see com.tbtosoft.smio.SmsIoHandler#onChannelDisconnected(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
		 */
		@Override
		protected void onChannelDisconnected(ChannelHandlerContext ctx,
				ChannelStateEvent e) {
			logger.info(ctx.getChannel().toString()+" outgoing...");
		}

		/* (non-Javadoc)
		 * @see com.tbtosoft.smio.SmsIoHandler#onChannelIdle(org.jboss.netty.channel.ChannelHandlerContext, com.tbtosoft.smio.handlers.ActiveEvent)
		 */
		@Override
		protected void onChannelIdle(ChannelHandlerContext ctx, ActiveEvent e) {
			logger.info("ActiveTest");			
			super.onChannelIdle(ctx, e);
		}

		/* (non-Javadoc)
		 * @see com.tbtosoft.smio.SmsIoHandler#onChannelKeepAlive(org.jboss.netty.channel.ChannelHandlerContext, com.tbtosoft.smio.handlers.KeepAliveEvent)
		 */
		@Override
		protected void onChannelKeepAlive(ChannelHandlerContext ctx,
				KeepConnectionEvent e) {
			
			super.onChannelKeepAlive(ctx, e);
		}

		/* (non-Javadoc)
		 * @see com.tbtosoft.smio.handlers.SimpleCmppHandler#received(org.jboss.netty.channel.ChannelHandlerContext, com.tbtosoft.cmpp.ConnectReqPkg)
		 */
		@Override
		public void received(ChannelHandlerContext ctx,
				ConnectReqPkg connectReqPkg) {			
			super.received(ctx, connectReqPkg);
		}

		/* (non-Javadoc)
		 * @see com.tbtosoft.smio.handlers.SimpleCmppHandler#received(org.jboss.netty.channel.ChannelHandlerContext, com.tbtosoft.cmpp.ConnectRspPkg)
		 */
		@Override
		public void received(ChannelHandlerContext ctx,
				ConnectRspPkg connectRspPkg) {			
			super.received(ctx, connectRspPkg);
		}

		/* (non-Javadoc)
		 * @see com.tbtosoft.smio.handlers.SimpleCmppHandler#receiveImpl(org.jboss.netty.channel.ChannelHandlerContext, com.tbtosoft.cmpp.IPackage)
		 */
		@Override
		protected void receiveImpl(ChannelHandlerContext ctx, IPackage t) {			
			super.receiveImpl(ctx, t);
		}
		
	}	
}
