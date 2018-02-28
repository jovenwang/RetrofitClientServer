package com.suven.frame.server.filter;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * druid 配置.
 *
 * 这样的方式不需要添加注解：@ServletComponentScan
 * @author Administrator
 *
 */

@Configuration
@ConfigurationProperties(prefix = "server.datasource.druid")
public class DataSourceDruidFilter {

        private String url;
        private String urlPatterns;
        private String allow ;
        private String deny ;
        private String loginUsername ;
        private String loginPassword ;
        private boolean resetEnable;
        private String exclusions;


        /**
         * 注册一个StatViewServlet
         * @return
         */

        @Bean
        public ServletRegistrationBean druidStatViewServlet(){
                //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
                ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
                        new StatViewServlet(),getUrl());
                //添加初始化参数：initParams
                //白名单：
                servletRegistrationBean.addInitParameter("allow",getAllow());
                //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
                servletRegistrationBean.addInitParameter("deny",getDeny());
                //登录查看信息的账号密码.
                servletRegistrationBean.addInitParameter("loginUsername",getLoginUsername());
                servletRegistrationBean.addInitParameter("loginPassword",getLoginPassword());
                //是否能够重置数据.
                servletRegistrationBean.addInitParameter("resetEnable",String.valueOf(isResetEnable()));
                return servletRegistrationBean;

        }



        /**
         * 注册一个：filterRegistrationBean
         * @return
         */
        @Bean
        public FilterRegistrationBean druidStatFilter(){
                FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
                //添加过滤规则.
                filterRegistrationBean.addUrlPatterns(getUrlPatterns());
                //添加不需要忽略的格式信息.
                filterRegistrationBean.addInitParameter("exclusions",getExclusions());
                return filterRegistrationBean;
        }


        public String getUrl() {
                return url;
        }

        public void setUrl(String url) {
                this.url = url;
        }

        public String getUrlPatterns() {
                return urlPatterns;
        }

        public void setUrlPatterns(String urlPatterns) {
                this.urlPatterns = urlPatterns;
        }

        public String getAllow() {
                return allow;
        }

        public void setAllow(String allow) {
                this.allow = allow;
        }

        public String getDeny() {
                return deny;
        }

        public void setDeny(String deny) {
                this.deny = deny;
        }

        public String getLoginUsername() {
                return loginUsername;
        }

        public void setLoginUsername(String loginUsername) {
                this.loginUsername = loginUsername;
        }

        public String getLoginPassword() {
                return loginPassword;
        }

        public void setLoginPassword(String loginPassword) {
                this.loginPassword = loginPassword;
        }

        public boolean isResetEnable() {
                return resetEnable;
        }

        public void setResetEnable(boolean resetEnable) {
                this.resetEnable = resetEnable;
        }

        public String getExclusions() {
                return exclusions;
        }

        public void setExclusions(String exclusions) {
                this.exclusions = exclusions;
        }
}