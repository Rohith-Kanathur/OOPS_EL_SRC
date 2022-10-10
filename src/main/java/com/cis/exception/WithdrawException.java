package com.cis.exception;

@SuppressWarnings("serial")
public class WithdrawException extends Exception {

		public String toString() {
			return "Your Account Balance Is Low";
		}
}
