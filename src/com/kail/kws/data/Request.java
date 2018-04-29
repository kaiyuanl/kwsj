package com.kail.kws.data;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.*;

import org.apache.log4j.Logger;

import com.kail.kws.type.METHOD;
import com.kail.kws.type.REQUESTTYPE;
import com.kail.kws.type.VERSION;

import java.io.*;

public class Request implements IReusable {
	static Logger logger = Logger.getLogger(Request.class.getName());
	
    private VERSION version;
    private METHOD method;
    private String url;
    private HashMap<String, String> fields;
    private HashMap<String, String> queryParams;
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private REQUESTTYPE requestType;

    public Request(Socket socket) {
    	this.init(socket);
    }
    
    public void init(Socket socket) {
        this.socket = socket;
        try {
            this.in = this.socket.getInputStream();
            this.out = this.socket.getOutputStream();
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
        this.fields = new HashMap<String, String>();
    }

    public VERSION getVersion() {
        return this.version;
    }

    public void setVersion(VERSION version) {
        this.version = version;
    }

    public void setVersion(String version) {
        switch (version) {
            case "HTTP/0.9":
                this.version = VERSION.V09;
                break;
            case "HTTP/1.0":
                this.version = VERSION.V10;
                break;
            case "HTTP/1.1":
                this.version = VERSION.V11;
                break;
            default:
                break;
        }
    }

    public METHOD getMethod() {
        return this.method;
    }

    public void setMethod(METHOD method) {
        this.method = method;
    }

    public void setMethod(String method) {
        switch (method) {
            case "GET":
                this.method = METHOD.GET;
                break;
            case "POST":
                this.method = METHOD.POST;
                break;
            case "PUT":
                this.method = METHOD.PUT;
                break;
            case "DELETE":
                this.method = METHOD.DELETE;
                break;
            default:
                break;
        }
    }

    public String getURL() {
        return this.url;
    }

    public void setURL(String url) {
    	try {
        this.url = URLDecoder.decode(url, "UTF-8");
        logger.debug(this.url);
    	} catch (Exception ex) {
    		this.url = url;
    		logger.info(ex);
    	}
    }

    public InputStream getInputStream() {
        return this.in;
    }

    public OutputStream getOutputStream() {
        return this.out;
    }

    public String getParam(String key) {
        return this.fields.get(key);
    }

    public void setParam(String key, String value) {
        this.fields.put(key, value);
    }
    
    public String getQueryParam(String key) {
    	return this.queryParams.get(key);
    }
    
    public void setQueryParam(String key, String value) {
    	this.queryParams.put(key, value);
    }
    
    public REQUESTTYPE getRequestType() {
    	return this.requestType;
    }
    
    public void setRequestType(REQUESTTYPE requestType) {
    	this.requestType = requestType;
    }

	@Override
	public void clear() {
		this.socket = null;
		this.in = null;
		this.out = null;
		this.fields = null;
		this.queryParams = null;
		this.url = null;
	}
}
