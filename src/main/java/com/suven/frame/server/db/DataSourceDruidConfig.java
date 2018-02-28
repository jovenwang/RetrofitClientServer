//package com.suven.frame.server.db;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.alibaba.druid.util.JdbcUtils;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//
//
//
//
//
//import java.io.Serializable;
//import java.sql.SQLException;
//
//@ComponentScan
//@PropertySource("classpath:config/datasource.yml")
//@ConfigurationProperties(prefix="spring.datasource.jdbc" )
//
//@Configuration
//public class DataSourceDruidConfig implements Serializable {
//
//    private String url;
//    private String username;
//    private String password;
//   // <!-- 配置初始化大小、最小、最大 -->
//    private int initialSize = 10;
//    private int minIdle = 10;
//    private int maxActive = 100;
//    //<!-- 配置获取连接等待超时的时间 -->
//    private long maxWait = 60000L;
//    //<!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
//    private long timeBetweenEvictionRunsMillis = 60000L;
//    //<!-- 是否仅仅可读 -->
//    private boolean defaultReadOnly = false;
//    //<!-- 合并多个DruidDataSource的监控数据 -->
//    private boolean useGlobalDataSourceStat = true;
//
//    //<!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->
//    private long minEvictableIdleTimeMillis = 300000L;
//    private String validationQuery = "SELECT 'x'";
//    private boolean testWhileIdle  = true;
//    private boolean testOnBorrow = false;
//    private boolean testOnReturn = false;
//
//    private String dbType = "com.alibaba.druid.pool.DruidDataSource";
//    //<!-- 配置监控统计拦截的filters -->
//    private String filters ="wall,stat,slf4j";
//    private String connectionProperties = "druid.stat.mergeSql=true;druid.stat.logSlowSql=true;druid.wall.logViolation=true;druid.wall.throwException=false";
//
//
//
//  public void setDataSource(DruidDataSource dataSource) {
// //  DruidDataSource dataSource = new DruidDataSource();
//   if (null == dataSource ){
//       return;
//   }
//   dataSource.setUrl(url);
//   dataSource.setUsername(username);// 用户名
//   dataSource.setPassword(password);// 密码
//   dataSource.setInitialSize(initialSize);
//   dataSource.setMinIdle(minIdle);
//   dataSource.setMaxActive(maxActive);
//   dataSource.setMaxWait(maxWait);
//   dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
//   dataSource.setDefaultReadOnly(defaultReadOnly);
//   dataSource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);
//   dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
//   dataSource.setValidationQuery(validationQuery);
//   dataSource.setTestWhileIdle(testWhileIdle);
//   dataSource.setTestOnBorrow(testOnBorrow);
//   dataSource.setTestOnReturn(testOnReturn);
//   try {
//    dataSource.setDriverClassName(JdbcUtils.getDriverClassName(dataSource.getUrl()));
//    if (this.dbType == null || this.dbType.length() == 0) {
//      this.dbType = JdbcUtils.getDbType(dataSource.getUrl(), null);
//    }else {
//      dataSource.setDbType(dbType);
//    }
//    dataSource.setFilters(filters);
//    dataSource.setConnectionProperties(connectionProperties);
//   } catch (SQLException e) {
//    e.printStackTrace();
//   }
//  }
//
// public String getUrl() {
//  return url;
// }
//
// public void setUrl(String url) {
//  this.url = url;
// }
//
// public String getUsername() {
//  return username;
// }
//
// public void setUsername(String username) {
//  this.username = username;
// }
//
// public String getPassword() {
//  return password;
// }
//
// public void setPassword(String password) {
//  this.password = password;
// }
//
// public int getInitialSize() {
//  return initialSize;
// }
//
// public void setInitialSize(int initialSize) {
//  this.initialSize = initialSize;
// }
//
// public int getMinIdle() {
//  return minIdle;
// }
//
// public void setMinIdle(int minIdle) {
//  this.minIdle = minIdle;
// }
//
// public int getMaxActive() {
//  return maxActive;
// }
//
// public void setMaxActive(int maxActive) {
//  this.maxActive = maxActive;
// }
//
// public long getMaxWait() {
//  return maxWait;
// }
//
// public void setMaxWait(long maxWait) {
//  this.maxWait = maxWait;
// }
//
// public long getTimeBetweenEvictionRunsMillis() {
//  return timeBetweenEvictionRunsMillis;
// }
//
// public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
//  this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
// }
//
// public boolean isDefaultReadOnly() {
//  return defaultReadOnly;
// }
//
// public void setDefaultReadOnly(boolean defaultReadOnly) {
//  this.defaultReadOnly = defaultReadOnly;
// }
//
// public boolean isUseGlobalDataSourceStat() {
//  return useGlobalDataSourceStat;
// }
//
// public void setUseGlobalDataSourceStat(boolean useGlobalDataSourceStat) {
//  this.useGlobalDataSourceStat = useGlobalDataSourceStat;
// }
//
// public long getMinEvictableIdleTimeMillis() {
//  return minEvictableIdleTimeMillis;
// }
//
// public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
//  this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
// }
//
// public String getValidationQuery() {
//  return validationQuery;
// }
//
// public void setValidationQuery(String validationQuery) {
//  this.validationQuery = validationQuery;
// }
//
// public boolean isTestWhileIdle() {
//  return testWhileIdle;
// }
//
// public void setTestWhileIdle(boolean testWhileIdle) {
//  this.testWhileIdle = testWhileIdle;
// }
//
// public boolean isTestOnBorrow() {
//  return testOnBorrow;
// }
//
// public void setTestOnBorrow(boolean testOnBorrow) {
//  this.testOnBorrow = testOnBorrow;
// }
//
// public boolean isTestOnReturn() {
//  return testOnReturn;
// }
//
// public void setTestOnReturn(boolean testOnReturn) {
//  this.testOnReturn = testOnReturn;
// }
//
// public String getFilters() {
//  return filters;
// }
//
// public void setFilters(String filters) {
//  this.filters = filters;
// }
//
// public String getConnectionProperties() {
//  return connectionProperties;
// }
//
// public void setConnectionProperties(String connectionProperties) {
//  this.connectionProperties = connectionProperties;
// }
//
//
//
//}
