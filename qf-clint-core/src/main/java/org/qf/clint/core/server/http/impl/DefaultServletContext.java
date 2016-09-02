package org.qf.clint.core.server.http.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.qf.clint.core.server.http.ServletContext;
import org.qf.clint.core.util.StringUtil;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: 默认ServletContext
 * <br>
 * File Name: DefaultServletContext.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年9月2日 下午3:58:31 
 * @version: v1.0
 *
 */
public class DefaultServletContext implements ServletContext {
	
	private final Logger log = Logger.getLogger(getClass().getName());
	
	private Map<String, Object> attributes = new HashMap<String, Object>();

	@Override
	public void addAttribute(String attributeName, Object obj) {
		if (StringUtil.isBlank(attributeName)) {
			log.severe("属性名不能为空");
		}
		attributes.put(attributeName, obj);
	}

	@Override
	public void addAttributes(Map<String, Object> attributeMap) {
		if (attributeMap == null || attributeMap.size() == 0) {
			log.severe("属性集合不能为空");
		}
		for (Entry<String, Object> entry : attributeMap.entrySet()) {
			if (StringUtil.isBlank(entry.getKey())) {
				continue;
			}
			attributes.put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public Object getAttribute(String attributeName) {
		return attributes.get(attributeName);
	}

}
