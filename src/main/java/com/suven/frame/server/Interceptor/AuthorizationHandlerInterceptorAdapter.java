//package com.jady.retrofitclientserver;
//
//import org.springframework.messaging.handler.HandlerMethod;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.lang.reflect.Method;
//
//@Component
//public class AuthorizationHandlerInterceptorAdapter extends HandlerInterceptorAdapter {
//
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (!(handler instanceof HandlerMethod)) {
//            return true;
//        }
//        HandlerMethod handlerMethod = (HandlerMethod) handler;
//        Method method = handlerMethod.getMethod();
//
////        String authorization = request.getHeader(Constants.AUTHORIZATION);
////
////        TokenModel model = manager.getToken(authorization);
////        if (manager.checkToken(model)) {
////            request.setAttribute(Constants.CURRENT_USER_ID, model.getUserId());
////            return true;
////        }
////        if (method.getAnnotation(Authorization.class) != null) {
////            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
////            return false;
////        }
//        return true;
//    }
//}
