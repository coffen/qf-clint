package org.qf.clint.core.server.http;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: Http回复
 * <br>
 * File Name: HttpResponse.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年6月28日 下午10:08:46 
 * @version: v1.0
 *
 */
public interface HttpResponse {
	
	/**
	 * 添加头信息（允许同名的设置）
	 * 
	 * @param header
	 * @param value
	 */
	public void addHeader(String header, String value);
	
	/**
	 * 设置头信息（同名覆盖原设置）
	 * 
	 * @param header
	 * @param value
	 */
	public void setHeader(String header, String value);
	
	/**
	 * 按指定名称返回头信息
	 * 
	 * @param header
	 * @return List<String>
	 */
	public List<String> getHeader(String header);
	
	/**
	 * 设置应答状态
	 * 
	 * @param status
	 */
	public void setStatus(int status);
	
	/**
	 * 获取应答状态
	 * 
	 * @return int
	 */
	public int getStatus();
	
	/**
	 * 设置字符编码
	 * 
	 * @param encoding
	 */
	public void setCharEncoding(String encoding);
	
	/**
	 * 获取字符编码
	 * 
	 * @return String
	 */
	public String getCharEncoding();
	
	/**
	 * 返回请求应答
	 * 
	 * @param status
	 * @param info
	 */
	public void send(int status, String info) throws IOException;
	
}
