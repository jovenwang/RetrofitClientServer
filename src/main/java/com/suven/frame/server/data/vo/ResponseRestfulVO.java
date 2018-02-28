package com.suven.frame.server.data.vo;

import com.alibaba.druid.support.json.JSONUtils;

public class ResponseRestfulVO {

	private int code;     //状态码
	private String msg;     //状态描述
	private long times;        //时间戳 13位
	private Object response="{}" ;    //Json 内容

	public ResponseRestfulVO(){

	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public long getTimes() {
		return times;
	}

	public void setTimes(long times) {
		this.times = times;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public String toString(){
		return JSONUtils.toJSONString(this);
	}
}
