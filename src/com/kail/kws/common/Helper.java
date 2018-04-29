package com.kail.kws.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.kail.kws.Configure;

public class Helper {
	static Logger logger = Logger.getLogger(Helper.class.getName());
    public static String getServerTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(calendar.getTime());
    }
    
    public static void sendFile(OutputStream out, String url) {
        int buffLen = 1024;
        int read;
        String path = Configure.getProperty("WWWRoot") + url;
        InputStream file = null;
        BufferedInputStream in = null;
        String header = null;
        try {
            file = new FileInputStream(path);
            byte[] buff = new byte[buffLen];
            in = new BufferedInputStream(file);
            
            header =
            "HTTP/1.1 200 OK\r\n" +
            "Date: " + Helper.getServerTime() + "\r\n" +
            "Connection: Closed\r\n" +
            "Content-Length: " + in.available() + 
            "\r\n\r\n";
            
            byte[] headerBytes = header.getBytes(StandardCharsets.UTF_8);
            out.write(headerBytes);
            
            while((read = in.available()) > 0) {
                if(read > buffLen) {
                    in.read(buff, 0, buffLen);
                    out.write(buff);
                } else {
                    in.read(buff, 0, read);
                    out.write(buff, 0, read);
                }
            }
            
            out.flush();
            if(file != null) file.close();
            if(in != null) in.close();
            if(out != null) out.close();
        } catch(Exception ex) {
            logger.error(ex);
        }
    }
    
    public static void sendDir(OutputStream out, String url) {
    	String path = Configure.getProperty("WWWRoot") + url;
    	try {
	        File[] files = new File(path).listFiles();
	        
	        String body = "<html>\n" +
	                "    <title>kws</title>\n" +
	                "    <body>\n" +
	                "        <hr>\n" +
	                "        <ul>\n";
	
	        for(File file: files) {
	            String item = String.format(
	            		"<li><a href=\"%s\">%s</a></li>\n",
	                    url + file.getName() + (file.isDirectory()?"/":""),
	                    file.getName());
	
	            body += item;
	        }
	        
	        body += "        </ul>\n" +
	                "        <hr>\n" +
	                "        <i>kws created by Kaiyuan Liang</i>\n" +
	                "    </body>\n" +
	                "</html>";
	        String header =
	                "HTTP/1.1 200 OK\r\n" +
	                        "Date: " + Helper.getServerTime() + "\r\n" +
	                        "Connection: Closed\r\n" +
	                        "Content-Length: " + body.getBytes("GBK").length + 
	                        "\r\n\r\n";
	        
	        String result = header + body;
	        logger.info(result);
	        byte[] outBytes = result.getBytes("GBK");
	        out.write(outBytes);
    	} catch (Exception ex) {
    		logger.error(ex);
    	}
    }
}
