package org.qf.clint.core.server.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: 请求映射路径
 * <br>
 * File Name: RequestPath.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年8月23日 下午8:27:02 
 * @version: v1.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestPath {
	
	/**
	 * 请求路径
	 * 
	 * @return String
	 */
	String url();
	
	/**
	 * 请求方法
	 * 
	 * @return method
	 */
	String method() default "";
	
}
