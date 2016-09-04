package org.qf.clint.core.server.http.impl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import org.qf.clint.core.server.http.ActionException;
import org.qf.clint.core.server.http.HttpAction;
import org.qf.clint.core.server.http.HttpRequest;
import org.qf.clint.core.server.http.HttpResponse;
import org.qf.clint.core.server.http.HttpServlet;
import org.qf.clint.core.server.http.SimpleHttpServer;
import org.qf.clint.core.util.HttpExchangeUtil;
import org.qf.clint.core.util.StringUtil;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: JdkApi编写的http服务
 * <br>
 * File Name: JdkHttpServer.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年6月27日 下午8:15:04 
 * @version: v1.0
 *
 */
public class JdkHttpServer implements SimpleHttpServer {
	
	private final Logger log = Logger.getLogger(getClass().getName());
	
	private final static String DEFAULT_CHAR_ENCODING = "UTF-8";
	
	private final static int STATUS_READY = 1;
	private final static int STATUS_RUNNING = 2;
	private final static int STATUS_STOP = 4;
	
	private final HttpServlet servlet = new DefaultHttpServlet();
	
	private HttpServer server;		// JDK Http服务器
		
	private int status = STATUS_READY;
	
	private String charEncoding = DEFAULT_CHAR_ENCODING;
	
	private int port = 9076;
	
	private int maxProcess = 30;
	
	private int delayToStop = 5;
	
	private ReentrantLock lock = new ReentrantLock();
	
	public JdkHttpServer() {}

	@Override
	public void start() {
		lock.lock();
		if (status != STATUS_READY) {
			log.severe("*^* 服务已经在运行中或停止");
			lock.unlock();
			return;
		}
		initServlet();
		try {
			server = HttpServer.create(new InetSocketAddress(port), maxProcess);
			server.createContext("/", new SimpleHandler());
			server.setExecutor(null);

			server.start();			
			status = STATUS_RUNNING;
			
			log.info("^-^ 服务启动成功");
		}
		catch (Exception e) {
			log.severe("*^* 服务启动错误，请检查");
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public void stop() {
		lock.lock();
		if (status == STATUS_STOP) {
			log.severe("*^* 服务已经停止");
		}
		else {
			server.stop(delayToStop);			
			status = STATUS_STOP;
			
			log.info("^-^ 服务停止运行");
		}
		lock.unlock();
	}
	
	private void initServlet() {
		servlet.addAttribute("_encoding", charEncoding);
	}

	@Override
	public boolean isActive() {
		return status == STATUS_RUNNING;
	}
	
	@Override
	public String getHost() {
		if (server == null || server.getAddress() == null) {
			log.info("*^* 服务未完成初始化");
		}
		return server.getAddress().getHostString();
	}

	@Override
	public int getPort() {
		return this.port;
	}

	@Override
	public void setPort(int port) {
		lock.lock();
		if (status != STATUS_READY) {
			log.severe("*^* 服务已经在运行中或停止");
		}
		else {
			if (port >= 0 && port <= 65535) {
				this.port = port;
			}
		}
		lock.unlock();
	}
	
	public String getCharEncoding() {
		return charEncoding;
	}
	
	public void setCharEncoding(String charEncoding) {
		lock.lock();
		if (status != STATUS_READY) {
			log.severe("*^* 服务已经在运行中或停止");
		}
		else {
			if (!(StringUtil.isBlank(charEncoding))) {
				this.charEncoding = charEncoding;
			}
		}
		lock.unlock();
	}
	
	public int getMaxProcess() {
		return maxProcess;
	}
	
	public void setMaxProcess(int maxProcess) {
		lock.lock();
		if (status != STATUS_READY) {
			log.severe("*^* 服务已经在运行中或停止");
		}
		else {
			this.maxProcess = maxProcess < 10 ? 10 : maxProcess;
		}
		lock.unlock();
	}

	@Override
	public String getProtocol() {
		return "http";
	}
	
	@Override
	public void addAttribute(String name, Object obj) {
		servlet.addAttribute(name, obj);		
	}
	
	@Override
	public boolean bind(HttpAction action) {
		if (isActive()) {
			log.severe("*^* 服务已经在运行中, 不允许注册!");
			return false;
		}
		return servlet.register(action);
	}
	
	/**
	 * Simple HttpHandler to handle the simple httprequest
	 */
	private class SimpleHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			HttpRequest request = HttpExchangeUtil.buildHttpRequest(exchange, servlet);
			HttpResponse response = HttpExchangeUtil.buildHttpResponse(exchange, servlet);
			
			try {
				servlet.service(request, response);
			}
			catch (ActionException e) {
				log.severe("处理器错误: " + e.getMessage());
			}
		}		
	}
	
}
