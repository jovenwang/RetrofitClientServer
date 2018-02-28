package com.suven.frame.server.handler;


import com.google.common.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 对Controller中的入参进行转换
 * 具体的转换逻辑由子类实现
 * Created by Alex on 2014/4/28
 */
public abstract class AbstractHandlerArgumentResolver<T> implements HandlerMethodArgumentResolver {

    static Logger logger = LoggerFactory.getLogger(AbstractHandlerArgumentResolver.class);
    final TypeToken<T> type = new TypeToken<T>(getClass()) {};

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        System.err.println("AbstractHandlerArgumentResolver  supportsParameter method 5 ...." );
        return type.getRawType().isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        System.err.println("AbstractHandlerArgumentResolver  resolveArgument method 6 ...." );
        Object result = onResolve(parameter, webRequest, mavContainer, binderFactory);
        if (result == null && parameter.getMethodAnnotation(Nullable.class) == null) {
            logger.error("转换参数失败");
            //todo
//            throw new SystemLogicException(SysMsgEnumType.SYS_PARAM_CHECK);
        }
        return result;
    }

    protected abstract Object onResolve(MethodParameter parameter,
                                        NativeWebRequest webRequest,
                                        ModelAndViewContainer mavContainer,
                                        WebDataBinderFactory binderFactory) throws Exception;




}
