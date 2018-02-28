package com.suven.frame.server.db;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

public enum DataSourceEnum {
    MASTER("write", "主库"),
    SLAVE("read", "从库")
    ;
    private String type;
    private String name;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    DataSourceEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    }