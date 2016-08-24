package org.qf.clint.core.server.http.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import org.qf.clint.core.server.http.HandleException;
import org.qf.clint.core.server.http.HttpAction;
import org.qf.clint.core.server.http.HttpMethod;
import org.qf.clint.core.server.http.HttpRequest;
import org.qf.clint.core.server.http.HttpResponse;
import org.qf.clint.core.server.http.InvocationException;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: 请求处理器
 * <br>
 * File Name: RequestHandler.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年8月23日 下午8:35:01 
 * @version: v1.0
 *
 */
public class RequestHandler {
	
	private final Logger log = Logger.getLogger(getClass().getName());
	
	private HttpAction target;	
	private Method invokeMethod;
	
	private HttpMethod httpMethod;
	
	public RequestHandler(HttpAction action, Method method) {
		this.target = action;
		this.invokeMethod = method;
	}
	
	public HttpAction getTarget() {
		return target;
	}
	
	public Method getInvokeMethod() {
		return invokeMethod;
	}
	
	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}
	
	public HttpMethod getHttpMethod() {
		return httpMethod;
	}
	
	public Object handle(HttpRequest request, HttpResponse response) throws InvocationException, HandleException {
		// 验证请求类型
		if (httpMethod != null && request.getMethod() != httpMethod) {
			throw new HandleException("不支持的请求类型: " + request.getMethod());
		}
		try {
			Class<?>[] clazzArr = invokeMethod.getParameterTypes();
			int paramCount = clazzArr == null ? 0 : clazzArr.length;
			Object[] params = new Object[paramCount];
			if (paramCount > 0) {
				for (int i = 0; i < clazzArr.length; i++) {
					if (clazzArr[i] == HttpRequest.class) {
						params[i] = request;
					}
					else if (clazzArr[i] == HttpResponse.class) {
						params[i] = response;
					}
				}
			}		
			return invokeMethod.invoke(target, params);
		}
		catch (IllegalAccessException e) {
			log.severe("IllegalAccessException");
			throw new InvocationException(e);
		}
		catch (IllegalArgumentException e) {
			log.severe("IllegalArgumentException");
			throw new InvocationException(e);
		}
		catch (InvocationTargetException e) {
			log.severe("InvocationTargetException");
			throw new InvocationException(e);
		}
		catch (Exception e) {
			throw new HandleException(e);
		}
	}
	
}
