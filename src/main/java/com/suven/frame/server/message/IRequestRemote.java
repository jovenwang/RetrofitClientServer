package com.suven.frame.server.message;
/**
 * 
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author suven  pf_qq@163.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public interface IRequestRemote {
	/**
	 * 
	 * @return
	 */
    String getUrl() ;

	 String getClientIp();
}
