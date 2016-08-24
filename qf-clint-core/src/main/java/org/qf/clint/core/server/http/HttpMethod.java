package org.qf.clint.core.server.http;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: Http请求方式，目前只涉及get, post, put, delete这4种操作
 * <br>
 * File Name: HttpMethod.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年7月2日 上午9:22:30 
 * @version: v1.0
 *
 */
public enum HttpMethod {
	
	GET, POST, PUT, DELETE;
	
	private static final Map<String, HttpMethod> methods = new HashMap<String, HttpMethod>();
	
	static {
		methods.put("GET", GET);
		methods.put("POST", POST);
		methods.put("PUT", PUT);
		methods.put("DELETE", DELETE);
	}
	
	public static HttpMethod getMethod(String method) {
		return methods.get(method);
	}
	
}
