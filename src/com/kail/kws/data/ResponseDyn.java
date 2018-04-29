package com.kail.kws.data;

public class ResponseDyn extends Response {

	public ResponseDyn(Request request) {
		// TODO Auto-generated constructor stub
		super(request);
	}

	@Override
	public String getContent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void send() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		this.request = null;
	}
}
