//package com.suven.frame.server.db;
//
///**
// * Created by joven on 16/9/7.
// */
//public class DataChooseParam {
//
//    private boolean isRotate;
//    private String groupName;
//    private DataSourceEnum dataType; // 入参数为: MASTER 或 SLAVE;
//    private String dataClient;
//
////    public DataChooseParam(String dataClient) {
////        this.isRotate = false;
////        this.dataClient = dataClient;
////    }
//
//    public DataChooseParam(String groupName) {
//        this.groupName = groupName;
//        this.isRotate = true;
//    }
//    public DataChooseParam(String groupName, DataSourceEnum dataType) {
//        this.groupName = groupName;
//        this.dataType = dataType;
//        this.isRotate = true;
//    }
//
//    public boolean isRotate() {
//        return isRotate;
//    }
//
//
//
//    public String getGroupName() {
//        return groupName;
//    }
//
//
//
//    public DataSourceEnum getDataType() {
//        return dataType;
//    }
//
//
//    public String getDataClient() {
//        return dataClient;
//    }
//
//
//
//    @Override
//    public String toString() {
//        return "DataChooseParam{" +
//                "isRotate=" + isRotate +
//                ", groupName='" + groupName + '\'' +
//                ", dataType='" + dataType + '\'' +
//                ", dataClient='" + dataClient + '\'' +
//                '}';
//    }
//}
