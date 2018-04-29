package com.kail.kws;

import org.apache.log4j.Logger;

public class Main {
	static Logger logger = Logger.getLogger(Main.class.getName());
    /**
     * @param args
     */
    public static void main(String[] args) {
        logger.info("Launching Kaiyuan's Web Server");
        int port;
        try {
        	port = Integer.parseInt(Configure.getProperty("Port"));
        	Master master = new Master(port);
        	new Thread(master).start();
        } catch (Exception ex) {
        	
        }
    }
}
