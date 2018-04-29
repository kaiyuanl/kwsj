package com.kail.kws;
import java.io.IOException;
import java.net.*;

import org.apache.log4j.Logger;

import com.kail.kws.common.TPool;
public class Master implements Runnable {
	static Logger logger = Logger.getLogger(Master.class.getName());
    private ServerSocket serverSocket;
    private boolean running;
    private int port;
    private TPool pool = new TPool();
    public Master(int port) {
    	this.running = false;
    	this.port = port;
    }
    public void run() {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            logger.error(ex);
            return;
        }
        this.running = true;
        while(this.running) {
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
                logger.info("Accept request from " + clientSocket.getInetAddress());
            } catch (IOException ex) {
                logger.error(ex);
                continue;
            }
            Runnable worker = new Worker(clientSocket);
            this.pool.execute(worker);
        }
        try {
            this.serverSocket.close();
        } catch (IOException ex) {
        	logger.error(ex);
        }    	
    }
    public void Start() {
        this.running = true;
    }
    public void Stop() {
        this.running = false;
    }
}
