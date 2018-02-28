package com.suven.frame.server.exception;


/**
 * Created by Alex on 2014/9/3
 */
public class BusinessLogicException extends Exception {
	
	private IMsgEnumType error;

	
	public BusinessLogicException(String message) {
		super(message);
	}

	public BusinessLogicException(IMsgEnumType error) {
		this.error = error;
	}

	/**
	 * @return 返回 error。
	 */
	public IMsgEnumType getError() {
		return error;
	}
	
	
}
