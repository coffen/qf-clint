package org.qf.clint.core.server.http;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: 请求处理异常
 * <br>
 * File Name: HandleException.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年8月23日 下午9:14:25 
 * @version: v1.0
 *
 */
public class HandleException extends ActionException {

	private static final long serialVersionUID = -657642344990309006L;
	
	private Exception exception;
	private String errorMsg;
	
	public HandleException(Exception exception) {
		this(exception, null);
	}
	
	public HandleException(String errorMsg) {
		this(null, errorMsg);
	}
	
	public HandleException(Exception exception, String errorMsg) {
		this.exception = exception;
		this.errorMsg = errorMsg;
	}
	
	public Exception getException() {
		return exception;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}

}
