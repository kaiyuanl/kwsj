/**
 * 
 */
package com.kail.kws.data;

import java.io.OutputStream;

import com.kail.kws.common.Helper;
import com.kail.kws.data.Response;
import com.kail.kws.type.REQUESTTYPE;

/**
 * @author kaiyuan.liang
 *
 */
public class ResponseDir extends Response {

	/**
	 * 
	 */
	public ResponseDir(Request request) {
		super(request);
	}

	@Override
	public String getContent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void send() {
		// TODO Auto-generated method stub
		if(this.request.getRequestType() != REQUESTTYPE.INVALID) {
			this.SendDir(this.request.getOutputStream(), this.request.getURL());
		}
	}

    public void SendDir(OutputStream out, String url) {
    	Helper.sendDir(out, url);
    }

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		this.request = null;
	}
}
