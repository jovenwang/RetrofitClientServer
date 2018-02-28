//package com.suven.frame.server.Interceptor;
//
//import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
//import org.springframework.http.HttpOutputMessage;
//import org.springframework.http.converter.HttpMessageNotWritableException;
//
//import java.io.IOException;
//
//public class JsonReturnInterceptorConverter extends FastJsonHttpMessageConverter {
//
//    @Override
//    protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
//            throws IOException, HttpMessageNotWritableException {
//
//        super.writeInternal(obj,outputMessage);
//    }
//
//    @SuppressWarnings("unchecked")
//    public  void hdlBeforeRtn(Object resData, String... errParam){
//        if(errParam == null || errParam.length <=0)
//            return;
////        String msg = String.format(super.g, errParam);
////        super.setMsg(msg);
//    }
//}
