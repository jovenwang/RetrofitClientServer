package com.suven.frame.server.Interceptor;

import com.suven.frame.server.NetworkUtils;
import com.suven.frame.server.message.ParamMessage;
import com.suven.frame.server.message.RequestMessage;
import com.suven.frame.server.message.RequestRemote;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class JsonHandlerInterceptorAdapter extends HandlerInterceptorAdapter {
    //AbstractHandlerMethodAdapter  HandlerInterceptorAdapter

    private Logger logger = LoggerFactory.getLogger(getClass());
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //判断是get还是post
        boolean isPostReq = request.getMethod().equals(RequestMethod.POST);


        Map<String, String[]> param = request.getParameterMap();
        if(null == param || (isPostReq && param.size() < 1)){
            logger.warn("[BAD PARAM] size < 1 or is null. url:{}", request.getRequestURI());
            throw new Exception("no request param, request url:"+request.getRequestURI());
        }
        //返回签名
        RequestMessage message = new RequestMessage();
        BeanUtils.copyProperties(param,message);
        if(message == null)
            throw new Exception("request data error, request url:"+request.getRequestURI());
        message.setIp(NetworkUtils.getIpAddress(request));
        message.setUri(request.getRequestURI());
        message.setTimes(System.currentTimeMillis());

        ParamMessage.setRequestMessage(message);


        RequestRemote remote = new RequestRemote();
        remote.setClientIp(message.getIp());
        String url = request.getServletPath();
        if(StringUtils.isBlank(url)){
            url = request.getRequestURI();
        }
        remote.setUrl(url);
//        remote.setSrvMd5Sign(SignParam.getServerSign(param));
        remote.setCliMd5Sign(message.getSign());
        remote.setPostReq(isPostReq);
        if(isPostReq) {
            remote.setIgnoreLogin(true);
        }
        ParamMessage.setRequestRemote(remote);

        System.err.println("JsonHandlerInterceptorAdapter Object preHandle method 1 ...." );
        return true;
    }



    /**
     * This implementation is empty.
     */
    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        System.err.println("JsonHandlerInterceptorAdapter preHandle method ModelAndView 2 ...." );
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.err.println("JsonHandlerInterceptorAdapter afterCompletion method 3 ...." );
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void afterConcurrentHandlingStarted(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.err.println("JsonHandlerInterceptorAdapter afterConcurrentHandlingStarted method 4 ...." );
    }
}
