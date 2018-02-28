package com.suven.frame.server.message;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * 手机访问来源平台信息
 *
 * @author vincentdeng
 */
@Component
public class ParamMessage {

    /**请求参数**/
    private static ThreadLocal<RequestMessage> param = new ThreadLocal<>();
    /**url参数 类型**/
    private static ThreadLocal<RequestRemote> remote = new ThreadLocal<>();



    /**请求参数**/
    public static RequestMessage getRequestMessage() {
        RequestMessage result = param.get();
        return result == null ? new RequestMessage() : result;
    }

    /**请求参数**/
    public static void setRequestMessage(RequestMessage cmssage) {
        if(cmssage == null){
            cmssage = new RequestMessage();
        }
        param.set(cmssage);
    }
    /**url参数 类型**/
    public static RequestRemote getRequestRemote() {
        RequestRemote result = remote.get();
        return result == null ? new RequestRemote() : result;
    }

    /**url参数 类型**/
    public static void setRequestRemote(RequestRemote requestRemote) {
        if(requestRemote == null){
            requestRemote = new RequestRemote();
        }
        remote.set(requestRemote);
    }



    
}


