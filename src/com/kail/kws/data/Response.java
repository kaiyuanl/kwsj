package com.kail.kws.data;
import org.apache.log4j.Logger;

import com.kail.kws.common.Helper;
import com.kail.kws.type.REQUESTTYPE;
import com.kail.kws.type.VERSION;

public abstract class Response implements IReusable {
	static Logger logger = Logger.getLogger(Response.class.getName());
    private int code;
    private VERSION version;
    protected Request request;
    
    //private HashMap<String, String> params = new HashMap<String, String>();
    //private String body;


    public Response(Request request) {
        this.request = request;
    }

    public int getCode() {
        return this.code;
    }
    
    public void setCode(int code) {
    	this.code = code;
    }
    
    public VERSION getVersion() {
    	return this.version;
    }

    public String getURL() {
        return this.request.getURL();
    }

    public REQUESTTYPE getRequestType() {
    	return this.request.getRequestType();
    }
    
    protected void send404() {
    	Helper.sendFile(this.request.getOutputStream(), "/static/404.html");
    }
    
    public static Response getResponse(Request request) {
    	Response response = null;
    	switch(request.getRequestType()) {
    	case FILE:
    		response = new ResponseFile(request);
    		break;
    	case DIR:
    		response = new ResponseDir(request);
    		break;
    	case DYN:
    		response = new ResponseDyn(request);
    		break;
    	case INVALID:
    	default:
    		response = new ResponseNotFound(request);
    		break;
    	}
    	return response;
    }
    
    public void init(Request request) {
    	this.request = request;
    }
    
    public abstract String getContent();
    
    public abstract void send();
}
