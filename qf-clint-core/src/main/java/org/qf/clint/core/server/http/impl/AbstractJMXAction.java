package org.qf.clint.core.server.http.impl;

import java.util.logging.Logger;

import org.qf.clint.core.agent.HttpAgent;
import org.qf.clint.core.server.http.HttpAction;
import org.qf.clint.core.server.http.ServletContext;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: 抽象Action, 调用JMX的资源
 * <br>
 * File Name: AbstractAction.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年9月2日 上午9:54:53 
 * @version: v1.0
 *
 */
public abstract class AbstractJMXAction implements HttpAction {
	
	private final Logger log = Logger.getLogger(getClass().getName());
	
	private ServletContext servletContext;
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	protected HttpAgent getAgent() {
		if (servletContext == null) {
			log.severe("servletContext为空");
			return null;
		}
		Object target = servletContext.getAttribute("_httpAgent");
		if (target == null || !(target instanceof HttpAgent)) {
			log.severe("ServletContext中未找到HttpAgent属性");
			return null;
		}
		return (HttpAgent)target;
	}
	
}
