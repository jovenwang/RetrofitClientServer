/**
 * Copyright(c) KuGou-Inc.All Rights Reserved. 
 */
package com.suven.frame.server.handler;


import com.suven.frame.server.exception.SysMsgEnumType;
import com.suven.frame.server.message.ParamMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 * 程序的中文名称。
 * </pre>
 *
 * @author andrewyang yangjianjun@kugou.net
 * @version 1.00.00
 *
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
//@Component
public class OutputMessage extends HttpMessageHandlerConverter {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private  int traceErrorCode= SysMsgEnumType.SYS_UNKOWNN_FAIL.getCode();
	private  String traceMsg = SysMsgEnumType.SYS_UNKOWNN_FAIL.getMsg();

	public static OutputMessage getInstance(HttpServletResponse response) {
		OutputMessage outputMessage = new OutputMessage();
		outputMessage.response = response;
		return outputMessage ;
	}



	@Override
	public String toString() {
		long exeTime = System.currentTimeMillis() - ParamMessage.getRequestMessage().getTimes();
		return "OutputMessage{" +
				"" + ParamMessage.getRequestMessage().toString() +
				" [ code = "+code +
				", msg = " +msg +
				"] "+
				"responseEndTime = " + exeTime
				+"}";
	}



}
