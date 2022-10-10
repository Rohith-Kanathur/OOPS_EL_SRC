package com.cis.exception;

@SuppressWarnings("serial")
public class InvalidPasswordException extends Exception{
	
	public String toString() {
		return "Password Invalid!!";
	}
}
