package org.qf.clint.core.server.http.impl;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.qf.clint.core.server.http.ActionException;
import org.qf.clint.core.server.http.HttpAction;
import org.qf.clint.core.server.http.HttpMethod;
import org.qf.clint.core.server.http.HttpRequest;
import org.qf.clint.core.server.http.HttpResponse;
import org.qf.clint.core.server.http.HttpResponseResolver;
import org.qf.clint.core.server.http.HttpServlet;
import org.qf.clint.core.server.http.RequestPath;
import org.qf.clint.core.server.http.ServletContext;
import org.qf.clint.core.util.StringUtil;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: 通用Http处理器
 * <br>
 * File Name: DefaultHttpServlet.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年6月30日 下午9:24:54 
 * @version: v1.0
 *
 */
public class DefaultHttpServlet implements HttpServlet {
	
	private final Logger log = Logger.getLogger(getClass().getName());
	
	private Map<String, RequestHandler> urlMapping = new HashMap<String, RequestHandler>();
	
	private ServletContext context;
	
	private HttpResponseResolver resolver;
	
	public DefaultHttpServlet() {
		this(new JsonResolver());
	}
	
	public DefaultHttpServlet(HttpResponseResolver resolver) {
		this.resolver = resolver == null ? new JsonResolver() : resolver;		
		this.context = new DefaultServletContext();
	}
	
	@Override
	public void service(HttpRequest request, HttpResponse response) throws IOException, ActionException {
		String path = request.getReuestURI().getPath();
		if (!urlMapping.containsKey(path)) {
			log.severe("未找到对应的处理器：" + path);
		}
		RequestHandler handler = urlMapping.get(path);
		resolver.resolve(request, response, handler.handle(request, response));
	}
	
	@Override
	public void addAttribute(String name, Object obj) {
		context.addAttribute(name, obj);		
	}
	
	@Override
	public boolean register(HttpAction action) {
		if (action == null) {
			log.severe("不合法的控制器");
			return false;
		}
		buildContext(action);
		parseAction(action);
		
		return true;
	}
	
	private void buildContext(HttpAction action) {
		action.setServletContext(context);
	}
	
	private void parseAction(HttpAction action) {
		Class<?> clazz = action.getClass();
		// 解析方法
		Method[] methods = clazz.getDeclaredMethods();
		if (methods != null && methods.length > 0) {
			for (Method method : methods) {
				if (!method.isAnnotationPresent(RequestPath.class)) {
					continue;
				}
				RequestPath rp = method.getAnnotation(RequestPath.class);
				String url = getUrl(rp.url());
				if (urlMapping.containsKey(url)) {
					log.severe("重复的路径配置: " + action.getClass().toString() + "." + method.getName());
					continue;
				}
				RequestHandler handler = buildHandler(action, method, rp);
				if (handler != null) {
					urlMapping.put(url, handler);
				}
			}
		}
	}
	
	private RequestHandler buildHandler(HttpAction action, Method method, RequestPath rp) {
		if (!Modifier.isPublic(method.getModifiers())) {
			log.severe("映射方法应有public修饰符: " + method.getName());
			return null;
		}
		RequestHandler handler = new RequestHandler(action, method);
		if (!StringUtil.isBlank(rp.method())) {
			HttpMethod hm = HttpMethod.getMethod(rp.method().toUpperCase());
			if (hm == null) {
				log.severe("未找到请求类型: " + rp.method());
				return null;
			}
			handler.setHttpMethod(hm);
		}
		return handler;
	}
	
	private String getUrl(String url) {
		return StringUtil.isBlank(url) ? "/" : url.trim();
	}

}
