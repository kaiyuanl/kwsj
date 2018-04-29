package com.kail.kws;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.io.FileInputStream;
public class Configure {
	static Logger logger = Logger.getLogger(Configure.class.getName());
    private static Properties prop = null;
    static {
        prop = new Properties();
        InputStream in = null;
        try {
            in = new FileInputStream("Configure.properties");
            prop.load(in);
        } catch (Exception ex) {
            logger.error(ex);
        }
        logger.info("Reading configuration file");
        Enumeration<Object>  e = prop.keys();
        while(e.hasMoreElements()) {
        	String key = (String) e.nextElement();
        	String value = (String) prop.getProperty(key);
        	logger.info(key + " : " + value);
        }
    }

    public static String getProperty(String prop) {
        return Configure.prop.getProperty(prop);
    }
}
