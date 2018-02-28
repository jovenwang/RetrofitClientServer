package com.suven.frame.server.db;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Configuration
@PropertySource("classpath:application.properties")
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,WebMvcAutoConfiguration.class })
public class DataSourceBeanGroup{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment environment;



    @Primary
    @Bean
    @ConfigurationProperties(value = "spring.datasource.druid.master")
    public DataSource dataSourceMaster(){
        DruidDataSource dataSource =  DruidDataSourceBuilder.create().build();
//        super.setDataSource(dataSource);
        return dataSource;
    }
    @Bean
    @ConfigurationProperties("spring.datasource.druid.slave")
    public DataSource dataSourceSlave(){
        DruidDataSource dataSource =  DruidDataSourceBuilder.create().build();
//        super.setDataSource(dataSource);
        return dataSource;
    }



//    /**
//     * 有多少个从库就要配置多少个
//     * @return
//     */
//    @Bean(name = "slaveDataSource")
//    public DataSource readDataSourceOne(){
//        logger.info("-------------------- readDataSourceOne init ---------------------");
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSourceConfig.setDataSource(dataSource);
//        dataSource.setUrl(environment.getProperty("spring.datasource.slave.url"));
//        dataSource.setUsername(environment.getProperty("spring.datasource.slave.username"));// 用户名
//        dataSource.setPassword(environment.getProperty("spring.datasource.slave.password"));// 密码
//        return dataSource;
//    }
//
//    @Bean(name = "slaveDataSource2")
//    public DataSource readDataSourceTwo() {
//        logger.info("-------------------- readDataSourceTwo init ---------------------");
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSourceConfig.setDataSource(dataSource);
//        dataSource.setUrl(environment.getProperty("spring.datasource.slave2.url"));
//        dataSource.setUsername(environment.getProperty("spring.datasource.slave2.username"));// 用户名
//        dataSource.setPassword(environment.getProperty("spring.datasource.slave2.password"));// 密码
//        return dataSource;
//    }
//    @Bean("readDataSources")
//    public List<DataSource> readDataSources(){
//        List<DataSource> dataSources=new ArrayList<>();
//        dataSources.add(readDataSourceOne());
//        dataSources.add(readDataSourceTwo());
//        return dataSources;
//    }
}
