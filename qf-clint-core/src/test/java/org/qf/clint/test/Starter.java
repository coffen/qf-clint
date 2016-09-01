package org.qf.clint.test;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.qf.clint.core.agent.HttpAgent;
import org.qf.clint.core.server.http.SimpleHttpServer;
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
		SimpleHttpServer server = new JdkHttpServer();	
		server.bind(new TestAction());
		HttpAgent agent = new HttpAgent(server);
		
		MBeanServer mbs1 = MBeanServerFactory.createMBeanServer("A");
		MBeanServer mbs2 = MBeanServerFactory.createMBeanServer("B");
		
		try {
			mbs1.registerMBean(agent, new ObjectName(mbs1.getDefaultDomain() + ":name=htmlagent,port=" + agent.getPort()));
			mbs2.registerMBean(agent, new ObjectName(mbs2.getDefaultDomain() + ":name=htmlagent,port=" + agent.getPort()));
		} 
		catch (InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException | MalformedObjectNameException e) {
			e.printStackTrace();
		}
		
		agent.startServer();
		
		try {
			Thread.currentThread().sleep(2000);
			mbs2.unregisterMBean(new ObjectName(mbs2.getDefaultDomain() + ":name=htmlagent,port=" + agent.getPort()));
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		} 
		catch (MBeanRegistrationException e) {
			e.printStackTrace();
		} 
		catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} 
		catch (MalformedObjectNameException e) {
			e.printStackTrace();
		}		
	}
	
}
