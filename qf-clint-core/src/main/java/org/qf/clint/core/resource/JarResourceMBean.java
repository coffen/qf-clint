package org.qf.clint.core.resource;

import java.util.List;

import org.qf.clint.core.model.common.Jar;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: Jar包资源MBean
 * <br>
 * File Name: JarResourceMBean.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年9月5日 上午11:03:29 
 * @version: v1.0
 *
 */
public interface JarResourceMBean {
	
	/**
	 * 获取全部jar列表（名称）
	 * 
	 * @return
	 */
	public List<String> getJarList();
	
	/**
	 * 根据jar名称获取jar详细信息
	 * 
	 * @param jarName
	 * @return
	 */
	public Jar getJarInfo(String jarName);
	
}
