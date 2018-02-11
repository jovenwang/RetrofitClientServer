package com.jady.retrofitclientserver.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author vincentdeng
 *
 */
public class SystemLogicException extends RuntimeException{

	private static Logger logger = LoggerFactory.getLogger(SystemLogicException.class);

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1349660295724046112L;
	
	private int errorCode;
	
	private String errorMessage;
	

	private IMsgEnumType error;


	public SystemLogicException() {
		super();
	}
	
	public SystemLogicException(int errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		
	}
	
	

	public SystemLogicException(IMsgEnumType error)  {
		super(error.getMsg());
		this.error = error;
		this.errorCode =  error.getCode();
		this.errorMessage = error.getMsg();
	}

    public SystemLogicException(IMsgEnumType error,String errParam)  {
        super(error.getMsg());
        this.error = error;
        this.errorCode =  error.getCode();
        this.errorMessage = String.format(error.getMsg(), errParam);
    }
	
	

	public SystemLogicException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SystemLogicException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemLogicException(String message) {
		super(message);
	}

	public SystemLogicException(Throwable cause) {
		super(cause);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return 返回 error。
	 */

	public IMsgEnumType getError() {

		return error;
	}
}
