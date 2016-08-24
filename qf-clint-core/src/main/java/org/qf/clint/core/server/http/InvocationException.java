package org.qf.clint.core.server.http;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: 反射调用异常
 * <br>
 * File Name: InvocationException.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年8月23日 下午9:04:03 
 * @version: v1.0
 *
 */
public class InvocationException extends ActionException {

	private static final long serialVersionUID = -8248684648154255463L;
	
	private Exception exception;
	
	public InvocationException(Exception exception) {
		this.exception = exception;
	}
	
	public Exception getException() {
		return exception;
	}
	
}
