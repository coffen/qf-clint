package org.qf.clint.core.server.http.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.qf.clint.core.server.http.HttpResponse;
import org.qf.clint.core.util.HttpExchangeUtil;

import com.sun.net.httpserver.HttpExchange;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: 简单Http回复
 * <br>
 * File Name: SimpleHttpResponse.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年6月30日 下午8:40:21 
 * @version: v1.0
 *
 */
public class SimpleHttpResponse implements HttpResponse {
	
	private HttpExchange exchange;
	
	private int responseStatus;
	private String charEncoding;
	
	public SimpleHttpResponse(HttpExchange exchange) {
		this.exchange = exchange;
		
		responseStatus = exchange.getResponseCode();
		charEncoding = HttpExchangeUtil.parseCharEncoding(exchange);
	}

	@Override
	public void addHeader(String header, String value) {
		exchange.getResponseHeaders().add(header, value);
	}

	@Override
	public void setHeader(String header, String value) {
		exchange.getResponseHeaders().set(header, value);
	}

	@Override
	public List<String> getHeader(String header) {
		return exchange.getResponseHeaders().get(header);
	}
	
	@Override
	public int getStatus() {
		return responseStatus;
	}
	
	@Override
	public void setStatus(int status) {
		this.responseStatus = status;
	}
	
	@Override
	public String getCharEncoding() {
		return charEncoding;
	}
	
	@Override
	public void setCharEncoding(String encoding) {
		this.charEncoding = encoding;
	}

	@Override
	public void send(int status, String info) throws IOException {
		exchange.sendResponseHeaders(status, info.getBytes(charEncoding).length);	// 设置响应头属性及响应信息的长度  
        OutputStream out = exchange.getResponseBody(); 					// 获得输出流  
        out.write(info.getBytes(charEncoding));  
        out.flush();  
        exchange.close(); 
	}

}
