package com.kail.kws;
import java.io.IOException;
import java.lang.Runnable;
import java.net.*;

import org.apache.log4j.Logger;

import com.kail.kws.common.Parser;
import com.kail.kws.data.Request;
import com.kail.kws.data.Response;

public class Worker implements Runnable {
	static Logger logger = Logger.getLogger(Worker.class.getName());
	
    private Socket socket;

    public Worker(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        Request request = Parser.parse(this.socket);
        if(request == null) {
            logger.info("Request invalid");
            return;
        }
        Response response = Response.getResponse(request);
        if (response == null) {
        	logger.info("Failed to generate response");
        	return;
        }
        response.send();
        try {
        	socket.close();
        } catch (IOException ex) {
        	logger.error(ex);
        }
    }
}
