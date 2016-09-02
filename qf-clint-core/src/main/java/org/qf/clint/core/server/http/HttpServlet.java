package org.qf.clint.core.server.http;

import java.io.IOException;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: Http请求处理器
 * <br>
 * File Name: HttpServlet.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年6月30日 下午9:17:07 
 * @version: v1.0
 *
 */
public interface HttpServlet {
	
	/**
	 * 处理Http请求
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void service(HttpRequest request, HttpResponse response) throws IOException, ActionException;
	
	/**
	 * 添加属性
	 * 
	 * @param name
	 * @param obj
	 */
	public void addAttribute(String name, Object obj);
	
	/**
	 * 注册Http请求控制器
	 * 
	 * @param action
	 * @return
	 */
	public boolean register(HttpAction action);
	
}
