//package com.suven.frame.server.Interceptor;
//
//
//import com.alibaba.fastjson.JSON;
//import com.suven.frame.server.data.vo.ResponseResultVO;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Map;
//
//@Component
//public class JsonReturnHandlerInterceptorConverter  {
//	private static Logger logger = LoggerFactory.getLogger(JsonReturnHandlerInterceptorConverter.class);
//
//
//	private Map<String, String[]>  body;
//
//    private JsonReturnHandlerInterceptorConverter(){}
//
//
//    public static HttpMessageHandlerConverter getInstance(HttpServletResponse res) {
////        HttpMessageHandlerConverter message = new JsonReturnHandlerInterceptorConverter();
//////        mMessage.body = ParamMessage.getParameterMap();
////        message.res = res;
////		return message ;
//        return null;
//	}
//
//    /**
//     * 统一出口
//     * @param message json对象
//     * @param code 状态码
//     * @param msg 状态码的描述
//     */
//	private void writeStream(Object message, int code, String msg) {
//
//    	this.setTraceLog(code, msg);
//    	ResponseResultVO sm = new ResponseResultVO();
//        try {
//            ServletOutputStream output = res.getOutputStream();
//            if(null == output){
//            	return ;
//            }
//            if (message != null) {
//
//            	sm.setResponse(message);
//            }
//            sm.setMsg(msg);
//            sm.setCode(code);
//            String smJson = JSON.toJSONString(sm);
//            logger.info("write output Stream response content : [{}] " ,smJson);
//            res.setCharacterEncoding("UTF-8");
//            res.setContentType("application/json;charset=utf-8");
//            cdnCache(res);
//            byte[] jsonString = smJson.getBytes();
//            output.write(jsonString);
//            output.flush();
//            output.close();
//        } catch (RuntimeException e) {
//            e.printStackTrace();
//            throw e;
//        } catch (Exception e1) {
//            logger.warn("response error", e1);
//        }
//    }
//
//	public void writeGzip(Object message) {
//
//		this.setTraceLog(code, msg);
//        ResponseResultVO sm = new ResponseResultVO();
//        try {
//            ServletOutputStream output = res.getOutputStream();
//
//            if (null!=message) {
//                  sm.setResponse(message);
//            }
//            sm.setMsg(msg);
//            sm.setCode(code);
//            sm.setTimes(System.currentTimeMillis());
//            String smJson = JSON.toJSONString(sm);
//            res.setCharacterEncoding("UTF-8");
//            res.setContentType("application/json");
//            cdnCache(res);
//            byte[] jsonString = smJson.getBytes();
//            output.write(jsonString);
//            output.flush();
//            output.close();
//        } catch (RuntimeException e) {
//            throw e;
//        } catch (Exception e1) {
//            logger.warn("response error", e1);
//        }
//    }
//
//    private void cdnCache(HttpServletResponse res) {
//        res.setHeader("Access-Control-Allow-Origin","*");
//        int cdnTime = 1;// Cdn.get(this.getParamMessage().getUri());
//        if (cdnTime == 0) {
//            res.addHeader("Cache-Control", "no-cache");
//        } else {
//            res.setHeader("Cache-Control", "max-age=" + cdnTime);
//            res.addDateHeader("Last-Modified", System.currentTimeMillis());
//            res.addDateHeader("Date", System.currentTimeMillis());
//            res.addDateHeader("Expires", System.currentTimeMillis() + (cdnTime*1000));
//        }
//    }
//
//
//	@Override
//	protected void writeMsgByType() {
//		this.writeStream(null, getCode(), getMsg());
//	}
//
//
//	@Override
//	protected void writeBodyByType(Object data) {
//		this.writeStream(data, getCode(), getMsg());
//	}
//
//
//
//
//}
