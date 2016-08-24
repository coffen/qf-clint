package org.qf.clint.core.server.http.impl;

import java.util.logging.Logger;

import org.qf.clint.core.server.http.HttpRequest;
import org.qf.clint.core.server.http.HttpResponse;
import org.qf.clint.core.server.http.HttpResponseResolver;

import com.alibaba.fastjson.JSON;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: HtppResponse Json处理器
 * <br>
 * File Name: JsonResolver.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年7月2日 上午10:03:54 
 * @version: v1.0
 *
 */
public class JsonResolver implements HttpResponseResolver {
	
	private final Logger log = Logger.getLogger(getClass().getName());

	@Override
	public void resolve(HttpRequest request, HttpResponse response, Object data) {
		response.addHeader("Content-Type", "text/json");
		try {
			String json = (data == null) ? "{}" : JSON.toJSONString(data);
			response.send(200, json);
		}
		catch (Exception e) {
			log.severe("服务器内部错误：" + e.getStackTrace());
			try {
				response.send(500, "非法的数据格式");
			}
			catch (Exception ex) {
				log.severe("服务器内部错误：" + e.getStackTrace());
			}
		}
		
		
	}

}
