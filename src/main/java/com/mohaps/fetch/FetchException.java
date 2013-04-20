package com.mohaps.fetch;


public class FetchException extends Exception {

	private static final long serialVersionUID = 1L;

	public FetchException() {
		super();
	}
	public FetchException(String message) {
		super(message);
	}
	public FetchException(String message, Throwable error) {
		super(message, error);
	}
	
}
