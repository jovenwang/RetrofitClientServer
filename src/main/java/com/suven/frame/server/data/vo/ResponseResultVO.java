package com.suven.frame.server.data.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ResponseResultVO {

	private int code = 0;     //状态码
	private String msg = "";     //状态描述
	private long times;        //时间戳 13位
	private Object response="{}" ;    //Json 内容

	public ResponseResultVO(){

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
		return times = System.currentTimeMillis();
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

	public void setData(String result, Object value){
		JSONObject object = new JSONObject();
		object.put(result,value);
		response = object;
	}

	public void setData(boolean result){
		JSONObject object = new JSONObject();
		int value = result ? 1 : 0;
		object.put("result",value);
		response = object;
	}


	public String toString(){
		return JSON.toJSONString(this);
	}
}
