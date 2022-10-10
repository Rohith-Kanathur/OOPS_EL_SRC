package com.cis.exception;

@SuppressWarnings("serial")
public class InvalidUserIDException extends Exception {
	
	public String toString() {
		return "User ID Invalid!!";
	}
}
