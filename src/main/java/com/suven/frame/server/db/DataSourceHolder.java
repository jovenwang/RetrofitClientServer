//package com.suven.frame.server.db;
//
//
///**
// * @author ShenHuaJie
// * @version 2016年5月20日 下午3:18:04
// */
//public class DataSourceHolder {
//	// 数据源名称线程池
//	private static final ThreadLocal<DataChooseParam> holder = new ThreadLocal<>();
//
//
////	public static void putDataSource(){
////		DataSourceHolder.putDataSource(new DataChooseParam("gis"));
////	}
//
//
//	public static void putDataSource(DataChooseParam dataChooseParam) {
//
//		holder.set(dataChooseParam);
//	}
//
//	public static DataChooseParam getDataSource() {
//		return holder.get();
//	}
//
//	public static void clear() {
//		holder.remove();
//	}
//}
