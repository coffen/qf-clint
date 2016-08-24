package org.qf.clint.core.server.http;

import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: Http请求
 * <br>
 * File Name: HttpRequest.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年6月28日 下午10:07:54 
 * @version: v1.0
 *
 */
public interface HttpRequest {
	
	/**
	 * 按指定头名称获取值
	 * 
	 * @param headerName
	 * @return
	 */
	public List<String> getHeader(String headerName);
	
	/**
	 * 获取全部头信息
	 * 
	 * @param headerName
	 * @return
	 */
	public Map<String, List<String>> getHeaders();
	
	/**
	 * 按指定参数名获取参数值
	 * 
	 * @param param
	 * @return
	 */
	public String getParamter(String param);
	
	/**
	 * 获取全部参数信息
	 * 
	 * @param param
	 * @return
	 */
	public Map<String, String> getParamters();
	
	/**
	 * 按指定属性名获取属性对象
	 * 
	 * @param param
	 * @return
	 */
	public Object getAttribute(String attributeName);
	
	/**
	 * 获取请求方法
	 * 
	 * @return
	 */
    public HttpMethod getMethod();
    
    /**
     * 获取上下文路径
     * 
     * @return
     */
    public String getContextPath();
    
    /**
     * 获取请求URI
     * 
     * @return
     */
    public URI getReuestURI();
    
    /**
     * 获取查询字符串
     * 
     * @return
     */
    public String getQueryString();
    
    /**
     * 获取字符编码
     * 
     * @return
     */
    public String getCharEncoding();
    
    /**
     * 获取请求流
     * 
     * @return
     */
    public InputStream getInputStream();
	
}
