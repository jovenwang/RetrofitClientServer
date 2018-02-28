package com.suven.frame.server.handler;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：对加了ReturnHandlerArgumentResolver注解的方法进行字段过滤处理
 *

 * @author suven.wang ReturnHandlerArgumentResolver
 */
@Component
public class ReturnHandlerArgumentResolver extends HttpMessageHandlerConverter implements HandlerMethodReturnValueHandler{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return methodParameter.hasMethodAnnotation(ResponseJson.class);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter paramter,
            ModelAndViewContainer container, NativeWebRequest request) throws Exception {
        container.setRequestHandled(true);
//        JsonFilterSerializer serializer = new JsonFilterSerializer();
        if(paramter.hasMethodAnnotation(ResponseJson.class)) {
            //如果有JsonFieldFilter注解，则过滤返回的对象returnObject
            ResponseJson jsonFilter = paramter.getMethodAnnotation(ResponseJson.class);
        }
        HttpServletResponse response = request.getNativeResponse(HttpServletResponse.class);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        this.response = response;
        this.write(returnValue);
    }






}