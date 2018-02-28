package com.suven.frame.server.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jetty.lowResources")
public class JettyLowResourceMonitorSettings {
    // all durations in milliseconds

    private int period = 1000;
    private int idleTimeout = 200;
    private boolean monitorThreads = true;
    private int maxConnections = 0;
    private int maxMemory;
    private int maxLowResourcesTime = 5000;

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(int idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public boolean isMonitorThreads() {
        return monitorThreads;
    }

    public void setMonitorThreads(boolean monitorThreads) {
        this.monitorThreads = monitorThreads;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public int getMaxMemory() {
        return maxMemory;
    }

    public void setMaxMemory(int maxMemory) {
        this.maxMemory = maxMemory;
    }

    public int getMaxLowResourcesTime() {
        return maxLowResourcesTime;
    }

    public void setMaxLowResourcesTime(int maxLowResourcesTime) {
        this.maxLowResourcesTime = maxLowResourcesTime;
    }
}