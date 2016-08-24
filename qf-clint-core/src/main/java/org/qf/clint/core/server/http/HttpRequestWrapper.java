package org.qf.clint.core.server.http;

import java.io.InputStream;
import java.net.URI;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: HttpRequest封装
 * <br>
 * File Name: HttpRequestWrapper.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年7月2日 下午2:13:57 
 * @version: v1.0
 *
 */
public interface HttpRequestWrapper {
	
	/**
	 * 添加头信息
	 * 
	 * @param header
	 * @param value
	 */
	public void addHeader(String header, String value);
	
	/**
	 * 设置头信息
	 * 
	 * @param header
	 * @param value
	 */
	public void setHeader(String header, String value);
	
	/**
	 * 添加参数
	 * 
	 * @param param
	 * @param value
	 */
	public void addParameter(String param, String value);
	
	/**
	 * 设置属性
	 * 
	 * @param attribute
	 * @param obj
	 */
	public void setAttribute(String attribute, Object obj);
	
	/**
	 * 设置请求方法
	 * 
	 * @param method
	 */
	public void setMethod(String method);
	
	/**
	 * 设置上下文路径
	 * 
	 * @param contextPath
	 */
	public void setContextPath(String contextPath);
	
	/**
	 * 设置请求Uri
	 * 
	 * @param uri
	 */
	public void setReuestURI(URI uri);
	
	/**
	 * 设置查询子串
	 * 
	 * @param queryString
	 */
	public void setQueryString(String queryString);
	
	/**
	 * 设置字符集
	 * 
	 * @param charEncoding
	 */
	public void setCharEncoding(String charEncoding);
	
	/**
	 * 设置请求流
	 * 
	 * @param inputStream
	 */
	public void setInputStream(InputStream inputStream);
	
}
