package com.avi6.user;

public class DuplicateEmailException extends  RuntimeException {
	
	//중복이메일 검증
	 public DuplicateEmailException(String message) {
	        super(message);
	    }
}
