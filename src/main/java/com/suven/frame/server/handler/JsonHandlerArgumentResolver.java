package com.suven.frame.server.handler;


import com.suven.frame.server.JsonParse;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 转换json Message 对象
 * @author alex
 */
@Component
public class JsonHandlerArgumentResolver extends AbstractHandlerArgumentResolver<JsonParse> {

    @Override
    protected Object onResolve(MethodParameter parameter,
                               NativeWebRequest webRequest,
                               ModelAndViewContainer mavContainer,
                               WebDataBinderFactory binderFactory)  throws Exception {

        mavContainer.setRequestHandled(true);
       return  this.jsonParseBody(webRequest.getParameterMap(),parameter.getParameterType());
    }


    @SuppressWarnings("unchecked")
    public <T> T jsonParseBody(Map body, Class<T> clazz) {
        if(body == null) {
            return null;
        }
        T t = null;
        try {
            Method method = clazz.getMethod("parseFrom", Map.class,Class.class);
            t = (T) method.invoke(clazz,body,clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}