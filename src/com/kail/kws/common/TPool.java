/**
 * 
 */
package com.kail.kws.common;

import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.kail.kws.Configure;

import java.util.concurrent.ExecutorService;

/**
 * @author kaiyuan.liang
 *
 */
public class TPool {
	static Logger logger = Logger.getLogger(TPool.class.getName());
	
	private int threadNum = 10;
	ExecutorService pool = null;
	public TPool() {
		logger.info("Initializing thread pool");
		try {
			String numStr =  Configure.getProperty("PoolThreadNum");
			this.threadNum = Integer.valueOf(numStr);
		} catch (NumberFormatException ex) {
			logger.error(ex);
		}
		this.pool = Executors.newFixedThreadPool(this.threadNum);
	}

	public void execute(Runnable task) {
		this.pool.execute(task);
	}
	
	public void shotdown() {
		this.pool.shutdown();
		logger.info("Shutdown thread pool");
	}
}
