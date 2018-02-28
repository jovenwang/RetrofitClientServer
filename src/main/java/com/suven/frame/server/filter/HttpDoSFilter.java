package com.suven.frame.server.filter;


import com.suven.frame.server.message.ParamMessage;
import com.suven.frame.server.message.RequestMessage;
import com.suven.frame.server.message.RequestRemote;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.servlets.DoSFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Title: ShowDoSFilter.java
 * @author Joven.wang
 * @date 2015年2月8日
 * @version V1.0
 * @Description: TODO(说明)
 */

public class HttpDoSFilter extends DoSFilter {

	private final static Logger logger = LoggerFactory.getLogger(HttpDoSFilter.class);

	private static  boolean ENV_IS_SERVER =  true ;//Env.isDev();
	
	/**
	 * 初始化相关配置DosFilter 相关配置参数;
	 * @throws ServletException
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException{
		super.init(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
		try {
			
			HttpServletRequest servletRequest =  (HttpServletRequest) request;
			HttpServletResponse servletResponse = (HttpServletResponse) response;
			RequestRemote requestRemote = ParamMessage.getRequestRemote();
			if(!requestRemote.isIgnoreLogin() || requestRemote.isCdn() || requestRemote.isWhite()){
				  logger.info("Http request's not post type, skin the dos filter. request url is [{}], request ip is [{}]",
						  servletRequest.getRequestURI(), requestRemote.getClientIp());
				  filterChain.doFilter(request, response);
				  return;
			}
			super.doFilter(request, response, filterChain);
			if (servletResponse.getStatus() == HttpServletResponse.SC_SERVICE_UNAVAILABLE) {
				RequestMessage message = ParamMessage.getRequestMessage();
				logger.warn("extractUserId := " + message.getPassportId() + ",device:={},token:={},  ServletResponse.SC_SERVICE_CODE:= "+ servletResponse.getStatus()+
						", request url is [{}], request ip is [{}], Platform is [{}], channel is [{}], sysVersion is [{}], version is [{}]",
						message.getDevice(),message.getAccessToken(),servletRequest.getRequestURI(), requestRemote.getClientIp(),
						message.getPlatform(), message.getChannel(), message.getSysVersion(), message.getVersion());
			}
		} catch (Exception e) {
			logger.warn("doFilter catch Exception:=", e);
		}

	}

	/****
	 * 重写实现具体的拦截
	 */
	@Override
	protected String extractUserId(ServletRequest request) {
		HttpServletRequest dosRequest = (HttpServletRequest) request;
		RequestMessage message = ParamMessage.getRequestMessage();
		VerifyDosStatus dos = new VerifyDosStatus();
		dos.setClientIp(ParamMessage.getRequestRemote().getClientIp());
		dos.setDevice(message.getDevice());
		dos.setToken(message.getAccessToken());
		dos.setPassportId(message.getPassportId());
		dos.setUrl(((HttpServletRequest) request).getRequestURI());
		String intercept = checkDos(dos);
		return intercept;
	}

	/**
	 * 拦截类型,1.用户ID,2.token,3到设备码,4.用户IP(不是公司内部);
	 * 
	 * @return
	 */
	public String checkDos(VerifyDosStatus dos) {
		if(ENV_IS_SERVER)
			return getRandomNum(dos);
			
		if (null != dos) {
			if (dos.getPassportId() > 0 ) {
				return "Passport:"+dos.getPassportId();
			}
			if (StringUtils.isNotBlank(dos.getToken())) {
				return "AccessToken:"+dos.getToken();
			}
			if(StringUtils.isNotBlank(dos.getUrl())){
				return "Passport:"+dos.getPassportId() +" Url:"+ dos.getUrl();
			}
//			if (StringUtils.isNotBlank(dos.getDevice())) {
//				return "device:"+dos.getDevice();
//			}
			/** 暂时不用检测IP
			if (null != dos.getClientIp()) {// 不是公司内部
				if (dos.getClientIp().startsWith("192.168")
						|| dos.getClientIp().startsWith("10.12")
						|| dos.getClientIp().startsWith("127.0.0.1")) {
					 return null;
				}
				return dos.getClientIp();

			}
			*/
		}
		/** 当返回null走ip检测*/
		logger.info("Request type's not get and Passport,AccessToken is blank, request url is [{}], request ip is [{}]", 
				dos.getUrl(), dos.getClientIp());
		return getRandomNum(dos);
	}
	
	private String getRandomNum(VerifyDosStatus dos){
		int baseInt = 1000000;
		String rs =  String.valueOf(RandomUtils.nextInt(baseInt))
				+ RandomUtils.nextInt(baseInt);
		logger.warn("Request type's not get and kugouId,token,device is blank, random num is [{}], request url is [{}], request ip is [{}]", 
				rs, dos.getUrl(), dos.getClientIp());
		return rs;
	}

	class VerifyDosStatus {

		private long passportId;
		private String token;
		private String device;
		private String clientIp;
		private String url;

		public VerifyDosStatus(){}

		public VerifyDosStatus(int passportId, String token, String device, String clientIp) {
			super();
			this.passportId = passportId;
			this.token = token;
			this.device = device;
			this.clientIp = clientIp;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getDevice() {
			return device;
		}
		public void setDevice(String device) {
			this.device = device;
		}
		public String getClientIp() {
			return clientIp;
		}
		public void setClientIp(String clientIp) {
			this.clientIp = clientIp;
		}

		/**
		 * @return 返回 url。
		 */
		public String getUrl() {
			return url;
		}

		/**
		 * @param url 设置 url。
		 */
		public void setUrl(String url) {
			this.url = url;
		}

		public long getPassportId() {
			return passportId;
		}

		public void setPassportId(long passportId) {
			this.passportId = passportId;
		}


	}
}
