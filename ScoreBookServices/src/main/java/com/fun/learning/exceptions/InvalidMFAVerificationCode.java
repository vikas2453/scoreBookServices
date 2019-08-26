package com.fun.learning.exceptions;

public class InvalidMFAVerificationCode extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidMFAVerificationCode(String message) {
		super(message);
	}
	
	public InvalidMFAVerificationCode(String message, Throwable ex) {
		super(message, ex);
	}


}
