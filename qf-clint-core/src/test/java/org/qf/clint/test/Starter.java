package org.qf.clint.test;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.qf.clint.core.agent.HttpAgent;
import org.qf.clint.core.resource.JarResource;
import org.qf.clint.core.resource.JarResourceMBean;
import org.qf.clint.core.resource.OSResource;
import org.qf.clint.core.resource.OSResourceMBean;
import org.qf.clint.core.server.http.SimpleHttpServer;
import org.qf.clint.core.server.http.action.CommonResourceAction;
import org.qf.clint.core.server.http.action.WelcomeAction;
import org.qf.clint.core.server.http.impl.JdkHttpServer;

/**
 * 
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: http服务启动类
 * <br>
 * File Name: Starter.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年8月23日 下午6:19:06 
 * @version: v1.0
 *
 */
public class Starter {
	
	public static void main(String[] args) {
		new Starter();
	}
	
	public Starter() {
		init();
	}
	
	private void init() {
		SimpleHttpServer server = new JdkHttpServer();	
		server.bind(new WelcomeAction());
		server.bind(new CommonResourceAction());
		
		HttpAgent agent = new HttpAgent(server);		
		MBeanServer commonMbs = MBeanServerFactory.createMBeanServer("common");
		OSResourceMBean os = new OSResource();
		JarResourceMBean jar = new JarResource();
		
		try {
			commonMbs.registerMBean(os, new ObjectName(commonMbs.getDefaultDomain() + ":type=OSResourceMBean"));
			commonMbs.registerMBean(jar, new ObjectName(commonMbs.getDefaultDomain() + ":type=JarResourceMBean"));
			commonMbs.registerMBean(agent, new ObjectName(commonMbs.getDefaultDomain() + ":name=htmlagent,port=" + agent.getPort()));
			
			agent.startServer();
		} 
		catch (InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException | MalformedObjectNameException e) {
			e.printStackTrace();
		}
	}
	
}
