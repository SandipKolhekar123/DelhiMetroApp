package com.mobisoft.exceptions;

public class RequestRejectedExceptions extends RuntimeException {

	String path;

	public RequestRejectedExceptions(String path) {
		super(String.format("The request was rejected because the URL contained a potentially malicious String: %s", path));
		this.path = path;
	}
}
