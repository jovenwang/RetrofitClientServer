package com.jady.retrofitclientserver;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jetty.server")
// @see http://eclipse.org/jetty/documentation/current/limit-load.html
// @see http://eclipse.org/jetty/documentation/current/embedding-jetty.html#d0e19129
public class JettyServerPortSettings {
    private int port = 8080;
    private int gcPort2 = 9090;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getGcPort2() {
        return gcPort2;
    }

    public void setGcPort2(int gcPort2) {
        this.gcPort2 = gcPort2;
    }
}
