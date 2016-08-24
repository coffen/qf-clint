package org.qf.clint.core.server.http;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: Http服务接口
 * <br>
 * File Name: SimpleHttpServer.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年6月27日 下午8:21:12 
 * @version: v1.0
 *
 */
public interface SimpleHttpServer {
	
	/**
	 * 启动服务
	 */
	public void start();
	
	/**
	 * 停止服务
	 */
	public void stop();
	
	/**
	 * 是否运行中
	 * 
	 * @return boolean
	 */
	public boolean isActive();
	
	/**
	 * 获取端口
	 * 
	 * @return int
	 */
	public int getPort();
	
	/**
	 * 设置端口
	 */
	public void setPort(int port);
	
	/**
	 * 获取协议
	 */
	public String getProtocol();
	
}
