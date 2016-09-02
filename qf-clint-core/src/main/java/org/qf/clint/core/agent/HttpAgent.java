package org.qf.clint.core.agent;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import javax.management.Attribute;
import javax.management.AttributeChangeNotification;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.JMRuntimeException;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanRegistration;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotificationBroadcaster;
import javax.management.NotificationBroadcasterSupport;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.RuntimeOperationsException;

import org.qf.clint.core.server.http.SimpleHttpServer;
import org.qf.clint.core.util.StringUtil;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: Http代理服务
 * <br>
 * File Name: HttpAgent.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年8月28日 上午9:37:36 
 * @version: v1.0
 *
 */
public class HttpAgent implements HttpAgentMBean, MBeanRegistration, NotificationBroadcaster, DynamicMBean {
	
	private final Logger log = Logger.getLogger(getClass().getName());
	
	private SimpleHttpServer httpServer;
	
	private ConcurrentHashMap<String, MBeanServer> mbsMap = new ConcurrentHashMap<String, MBeanServer>();
	
	private NotificationBroadcasterSupport notifyBroadcaster = new NotificationBroadcasterSupport();
	private MBeanNotificationInfo[] notifyInfos = null;
	
	private ReentrantLock registerLock = new ReentrantLock();
	
	private MBeanServer currentMbs;

	public HttpAgent(SimpleHttpServer server) throws RuntimeOperationsException {
		if (server == null) {
			log.severe("SimpleHttpServer参数为空");
			throw new RuntimeOperationsException(new IllegalArgumentException("SimpleHttpServer参数不能为空"), "创建Agent时参数错误");
		}
		httpServer = server;
	}

	@Override
	public void startServer() {
		// 添加Agent到上下文环境
		httpServer.addAttribute("_httpAgent", this);
		
		// 启动服务
		httpServer.start();
	}

	@Override
	public void stopServer() {
		httpServer.stop();		
	}

	@Override
	public boolean isActive() {
		return httpServer.isActive();
	}

	@Override
	public String getHost() {
		return httpServer.getHost();
	}

	@Override
	public int getPort() {
		return httpServer.getPort();
	}

	@Override
	public String getProtocol() {
		return httpServer.getProtocol();
	}

	@Override
	public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
		if (StringUtil.isBlank(attribute)) {
			throw new RuntimeOperationsException(new IllegalArgumentException("属性名不能为空."), "getAttribute属性调用参数为空.");
		}
		if ("Active".equalsIgnoreCase(attribute)) {
			return isActive();
		}
		else if ("Host".equalsIgnoreCase(attribute)) {
			return getHost();
		}
		else if ("Port".equalsIgnoreCase(attribute)) {
			return getPort();
		}
		else if ("Protocol".equalsIgnoreCase(attribute)) {
			return getProtocol();
		}
		throw new AttributeNotFoundException("未知的属性: " + attribute);
	}

	@Override
	public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
		log.info("不能修改Agent属性");
	}

	@Override
	public AttributeList getAttributes(String[] attributes) {
		if (attributes == null || attributes.length == 0) {
			throw new RuntimeOperationsException(new IllegalArgumentException("Attributes不能为空"), "Agent获取Attribute列表时错误");
		}
		AttributeList attributeList = new AttributeList();
		String attribute = null;
		for (int i = 0; i < attributes.length; ++i) {
			attribute = attributes[i];
			try {
				Object localObject = getAttribute(attribute);
				attributeList.add(new Attribute(attribute, localObject));
			} 
			catch (Exception e) {
				log.severe("获取属性值抛出未知异常: " + attribute);
				log.severe(e.getLocalizedMessage());
			}
		}
		return attributeList;
	}

	@Override
	public AttributeList setAttributes(AttributeList attributes) {
		log.info("不能修改Agent属性");
		return null;
	}

	@Override
	public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
		return null;
	}

	@Override
	public MBeanInfo getMBeanInfo() {
		MBeanInfo info = new MBeanInfo("HttpAgent", "Http Adaptor For Jmx", null, null, null, null);
		return info;
	}

	@Override
	public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) throws IllegalArgumentException {
		notifyBroadcaster.addNotificationListener(listener, filter, handback);
		log.info("注册NotificationListener: " + listener);
	}

	@Override
	public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
		notifyBroadcaster.removeNotificationListener(listener);
		log.info("移除NotificationListener: " + listener);
	}

	@Override
	public MBeanNotificationInfo[] getNotificationInfo() {
		if (this.notifyInfos == null) {
			this.notifyInfos = new MBeanNotificationInfo[1];
			String[] arrayOfString = { AttributeChangeNotification.ATTRIBUTE_CHANGE };
			this.notifyInfos[0] = new MBeanNotificationInfo(arrayOfString, AttributeChangeNotification.class.getName(), "Agent状态改变时发送通知");
		}
		return null;
	}

	@Override
	public ObjectName preRegister(MBeanServer server, ObjectName name) throws Exception {
		if (server == null || StringUtil.isBlank(server.getDefaultDomain())) {
			throw new IllegalArgumentException("MBeanServer为空或domain为空!");
		}
		registerLock.lock();
		
		currentMbs = mbsMap.putIfAbsent(name.getDomain(), server);
		if (currentMbs != null) {
			registerLock.unlock();
			throw new JMRuntimeException("Agent已注册MBeanServer: " + server.getDefaultDomain());
		}
		else {
			currentMbs = server;
		}
		
		return name;
	}

	@Override
	public void postRegister(Boolean registrationDone) {
		if (currentMbs != null) {
			if (registrationDone) {
				log.info("MBeanServer注册成功: " + currentMbs.getDefaultDomain());
			}
			else {
				log.severe("MBeanServer注册失败: " + currentMbs.getDefaultDomain());
				mbsMap.remove(currentMbs.getDefaultDomain());
				
			}
		}
		registerLock.unlock();
	}

	@Override
	public void preDeregister() throws Exception {
		
	}

	@Override
	public void postDeregister() {
		Enumeration<String> keyEnum = mbsMap.keys();
		String deregistered = null;
		while (keyEnum.hasMoreElements()) {
			String key = keyEnum.nextElement();
			MBeanServer mbs = mbsMap.get(key);
			ObjectName name = null;
			try {
				name = new ObjectName(key);
			}
			catch(MalformedObjectNameException e) {
				log.severe("不合法的ObjectName: " + key);
				continue;
			}
			if (name != null && !mbs.isRegistered(name)) {
				deregistered = key;
				break;
			}
		}
		if (deregistered != null) {
			MBeanServer removed = mbsMap.remove(deregistered);
			log.info("Agent从MBeanServer注销: " + (removed == null ? "" : removed.getDefaultDomain()));
		}		
	}
	
	public MBeanServer getMBeanServer(String domain) {
		return mbsMap.get(domain);
	}

}
