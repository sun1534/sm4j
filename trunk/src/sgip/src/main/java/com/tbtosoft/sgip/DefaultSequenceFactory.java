/**
 * 
 */
package com.tbtosoft.sgip;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author chengchun
 *
 */
public class DefaultSequenceFactory implements ISequenceFactory {
	private int srcNodeId;
	private int sequnece;
	public DefaultSequenceFactory(int srcNodeId){
		this.srcNodeId = srcNodeId;
	}
	/* (non-Javadoc)
	 * @see com.tbtosoft.sgip.ISequenceFactory#next()
	 */
	@Override
	public synchronized byte[] next() {
		byte[] tmp = new byte[12];
		ByteBuffer buffer = ByteBuffer.wrap(tmp);
		buffer.putInt(this.srcNodeId);
		buffer.putInt(nextTimestamp(new Date()));
		buffer.putInt(nextSequnece());
		return tmp;
	}
	private int nextSequnece(){
		return sequnece++;
	}
	private int nextTimestamp(Date date){
		return Integer.parseInt(new SimpleDateFormat("MMddHHmmss").format(date));
	}
	/**
	 * @return the srcNodeId
	 */
	public final int getSrcNodeId() {
		return srcNodeId;
	}
	/**
	 * @param srcNodeId the srcNodeId to set
	 */
	public final void setSrcNodeId(int srcNodeId) {
		this.srcNodeId = srcNodeId;
	}	
}
