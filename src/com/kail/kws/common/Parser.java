package com.kail.kws.common;
import java.io.*;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.kail.kws.Configure;
import com.kail.kws.data.Request;
import com.kail.kws.type.REQUESTTYPE;

public class Parser {
	static Logger logger = Logger.getLogger(Parser.class.getName());
    public static Request parse(Socket socket) {
        Request request = new Request(socket);
        String firstLine;
        BufferedReader buff;
        try {
        	buff = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        } catch (Exception ex) {
        	logger.error(ex);
        	return null;
        }
        try {
            firstLine = buff.readLine();
            //logger.debug(firstLine);
        } catch (IOException ex) {
            logger.error(ex);
            return null;
        }
        String[] arguments = firstLine.split(" ");
        if(arguments.length != 3) {
            return null;
        }

        String method = arguments[0];
        String url = arguments[1];
        String version = arguments[2];
        
        String[] urlPath = url.split("?");
        if(urlPath.length == 1) {
        	//No query parameters in url
        } else if(urlPath.length == 2) {
        	url = urlPath[0];
        	String queryParams = urlPath[1];
        	String[] queryParam = queryParams.split("&");
        	for(String param : queryParam) {
        		String[] kv = param.split("=");
        		if(kv.length != 2) {
        			continue;
        		}
        		String key = kv[0];
        		String value = kv[1];
        		request.setQueryParam(key, value);
        	}
        } else {
        	url = urlPath[0];
        }

        request.setMethod(method);
        request.setURL(url);
        request.setVersion(version);
        
        setRequestType(request);

        String line;
        try {
            while ((line = buff.readLine()) != null) {
            	logger.debug(line);
                if(line.length() == 0) {
                    break;
                }
                String[] pair = line.split(": ");
                if (pair.length != 2) {
                    return null;
                }
                String key = pair[0];
                String value = pair[1];
                request.setParam(key, value);
            }
        } catch(IOException ex) {
            logger.error(ex);
            return null;
        }
        return request;
    }
    
    private static void setRequestType(Request request) {
        String wwwroot = Configure.getProperty("WWWRoot");
        if(wwwroot == null) {
            wwwroot = ".";
        }

        String path = wwwroot + request.getURL();
        logger.info("Request path is " + path);
        File f = new File(path);
        if(!f.exists()) {
            request.setRequestType(REQUESTTYPE.INVALID);
            logger.info("Invalid path is" + path);
            return;
        }

        if(f.isFile()) {
            request.setRequestType(REQUESTTYPE.FILE);
            return;
        }

        if(f.isDirectory()) {
        	request.setRequestType(REQUESTTYPE.DIR);
            return;
        }
        
        request.setRequestType(REQUESTTYPE.DYN);
    }
}
