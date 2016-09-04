package org.qf.clint.core.server.http;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: Action返回类型
 * <br>
 * File Name: Result.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年9月4日 上午10:32:01 
 * @version: v1.0
 *
 */
public class Result<T> {
	
	private int status;		// 状态
	private String message;	// 消息
	
	private T data;			// 数据
	
	public Result(int status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public Result(int status, T data) {
		this.status = status;
		this.data = data;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}

}
