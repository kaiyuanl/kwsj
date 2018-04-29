/**
 * 
 */
package com.kail.kws.common;

import java.util.LinkedList;
import java.util.List;

import com.kail.kws.data.IReusable;

/**
 * @author kaiyuan.liang
 *
 */
public class OPool<T extends IReusable> {
	private LinkedList<T> queue;
	private int init;
	private Object lock;
	public OPool() {
		this(42);
	}
	public OPool(int init) {
		this.init = init;
		this.queue = new LinkedList<T>();
		for(int i = 0; i < init; i++) {
			
		}
	}
	
	private void enqueue(T item) {
		synchronized(lock) {
			this.queue.addLast(item);
		}
	}
	
	private T dequeue() {
		synchronized(lock) {
			if(this.queue.size() > 0) {
				return this.queue.removeFirst();
			} else {
				return null;
			}
		}
	}
	
	public synchronized T use() {
		return this.dequeue();
	}
	
	public synchronized void reuse(T obj) {
		this.enqueue(obj);
	}
}
