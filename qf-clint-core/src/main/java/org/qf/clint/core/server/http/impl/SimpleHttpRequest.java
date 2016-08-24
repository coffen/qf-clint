package org.qf.clint.core.server.http.impl;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.qf.clint.core.server.http.HttpMethod;
import org.qf.clint.core.server.http.HttpRequest;
import org.qf.clint.core.server.http.HttpRequestWrapper;
import org.qf.clint.core.util.StringUtil;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: 简单Http请求
 * <br>
 * File Name: SimpleHttpRequest.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年6月30日 下午8:39:59 
 * @version: v1.0
 *
 */
public class SimpleHttpRequest implements HttpRequest, HttpRequestWrapper {
	
	private final Map<String, List<String>> headers = new HashMap<String, List<String>>();
	private final Map<String, String> parameters = new HashMap<String, String>();
	private final Map<String, Object> attributes = new HashMap<String, Object>();
	
	private HttpMethod method;
	private String contextPath;
	private URI requestUri;
	private String queryString;
	private String charEncoding;
	private InputStream requestInputStream;

	@Override
	public List<String> getHeader(String headerName) {
		return headers.get(headerName);
	}

	@Override
	public Map<String, List<String>> getHeaders() {
		return headers;
	}
	
	@Override
	public void addHeader(String header, String value) {
		if (StringUtil.isBlank(header)) {
			return;
		}
		if (!headers.containsKey(header)) {
			headers.put(header, new ArrayList<String>());
		}
		headers.get(header).add(value);
	}
	
	@Override
	public void setHeader(String header, String value) {
		if (StringUtil.isBlank(header)) {
			return;
		}
		if (!headers.containsKey(header)) {
			headers.put(header, new ArrayList<String>());
		}
		List<String> list = headers.get(header);
		list.clear();
		list.add(value);
	}
	
	@Override
	public String getParamter(String param) {
		return parameters.get(param);
	}

	@Override
	public Map<String, String> getParamters() {
		return parameters;
	}
	
	@Override
	public void addParameter(String param, String value) {
		if (StringUtil.isBlank(param)) {
			return;
		}
		parameters.put(param, value);
	}

	@Override
	public Object getAttribute(String attributeName) {
		return attributes.get(attributeName);
	}
	
	@Override
	public void setAttribute(String attribute, Object obj) {
		if (StringUtil.isBlank(attribute)) {
			return;
		}
		attributes.put(attribute, obj);		
	}

	@Override
	public HttpMethod getMethod() {
		return method;
	}
	
	@Override
	public void setMethod(String method) {
		if (StringUtil.isBlank(method)) {
			return;
		}
		this.method = HttpMethod.getMethod(method.toUpperCase());
	}

	@Override
	public String getContextPath() {
		return contextPath;
	}
	
	@Override
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;		
	}

	@Override
	public URI getReuestURI() {
		return requestUri;
	}
	
	@Override
	public void setReuestURI(URI uri) {
		this.requestUri = uri;		
	}

	@Override
	public String getQueryString() {
		return queryString;
	}
	
	@Override
	public void setQueryString(String queryString) {
		this.queryString = queryString;		
	}

	@Override
	public String getCharEncoding() {
		return charEncoding;
	}
	
	@Override
	public void setCharEncoding(String charEncoding) {
		this.charEncoding = charEncoding;		
	}

	@Override
	public InputStream getInputStream() {
		return requestInputStream;
	}
	
	@Override
	public void setInputStream(InputStream inputStream) {
		this.requestInputStream = inputStream;		
	}

}
