package org.qf.clint.test;

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
		JdkHttpServer server = new JdkHttpServer();		
		server.bind(new TestAction());
		
		server.start();
	}
	
}
