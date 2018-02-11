package com.jady.retrofitclientserver.exception;


/**
 * Created by Alex on 2014/9/3
 */
public class ShowcaseException extends Exception {
	
	private IMsgEnumType error;

	
	public ShowcaseException(String message) {
		super(message);
	}

	public ShowcaseException(IMsgEnumType error) {
		this.error = error;
	}

	/**
	 * @return 返回 error。
	 */
	public IMsgEnumType getError() {
		return error;
	}
	
	
}
