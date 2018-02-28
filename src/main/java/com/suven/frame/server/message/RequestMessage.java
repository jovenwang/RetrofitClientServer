package com.suven.frame.server.message;


/**
 * @author skyyu
 *
 */
public class RequestMessage extends RequestBaseMessage {
	
	private long passportId;       //用户Id，pid
	private int appId;     //客户端kugou登录申请的appid
	private String accessToken;     //令牌
	private String device;   //设备标识
	private String sysVersion;//客户端手机系统版本号
	private long times;      //时间戳13位 1387614995111
	private int channel;      //app第三方渠道号  
	private int roleValue;
	private String showName;
	private String realName;
	private String ip;
	private String uri;
	
	public RequestMessage(){}
	
    public static RequestMessage valueOf(RequestBaseMessage cmessage){
		if(cmessage ==null){
			return null;
		}		
		RequestMessage message = new RequestMessage();
		message.setPlatform(cmessage.getPlatform());
		message.setSign(cmessage.getSign());
		message.setVersion(cmessage.getVersion());
		return message;
	}
	 

	

	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public long getTimes() {
		return times;
	}
	public void setTimes(long times) {
		this.times = times;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public String getSysVersion() {
		return sysVersion;
	}

	public void setSysVersion(String sysVersion) {
		this.sysVersion = sysVersion;
	}

	public long getPassportId() {
		return passportId;
	}
	
	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getRoleValue() {
		return roleValue;
	}

	public void setRoleValue(int roleValue) {
		this.roleValue = roleValue;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Override
	public String toString() {
		return "CMessage [passportId=" + passportId + ", appId=" + appId + ", accessToken=" + accessToken + ", device="
				+ device + ", sysVersion=" + sysVersion + ", times=" + times + ", channel=" + channel + ", roleValue="
				+ roleValue + ", showName=" + showName + ", realName=" + realName + ", ip=" + ip + ", uri=" + uri + "]";
	}

}
