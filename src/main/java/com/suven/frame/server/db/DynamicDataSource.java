//package com.suven.frame.server.db;
//
//import com.alibaba.fastjson.JSON;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//
//import javax.annotation.PostConstruct;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * 获取数据源
// *
// * @author ShenHuaJie
// * @version 2016年5月20日 下午3:17:16
// */
//public class DynamicDataSource extends AbstractRoutingDataSource {
//
//	private final Logger logger = LoggerFactory.getLogger(getClass());
//
//	private final static int DATA_SOURCE_INIT_HEAT_TIME = 1000;
//	private int DATA_SOURCE_HEAT_TIME =  60;
//	private static boolean DATA_SOURCE_HEAT_STATE = false;
//
//	public static Map<String, List<String>> methodType = new HashMap<>();
//	public static Map<String, Map<String,List<String>>> dataSourceGroupMap = new HashMap<>();
//	private static Map<String, Map<String,List<String>>> dataSourceGroupMapBack = new HashMap<>();
//	private ConcurrentHashMap<String,AtomicInteger> currentMap = new ConcurrentHashMap<>();
//
//
//
//	public void setDataSourcesGroup(Set<DataSourceParamGroup> dataSourcesGroup) {
//		if (null == dataSourcesGroup || dataSourcesGroup.isEmpty()){
//			throw new IllegalArgumentException("Property 'dataSourcesGroup' is null");
//		}
//		for (DataSourceParamGroup group : dataSourcesGroup) {
//			Map<String,List<String>> map = dataSourceGroupMap.get(group.getGroupName());
//			if(map == null){
//				map = new HashMap<>();
//				dataSourceGroupMap.put(group.getGroupName(),map);
//			}
//			String master = DataSourceEnum.MASTER.name();
//			String slave = DataSourceEnum.SLAVE.name();
//			map.put(master,strToList(group.getMasterSources()));
//			map.put(slave,strToList(group.getMasterSources()));
//
//			methodType.put(master,strToList(group.getMasterMethod()));
//			methodType.put(slave,strToList(group.getSlaveMethod()));
//		}
//		dataSourceGroupMapBack.putAll(dataSourceGroupMap);
////		DataSourceHolder.putDataSource("show","SLAVE");
////		Object client = determineCurrentLookupKey();
////		boolean isboolean = dataSourceConnCheck(client.toString());
////		new DataSourceGroupStatsThread("datasourcegroupstats").run();
//
//	}
//
//	public void setDataSourceHeatTime(int dataSourceHeatTime){
//		if(dataSourceHeatTime > 0 ){
//			this.DATA_SOURCE_HEAT_TIME = dataSourceHeatTime * DATA_SOURCE_INIT_HEAT_TIME;
//		}else {
//			DATA_SOURCE_HEAT_TIME *=  DATA_SOURCE_INIT_HEAT_TIME;
//		}
//	}
//
//	public void setDataSourceHeatState(int dataSourceHeatState){
//		if(dataSourceHeatState > 0 ){
//			DATA_SOURCE_HEAT_STATE = true;
//		}
//	}
//
//	private List<String> strToList(String splitStr){
//		String[] str = splitStr.split(",|;");
//		List<String> list = new ArrayList<>();
//		for (String s : str){
//			list.add(s.trim());
//		}
//		return list;
//	}
//
//	// 获取数据源名称,采用轮询的方式实现
//	protected Object determineCurrentLookupKey() {
//		DataChooseParam dataSource = DataSourceHolder.getDataSource();
//		if(null == dataSource  ){
//			throw new IllegalArgumentException("Property 'determineCurrentLookupKey' is DataSourceHolder.getDataSource() not  dataSource:["+ dataSource +"]");
//		}
//		Map<String,List<String>>  map = dataSourceGroupMap.get(dataSource.getGroupName());
//		String dataType = "MASTER";
//		DataSourceEnum dataEnum = dataSource.getDataType();
//		if(null != dataEnum){
//			dataType = dataEnum.name();
//		}
//		if(!dataSource.isRotate()){//不使用轮询,直接返回指定的数据库连接名称;
//			return  dataSource.getDataClient();
//		}else {
//			List list = map.get(dataType);
//			if (null == list || list.isEmpty()) {
//				throw new IllegalArgumentException("Property 'determineCurrentLookupKey' is DataSourceHolder.map.get(dataType) list isEmpty or null ");
//			}
//			int size = list.size();
//			if (size == 1) {
//				return list.get(0);
//			} else {
//				AtomicInteger counter = currentMap.get(dataType);
//				if (counter == null) {
//					counter = new AtomicInteger(0);
//					currentMap.put(dataType, counter);
//				} else {
//					if (counter.incrementAndGet() >= size) {
//						counter.set(0);
//					}
//				}
//				return list.get(counter.intValue());
//
//			}
//		}
//
//	}
//
//	/**
//	 * 检查数据源是否可用
//	 * @param dataSourceCliect
//	 * @return
//     */
//	public boolean dataSourceConnCheck(String dataSourceCliect){
//		logger.info(" ChooseDataSource to dataSourceConnCheck dataSourceCliect={}" ,dataSourceCliect);
//		int repeatCount = 5;
//		Connection conn = null;
//		try {
//			this.afterPropertiesSet();
//			DataSourceHolder.putDataSource(new DataChooseParam(dataSourceCliect));
//			DruidDataSource dataSource = (DruidDataSource) this.determineTargetDataSource();
//			if(null == dataSource ){
//				return false;
//			}
//			conn = dataSource.getConnection();
//			for (int i = 0 ; i < repeatCount ; i++){
//				try {
//					dataSource.validateConnection(conn);
//					return true;//验证没有异常,返回正常结束重试
//				}catch (SQLException e){
//					logger.error(" ChooseDataSource to dataSourceConnCheck dataSourceCliect=[{}], SQLException=[]" ,dataSourceCliect, e);
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e1) {
//						e1.printStackTrace();
//					}
//				}
//			}//如果循环没有命中,则认为是失败的
//			return false;
//
//		} catch (SQLException e) {
//			return false;
//		}finally {
//			if(null !=conn){
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//
//	}
//
//	/**
//	 * 心跳监控数据源是否可用
//	 */
//	public class DataSourceGroupHeatThread extends Thread {
//
//		public DataSourceGroupHeatThread(String name){
//			super(name);
//			this.setDaemon(true);
//		}
//
//		public void run() {
//			try {
//
//				L : for (;;) {
//					logger.info(" ChooseDataSource to DataSourceGroupHeatThread start, DATA_SOURCE_HEAT_STATE={}" ,DATA_SOURCE_HEAT_STATE);
//					if(!DATA_SOURCE_HEAT_STATE){
//						break L;
//					}
//					try {
//						logger.info(" DataSourceGroupHeatThread run  dataSourceGroupMapBack size ={}" ,dataSourceGroupMapBack.size());
//						for (Map.Entry<String,Map<String,List<String>>> group :dataSourceGroupMapBack.entrySet()) {
//							String groupName = group.getKey();
//							Map<String,List<String>> map = group.getValue();
//							logger.info(" DataSourceGroupHeatThread run  groupName={}, client map={}" ,groupName, JSON.toJSON(map));
//							for (Map.Entry<String,List<String>> client : map.entrySet()) {
//								String masterSlavekey = client.getKey();
//								List<String> datasourceList = client.getValue();
//								logger.info(" DataSourceGroupHeatThread while 2  masterSlavekey={}, datasourceList={}" ,groupName, JSON.toJSON(datasourceList));
//								for (String dataSourceCliect : datasourceList){//对应数据库的每个连接检查;
//									//验证 dataSourceConnCheck 对应的数据库 是否可以正常;
//									boolean isconnect = dataSourceConnCheck(dataSourceCliect);
//									if(isconnect) {
//										this.dataSourceGroupMapCheck(groupName, masterSlavekey, dataSourceCliect, isconnect);
//									}
//
//								}
//							}
//						}
//					} catch (Exception e) {
//						logger.error("DataSourceGroupStatsThread error", e);
//					}
//
//					Thread.sleep(DATA_SOURCE_INIT_HEAT_TIME);
//
//				}
//			} catch (Exception e) {
//				logger.error("DataSourceGroupHeatThread error", e);
//			}
//		}
//
//		/**
//		 * 缓存dataSourceGroupMap
//		 * @param groupName
//		 * @param masterSlavekey
//		 * @param dataSourceKey
//         * @param isconnect
//         */
//		public void dataSourceGroupMapCheck(String groupName, String masterSlavekey, String dataSourceKey, boolean isconnect){
//			Map<String,List<String>> lineGroupMap = dataSourceGroupMap.get(groupName);
//			if(null != lineGroupMap){
//				if(isconnect){
//					List<String> lineSourceList = lineGroupMap.get(masterSlavekey);
//					logger.info(" DataSourceGroupHeatThread dataSourceGroupMapCheck  lineGroupMap for  dataSourceKey={} masterSlavekey={} "
//							, dataSourceKey ,masterSlavekey);
//					if(null == lineSourceList){
//						lineSourceList = new ArrayList();
//						lineGroupMap.put(masterSlavekey,lineSourceList);
//					}if (!lineSourceList.contains(dataSourceKey)){//如果数据库结点可用时,并不在集合时,增加集合中;
//						lineSourceList.add(dataSourceKey);
//					}
//				}else{//删除坏结点;
//					logger.info(" DataSourceGroupHeatThread dataSourceGroupMapCheck  lineGroupMap to remove  dataSourceKey={}" ,dataSourceKey);
//					List<String> lineSourceList = lineGroupMap.get(masterSlavekey);
//					if(null != lineSourceList && lineSourceList.contains(dataSourceKey)){
//						lineSourceList.remove(dataSourceKey);
//					}
//
//
//				}
//			}
//		}
//
//
//	}
//
////	@PostConstruct
//	public void initCache(){
//		if(DATA_SOURCE_HEAT_STATE && !Env.isDev()) {
//			new DataSourceGroupHeatThread("DataSourceGroupHeatThread").run();
//		}
////		Runtime.getRuntime().addShutdownHook(new DataSourceGroupHeatThread("DataSourceGroupHeatThread"));
//	}
//
//	public static class  Env{
//
//		public static  boolean isDev(){
//			return true;
//		}
//	}
//
//}
