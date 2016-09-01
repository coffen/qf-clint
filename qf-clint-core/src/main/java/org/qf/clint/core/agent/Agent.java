package org.qf.clint.core.agent;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: Jmx代理接口
 * <br>
 * File Name: Agent.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年8月31日 上午11:02:23 
 * @version: v1.0
 *
 */
public interface Agent {
	
	/**
	 * 启动服务
	 */
	public void startServer();
	
	/**
	 * 停止服务
	 */
	public void stopServer();
	
	/**
	 * 是否运行中
	 * 
	 * @return boolean
	 */
	public boolean isActive();
	
	/**
	 * 获取服务地址
	 * 
	 * @return
	 */
	public String getHost();
	
	/**
	 * 获取协议
	 */
	public String getProtocol();
	
	/**
	 * 获取端口
	 * 
	 * @return int
	 */
	public int getPort();
	
}
