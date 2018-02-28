package com.suven.frame.server.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RequestRemote implements IRequestRemote {

	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 是否要进行登录。
	 */
	private boolean ignoreLogin = false;

	private boolean isCdn;

	private boolean isWhite;
	/**
	 * 是否进行md5密文校验(默认需求检测)。
	 */
	private boolean isMd5 = true;
	/**
	 * 来自客户端的md5校验密文。
	 */
	private String cliMd5Sign;
	/**
	 * 服务端自行加工的md5校验密文。
	 */
	private String srvMd5Sign;
	/** 
	 * 是否需要检测token
	 */
	private boolean isPostReq;
	/**
	 * 是否要进行版本升级检测。
	 */
	private boolean isChkUpgrade =true;
	
	private String url;
	private String clientIp;
	
	
	/**
	 * @return 返回 isMd5。
	 */
	public boolean isMd5() {
		return isMd5;
	}

	/**
	 * @param isMd5 设置 isMd5。
	 */
	public void setMd5(boolean isMd5) {
		this.isMd5 = isMd5;
	}

	/**
	 * @return 返回 cliMd5Sign。
	 */
	public String getCliMd5Sign() {
		return cliMd5Sign;
	}

	/**
	 * @param cliMd5Sign 设置 cliMd5Sign。
	 */
	public void setCliMd5Sign(String cliMd5Sign) {
		this.cliMd5Sign = cliMd5Sign;
	}

	/**
	 * @return 返回 srvMd5Sign。
	 */
	public String getSrvMd5Sign() {
		return srvMd5Sign;
	}

	/**
	 * @param srvMd5Sign 设置 srvMd5Sign。
	 */
	public void setSrvMd5Sign(String srvMd5Sign) {
		this.srvMd5Sign = srvMd5Sign;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	/**
	 * @return 返回 ignoreLogin。
	 */
	public boolean isIgnoreLogin() {
		return ignoreLogin;
	}

	/**
	 * @param ignoreLogin 设置 ignoreLogin。
	 */
	public void setIgnoreLogin(boolean ignoreLogin) {
		this.ignoreLogin = ignoreLogin;
	}

	/**
	 * @return 返回 isChkUpgrade。
	 */
	public boolean isChkUpgrade() {
		return isChkUpgrade;
	}

	/**
	 * @param isChkUpgrade 设置 isChkUpgrade。
	 */
	public void setChkUpgrade(boolean isChkUpgrade) {
		this.isChkUpgrade = isChkUpgrade;
	}

	/**
	 * @return 返回 isPostReq。
	 */
	public boolean isPostReq() {
		return isPostReq;
	}

	/**
	 * @param isPostReq 设置 isPostReq。
	 */
	public void setPostReq(boolean isPostReq) {
		this.isPostReq = isPostReq;
	}

	public boolean isCdn() {
		return isCdn;
	}

	public void setCdn(boolean cdn) {
		isCdn = cdn;
	}

	public boolean isWhite() {
		return isWhite;
	}

	public void setWhite(boolean white) {
		isWhite = white;
	}
}
