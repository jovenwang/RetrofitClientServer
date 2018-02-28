//package com.suven.frame.server.handler;
//
//import java.io.OutputStream;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletResponse;
//
//import com.alibaba.fastjson.JSON;
//import com.suven.frame.server.data.vo.ResponseResultVO;
//import com.suven.frame.server.exception.IMsgEnumType;
//import com.suven.frame.server.exception.SysMsgEnumType;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
///**
// * 处理 返回值
// * @Description:TODO
// * @author lh
// * @time:2015-8-31 上午11:24:08
// */
//
//@ControllerAdvice
//public class MyResponseBodyAdvice implements ResponseBodyAdvice{
//
//    @Override
//    public Object beforeBodyWrite(Object returnValue, MethodParameter methodParameter,
//            MediaType mediaType, Class clas, ServerHttpRequest serverHttpRequest,
//            ServerHttpResponse serverHttpResponse) {
//        //通过 ServerHttpRequest的实现类ServletServerHttpRequest 获得HttpServletRequest
//        //ServletServerHttpRequest sshr=(ServletServerHttpRequest) serverHttpRequest;
//        //此处获取到request 是为了取到在拦截器里面设置的一个对象 是我项目需要,可以忽略
//        //HttpServletRequest request=   sshr.getServletRequest();
//
//        //将返回值returnValue转成我需要的类型Message<?>  方便统一修改其中的某个属性
////       Messages<?> msg=(Messages<?>) returnValue;
////        //统一修改返回值/响应体
////        msg.setXXX("测试修改返回值");
////        //返回修改后的值
////        return msg;
//
//
//        ResponseResultVO sm = new ResponseResultVO();
//        if (returnValue != null) {
//            sm.setResponse(returnValue);
//        }
//        if(returnValue instanceof IMsgEnumType){
//            IMsgEnumType msgEnumType = (IMsgEnumType) returnValue;
//            sm.setMsg(msgEnumType.getMsg());
//            sm.setCode(msgEnumType.getCode());
//        }
//        HttpHeaders headers = new HttpHeaders();
//        cdnCache(headers);
//        ResponseEntity<ResponseResultVO> entity = new ResponseEntity(sm,headers, HttpStatus.OK);
////        String smJson = JSON.toJSONString(sm);
//        return  entity;
//    }
//    /**
//     * 统一出口
//     * @param message json对象
//     * @param code 状态码
//     * @param msg 状态码的描述
//     */
//    private void writeStream(Object message, int code, String msg,ServerHttpResponse response) {
//
////        this.setTraceLog(code, msg);
//        ResponseResultVO sm = new ResponseResultVO();
//        try {
//            OutputStream output = response.getBody();
//            if(null == output){
//                return ;
//            }
//            if (message != null) {
//
//                sm.setResponse(message);
//            }
//            sm.setMsg(msg);
//            sm.setCode(code);
////            sm.setTimes(System.currentTimeMillis());
//            String smJson = JSON.toJSONString(sm);
////            logger.info("write output Stream response content : [{}] " ,smJson);
////            response.setCharacterEncoding("UTF-8");
////            response.setContentType("application/json;charset=utf-8");
////            cdnCache(response);
//            byte[] jsonString = smJson.getBytes();
//            output.write(jsonString);
//            output.flush();
//            output.close();
//        } catch (RuntimeException e) {
//            e.printStackTrace();
//            throw e;
//        } catch (Exception e1) {
////            logger.warn("response error", e1);
//        }
//    }
//
//
//    private void cdnCache(HttpHeaders headers) {
////        MultiValueMap<String,Object> cdnMap = new MultiValueMap();
//        headers.set("Access-Control-Allow-Origin","*");
//        int cdnTime = 2;// Cdn.get(this.getParamMessage().getUri());
//        if (cdnTime == 0) {
//            headers.set("Cache-Control", "no-cache");
//        } else {
//            headers.set("Cache-Control", "max-age=" + cdnTime);
//            headers.set("Last-Modified", System.currentTimeMillis()+"");
//            headers.set("Date", System.currentTimeMillis()+"");
//            headers.set("Expires", System.currentTimeMillis() + (cdnTime*1000)+"");
//        }
//    }
//
//    private void cdnCache(HttpServletResponse res) {
//        res.setHeader("Access-Control-Allow-Origin","*");
//        int cdnTime = 2;// Cdn.get(this.getParamMessage().getUri());
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
//    @Override
//    public boolean supports(MethodParameter methodParameter, Class clas) {
////        //获取当前处理请求的controller的方法
////        String methodName=methodParameter.getMethod().getName();
////        // 不拦截/不需要处理返回值 的方法
////        String method= "loginCheck"; //如登录
////        //不拦截
////        return !method.equals(methodName);
//        return  true;
//    }
//}