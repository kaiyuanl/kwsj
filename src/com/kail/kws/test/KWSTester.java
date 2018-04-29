package com.kail.kws.test;

import org.apache.log4j.Logger;

public class KWSTester {
	static Logger logger = Logger.getLogger(KWSTester.class.getName());
	public KWSTester() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KWSTester tester = new KWSTester();		
		logger.info(tester.getClass());
		try {
			Class<?> reqClass = Class.forName("com.kail.kws.data.Request");
			logger.info(reqClass);
		} catch (Exception ex) {
			logger.error(ex);
		}
	}
}
