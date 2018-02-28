package com.suven.frame.server.message;

/**
 * @author skyyu
 *
 */
public class RequestBaseMessage {

    private int version;        //版本
    private int platform ;    //平台
    private String sign;        //会话密码
 
    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }
    public int getPlatform() {
        return platform;
    }
    public void setPlatform(int platform) {
        this.platform = platform;
    }
    public String getSign() {
        return sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    

}
