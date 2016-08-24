package org.qf.clint.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.qf.clint.core.server.http.HttpRequest;
import org.qf.clint.core.server.http.HttpRequestWrapper;
import org.qf.clint.core.server.http.HttpResponse;
import org.qf.clint.core.server.http.impl.SimpleHttpRequest;
import org.qf.clint.core.server.http.impl.SimpleHttpResponse;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: HttpExchange工具类
 * <br>
 * File Name: HttpExchangeUtil.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年7月2日 上午11:24:19 
 * @version: v1.0
 *
 */
public class HttpExchangeUtil {
	
	/**
	 * 创建HttpRequest
	 * 
	 * @param exchange
	 * @return
	 */
	public static HttpRequest buildHttpRequest(HttpExchange exchange) {
		HttpRequest request = new SimpleHttpRequest();
		HttpRequestWrapper wrapper = (HttpRequestWrapper)request;
		if (exchange != null) {
			buildHeaders(wrapper, exchange);		// 构造Header
			buildAttributes(wrapper, exchange);		// 构造Attribute
			buildCharEncoding(wrapper, exchange);	// 构造CharEncoding
			buildParameters(wrapper, exchange, request.getCharEncoding());	// 构造Uri, Paramter, queryString等
			buildRequestBody(wrapper, exchange);	// 构造RequestBody
		}		
		return request;
	}
	
	private static void buildHeaders(HttpRequestWrapper wrapper, HttpExchange exchange) {
		Headers headers = exchange.getRequestHeaders();
		if (headers != null && headers.size() > 0) {
			for (Entry<String, List<String>> entry : headers.entrySet()) {
				if (StringUtil.isBlank(entry.getKey())) {
					continue;
				}
				if (entry.getValue() == null || entry.getValue().size() == 0) {
					wrapper.addHeader(entry.getKey(), null);
				}
				else {
					for (String value : entry.getValue()) {
						wrapper.addHeader(entry.getKey(), value);
					}
				}
			}
		}
	}
	
	private static void buildAttributes(HttpRequestWrapper wrapper, HttpExchange exchange) {
		Map<String, Object> attributes = exchange.getHttpContext().getAttributes();
		if (attributes != null && attributes.size() > 0) {
			for (Entry<String, Object> entry : attributes.entrySet()) {
				if (StringUtil.isBlank(entry.getKey())) {
					continue;
				}
				wrapper.setAttribute(entry.getKey(), entry.getValue());
			}
		}
	}
	
	private static void buildCharEncoding(HttpRequestWrapper wrapper, HttpExchange exchange) {
        wrapper.setCharEncoding(parseCharEncoding(exchange));
	}
	
	private static void buildParameters(HttpRequestWrapper wrapper, HttpExchange exchange, String charEncoding) {
		// 设置requestURI
		URI uri = exchange.getRequestURI();
		wrapper.setReuestURI(uri);
		// 设置Method
		String method = exchange.getRequestMethod();
		wrapper.setMethod(method);
		// 设置Paramater
		try {
			parseGetParameters(exchange);
			parsePostParameters(exchange, charEncoding);
			@SuppressWarnings("unchecked")
			Map<String, String> parameters = (Map<String, String>) exchange.getAttribute("parameters");
			if (parameters != null && parameters.size() > 0) {
				for (Entry<String, String> entry : parameters.entrySet()) {
					if (StringUtil.isBlank(entry.getKey())) {
						continue;
					}
					wrapper.addParameter(entry.getKey(), entry.getValue());
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void parseGetParameters(HttpExchange exchange) throws UnsupportedEncodingException {
		Map<String, String> parameters = new HashMap<String, String>();
		URI requestedUri = exchange.getRequestURI();
		String query = requestedUri.getRawQuery();
		parseQuery(query, parameters);
		exchange.setAttribute("parameters", parameters);
	}

	private static void parsePostParameters(HttpExchange exchange, String charEncoding) throws IOException {
		if ("post".equalsIgnoreCase(exchange.getRequestMethod())) {
			@SuppressWarnings("unchecked")
			Map<String, String> parameters = (Map<String, String>) exchange.getAttribute("parameters");
			InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StringUtil.isBlank(charEncoding) ? "utf-8" : charEncoding);
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();
			parseQuery(query, parameters);
		}
	}
	
	private static void parseQuery(String query, Map<String, String> parameters) throws UnsupportedEncodingException {
		if (query != null) {
			String pairs[] = query.split("[&]");
			for (String pair : pairs) {
				String param[] = pair.split("[=]");
				String key = null;
				String value = null;
				if (param.length > 0) {
					key = URLDecoder.decode(param[0], System.getProperty("file.encoding"));
				}
				if (param.length > 1) {
					value = URLDecoder.decode(param[1], System.getProperty("file.encoding"));
				}
				if (!StringUtil.isBlank(value) || !parameters.containsKey(key)) {
					parameters.put(key, value);
				} 
			}
		}
	}
	
	private static void buildRequestBody(HttpRequestWrapper wrapper, HttpExchange exchange) {
		wrapper.setInputStream(exchange.getRequestBody());
	}
	
	/**
	 * 创建HttpResponse
	 * 
	 * @param exchange
	 * @return
	 */
	public static HttpResponse buildHttpResponse(HttpExchange exchange) {
		return new SimpleHttpResponse(exchange);
	}
	
	public static String parseCharEncoding(HttpExchange exchange) {
		String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
        if (contentType == null) {
            return null;
        }
        int start = contentType.indexOf("charset=");
        if (start < 0) {
            return null;
        }
        String encoding = contentType.substring(start + 8);
        int end = encoding.indexOf(';');
        if (end >= 0) {
            encoding = encoding.substring(0, end);
        }
        encoding = encoding.trim();
        if ((encoding.length() > 2) && (encoding.startsWith("\"")) && (encoding.endsWith("\""))) {
            encoding = encoding.substring(1, encoding.length() - 1);
        }
        return encoding.trim();
	}
	
}
