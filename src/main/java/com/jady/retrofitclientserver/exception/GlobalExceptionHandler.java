package com.jady.retrofitclientserver.exception;


import com.jady.retrofitclientserver.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;

/**
 * @author vincentdeng
 * 
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
//	@Autowired
//	@Lazy
//	private IResponseBaseMessage resBaesMessage;

	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler({ SystemLogicException.class })
	@ResponseBody
	protected ResponseResult handleBusinessException(SystemLogicException e, HttpServletResponse response) {

		loggerError(e);
		if(e.getError()==null)
			return 	this.write(SysMsgEnumType.SYS_UNKOWNN_FAIL, response);
		else
			return 	this.write(e.getError(), response);
	}

	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler({ ShowcaseException.class })
	@ResponseBody
	protected ResponseResult handleBusinessException(ShowcaseException e, HttpServletResponse response) {
		
		loggerError(e);
		if(e.getError() == null)
			return 	this.write(SysMsgEnumType.SYS_FX_UNKOWNN_FAIL, response);
		else
			return this.write(e.getError(), response);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ IllegalArgumentException.class })
	protected ResponseResult handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) {

		loggerError(e);
		return this.write(SysMsgEnumType.SYS_INVALID_REQUEST, response);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
	protected ResponseResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletResponse response) {

		loggerError(e);
		return  this.write(SysMsgEnumType.SYS_INVALID_REQUEST, response);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ Exception.class })
	protected ResponseResult handleException(Exception e, HttpServletResponse response) {
		
		loggerError(e);
		return this.write(SysMsgEnumType.SYS_UNKOWNN_FAIL, response);
	}

	private ResponseResult write(IMsgEnumType errorMsg, HttpServletResponse response) {
		
//		resBaesMessage.write(errorMsg, response);
		ResponseResult result = new ResponseResult();
		result.setCode(errorMsg.getCode());
		result.setMsg(errorMsg.getMsg());
		return result;
	}
	
	private void loggerError(Exception e){

        String msg = e.getClass().getSimpleName() + ":" + e.getMessage();
		StackTraceElement[] stackTrace = e.getStackTrace();
		for (StackTraceElement stackTraceElement : stackTrace) {
			String className = stackTraceElement.getClassName();
//			if(className.startsWith(GlobalConstants.DEPICT_PACKAGE)) {
                msg += ("\n\t at " + stackTraceElement.toString());
//            }
		}
		if(e instanceof SystemLogicException){
			logger.info(msg);
			return;
		}
        logger.warn(msg);
	}
}
