package com.suven.frame.server.db;

/**
 * Created by joven on 16/8/30.
 */
public class DataSourceParamGroup {

    private String groupName;
    private String masterSources;
    private String slaveSources;
    private String masterMethod;
    private String slaveMethod;
   


    public String getMasterSources() {
        return masterSources;
    }

    public void setMasterSources(String masterSources) {
        this.masterSources = masterSources;
    }

    public String getSlaveSources() {
        return slaveSources;
    }

    public void setSlaveSources(String slaveSources) {
        this.slaveSources = slaveSources;
    }

    public String getMasterMethod() {
        return masterMethod;
    }

    public void setMasterMethod(String masterMethod) {
        this.masterMethod = masterMethod;
    }

    public String getSlaveMethod() {
        return slaveMethod;
    }

    public void setSlaveMethod(String slaveMethod) {
        this.slaveMethod = slaveMethod;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


}
