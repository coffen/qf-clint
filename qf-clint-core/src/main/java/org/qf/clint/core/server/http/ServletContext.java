package org.qf.clint.core.server.http;

import java.util.Map;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: Servlet上下文环境
 * <br>
 * File Name: ServletContext.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年9月2日 下午3:46:41 
 * @version: v1.0
 *
 */
public interface ServletContext {
	
	/**
	 * 添加属性
	 * 
	 * @param attributeName
	 * @param obj
	 */
	public void addAttribute(String attributeName, Object obj);
	
	/**
	 * 批量添加属性
	 * 
	 * @param attributeMap
	 */
	public void addAttributes(Map<String, Object> attributeMap);
	
	/**
	 * 获取属性
	 * 
	 * @param attributeName
	 * @return
	 */
	public Object getAttribute(String attributeName);

}
