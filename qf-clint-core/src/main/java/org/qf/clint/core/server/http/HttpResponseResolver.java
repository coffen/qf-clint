package org.qf.clint.core.server.http;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: Http响应处理器
 * <br>
 * File Name: HttpResponseResolver.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年7月2日 上午9:37:13 
 * @version: v1.0
 *
 */
public interface HttpResponseResolver {
	
	/**
	 * 解析请求应答对象
	 * 
	 * @param request
	 * @param response
	 * @param result
	 * @return
	 */
	public void resolve(HttpRequest request, HttpResponse response, Object result);
	
}
