package org.qf.clint.test;

import org.qf.clint.core.server.http.HttpAction;
import org.qf.clint.core.server.http.RequestPath;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: 
 * <br>
 * File Name: TestAction.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年8月23日 下午6:25:37 
 * @version: v1.0
 *
 */
public class WelcomeAction implements HttpAction {
	
	@RequestPath(url = "/")
	public Object welcome() {
		return "Hello, World!";
	}
	
}
