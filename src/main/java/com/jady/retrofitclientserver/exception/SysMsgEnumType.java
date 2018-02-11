package com.jady.retrofitclientserver.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title: MsgEnumType.java
 * @author Joven.wang
 * @date 2015年2月10日
 * @version V1.0
 * @Description: TODO(说明) 此类封装了,从第三方平台的返回code,转换成业务逻辑用户错误提示信息; eg:
 *               SYS_CODE_为前缀,后缀编码为第三方反回code信息,括号为提前端编码和提示信息;
 */

public enum SysMsgEnumType implements IMsgEnumType{
	
	/** 编码规范说明: 1,1 01 001 错误编码的意思,第一位(1)代码是系统,第2位是功能码
	 第3，4位( eg: 01用户信息模块, 02礼物模块),第5，6，7号(该模块用到的返回提示用号的编码code)
	1. 1 00 01 - 100 99(共99个系统级别) 是手端(看7.0 app 2.0) 应用后台系统级别返回来的统一规范编码
	 1.第1位表示软件平台：1
	 2.第2位表示功能类型：1.弹窗提示，2.软接连，3.跳转url
	 3.第3.4位表示模块分类名称：
	 4.第5，6，7位表示错误序列号；
	 */
	
	SYS_SUCCESS(0, ""),
	/** 系统返回错误码 10000~10099*/
	SYS_UNKOWNN_FAIL(1100000, "操作失败!"), 
	SYS_TOKEN_NULL(1100001, "请重新登录"), 
	SYS_INVALID_REQUEST(1100002,"无效请求 主营业务:“ %s “"),
	SYS_FX_UNKOWNN_FAIL(1100003,"APPID 为0"),
	

	;
	
	private int code;
	private String msg;
	
	private static Map<String, IMsgEnumType> msgTypeMap = new HashMap<String, IMsgEnumType>();
	private static Map<Object, Integer> checkTypeMap = null;
	
	static {
		checkTypeMap  = new HashMap<>();
		
		for(SysMsgEnumType msgType : values()) {
			msgTypeMap.put(msgType.name(), msgType);
			if(null != checkTypeMap.get(msgType.getCode())){
				throw new RuntimeException(" SysMsgEnumType is type double :" +msgType.getCode()+" and "+ msgType.getMsg() +"is exist ");
			}
			checkTypeMap.put(msgType.getCode(), 0);
				
		}
		checkTypeMap = null;
	}

	SysMsgEnumType(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	
	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public IMsgEnumType setErrorMsg(String... errorMsg){
		if(errorMsg == null || errorMsg.length <=0)
			return this;
		String error = String.format(this.msg, errorMsg);
		this.msg = error;
		return this;
	}
	
	


	@Override
	public String getProject() {
		return null;
	}


	
}
