/**
 * 
 */
package com.kail.kws.data;

import java.io.OutputStream;
import org.apache.log4j.Logger;

import com.kail.kws.common.Helper;
import com.kail.kws.type.REQUESTTYPE;

/**
 * @author kaiyuan.liang
 *
 */
public class ResponseFile extends Response {
	static Logger logger = Logger.getLogger(ResponseFile.class.getName());
	/**
	 * 
	 */
	public ResponseFile(Request request) {
		super(request);
	}

	@Override
	public String getContent() {
		return null;
	}

	@Override
	public void send() {
		if (this.getRequestType() == REQUESTTYPE.FILE) {
			this.sendFile(this.request.getOutputStream(), this.request.getURL());
		} else {
			this.send404();
		}
	}
	
    private void sendFile(OutputStream out, String url) {
    	Helper.sendFile(out, url);
    }

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		this.request = null;
	}
}
