/**
 * Copyright(c) KuGou-Inc.All Rights Reserved. 
 */
package com.suven.frame.server.handler;


import com.alibaba.fastjson.JSON;
import com.suven.frame.server.data.vo.ResponseResultVO;
import com.suven.frame.server.exception.IMsgEnumType;
import com.suven.frame.server.exception.SysMsgEnumType;
import com.suven.frame.server.message.ParamMessage;
import com.suven.frame.server.message.RequestMessage;
import com.suven.frame.server.message.RequestRemote;
import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author suven  pf_qq@163.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:  v1.0.0    修改人： suven  修改日期:  20160110   修改内容: 添加异常信息参数化 
 * </pre>
 */
public abstract class HttpMessageHandlerConverter {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private  int traceErrorCode = SysMsgEnumType.SYS_UNKOWNN_FAIL.getCode();
	private  String traceMsg = SysMsgEnumType.SYS_UNKOWNN_FAIL.getMsg();
	
	/**
	 * 返回码。
	 */
	public int code;
	/**
	 * 返回信息。
	 */
	public String msg = "";

	protected HttpServletResponse response;
	

	/**
	 * 返回前端数据前处理。
	 * @param errParam
	 */
	@SuppressWarnings("unchecked")
	public  void returnErrorBeforeConverter(String... errParam){
		if(errParam == null || errParam.length <=0)
			return;
		String msg = String.format(this.getMsg(), errParam);
		this.setMsg(msg);
	}
	
	/**
	 * 写入客户端结果/消息。
	 * @param resData
	 */
	public void write(Object resData, String... errParam)  {

		//返回转换后的规范的错误码信息;
		if(resData != null && (resData instanceof IMsgEnumType)){//如果是消息类型
			setMsgByEnum(resData);
			returnErrorBeforeConverter(errParam);
			writeMsgByType();
			printErrorLog();
		}else{//为数据
			setMsgByEnum(SysMsgEnumType.SYS_SUCCESS);
			writeBodyByType(resData);
		}
	}
	
	private void printErrorLog() {
		RequestMessage message = ParamMessage.getRequestMessage();
		RequestRemote remote = ParamMessage.getRequestRemote();
		logger.warn("IMsgEnumType Exception request url is [{}], code:={},  msg:={}, request ip is [{}], RequestMessage is [{}], ",
				remote.getUrl(), this.code, this.msg, remote.getClientIp(),
				message.toString());
	}

	protected void writeMsgByType() {
		this.writeStream(null, getCode(), getMsg());
	}

	protected void writeBodyByType(Object data) {
		if(null == data){
			this.writeStream(null, getCode(), getMsg());
			return;
		}
		if(!ClassUtils.isPrimitiveOrWrapper(data.getClass())){
			writeStream(data,code,msg);
			return;
		}
		Map<String,Object> map = new HashMap<>();
		if(data instanceof  Boolean){
			int value = 0;
			if((Boolean)data){
				value = 1;
			}
			map.put("result", value);
		}else {
			map.put("Id", data);
		}
		writeStream(map,code,msg);
	}


	public void writeSuccess(){
		setMsgByEnum(SysMsgEnumType.SYS_SUCCESS);
		writeMsgByType();
	}


	/**
	 * 解析出返回代码及返回信息。
	 * @param errorType
	 */
	protected void setMsgByEnum(Object errorType) {
		try {
			if(errorType instanceof IMsgEnumType){
				IMsgEnumType warnType = (IMsgEnumType)errorType;
				code = warnType.getCode();
				msg =  warnType.getMsg();
			}
		} catch (Exception e) {
			logger.warn("IMsgEnumType Exception : ", e);
			code = SysMsgEnumType.SYS_UNKOWNN_FAIL.getCode();
			msg = SysMsgEnumType.SYS_UNKOWNN_FAIL.getMsg();
		}
	}
	private void setCdnCache(HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.setHeader("Access-Control-Allow-Origin","*");
		int cdnTime = 1;// Cdn.get(this.getParamMessage().getUri());
		if (cdnTime == 0) {
			response.addHeader("Cache-Control", "no-cache");
		} else {
			response.setHeader("Cache-Control", "max-age=" + cdnTime);
			response.addDateHeader("Last-Modified", System.currentTimeMillis());
			response.addDateHeader("Date", System.currentTimeMillis());
			response.addDateHeader("Expires", System.currentTimeMillis() + (cdnTime*1000));
		}
	}

	/**
	 * 统一出口
	 * @param message json对象
	 * @param code 状态码
	 * @param msg 状态码的描述
	 */
	protected void writeStream(Object message, int code, String msg) {

		this.setTraceLog(code, msg);
		ResponseResultVO sm = new ResponseResultVO();
		try {
			ServletOutputStream output = response.getOutputStream();
			if(null == output){
				return ;
			}
			if (message != null) {
				sm.setResponse(message);
			}
			sm.setMsg(msg);
			sm.setCode(code);
			String smJson = JSON.toJSONString(sm);
			logger.info("write output Stream response content : [{}] " ,smJson);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=utf-8");

			this.setCdnCache(response);
			byte[] jsonString = smJson.getBytes();
			output.write(jsonString);
			output.flush();
			output.close();
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e1) {
			logger.warn("response error", e1);
		}
	}

	public void writeGzip(Object message) {

		this.setTraceLog(code, msg);
		ResponseResultVO sm = new ResponseResultVO();
		try {
			ServletOutputStream output = response.getOutputStream();

			if (null!=message) {
				sm.setResponse(message);
			}
			sm.setMsg(msg);
			sm.setCode(code);
			sm.setTimes(System.currentTimeMillis());
			String smJson = JSON.toJSONString(sm);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			this.setCdnCache(response);

			byte[] jsonString = smJson.getBytes();
			output.write(jsonString);
			output.flush();
			output.close();
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e1) {
			logger.warn("response error", e1);
		}
	}
	/**
	 * 状态码记录，可以用于日志输出等
	 * @param code
	 * @param msg
	 */
	public void setTraceLog(int code, String msg){
		this.traceErrorCode = code;
		this.traceMsg = msg;
	}
	/**
	 * @return 返回 code。
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code 设置 code。
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return 返回 msg。
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg 设置 msg。
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
