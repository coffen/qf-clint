package org.qf.clint.test;

import org.qf.clint.core.server.http.RequestPath;
import org.qf.clint.core.server.http.impl.AbstractJMXAction;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: 测试Action
 * <br>
 * File Name: TestAction.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年9月2日 下午4:39:27 
 * @version: v1.0
 *
 */
public class TestAction extends AbstractJMXAction {
	
	@RequestPath(url = "/test")
	public Object test() {
		return getAgent();
	}
	
}
