package org.qf.clint.core.server.http;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: Http请求控制器
 * <br>
 * File Name: HttpAction.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年6月30日 下午9:30:21 
 * @version: v1.0
 *
 */
public interface HttpAction {
	
	/**
	 * 设置上下文环境
	 * 
	 * @param servletContext
	 */
	public void setServletContext(ServletContext servletContext);

}
