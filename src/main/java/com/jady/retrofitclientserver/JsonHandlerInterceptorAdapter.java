package com.jady.retrofitclientserver;

import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class JsonHandlerInterceptorAdapter extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
//        HandlerMethod handlerMethod = (HandlerMethod) handler;
//        Method method = handlerMethod.getMethod();

//        String authorization = request.getHeader(Constants.AUTHORIZATION);
//
//        TokenModel model = manager.getToken(authorization);
//        if (manager.checkToken(model)) {
//            request.setAttribute(Constants.CURRENT_USER_ID, model.getUserId());
//            return true;
//        }
//        if (method.getAnnotation(Authorization.class) != null) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return false;
//        }
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
