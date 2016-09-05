package org.qf.clint.core.resource;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import org.qf.clint.core.model.common.Jar;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: jar包资源
 * <br>
 * File Name: JarResource.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年9月5日 上午11:05:24 
 * @version: v1.0
 *
 */
public class JarResource implements JarResourceMBean, Resource {
	
	private final Logger log = Logger.getLogger(getClass().getName());

	@Override
	public List<String> getJarList() {
		String path = System.getProperty("user.dir");
		File file = new File(path);
		log.severe(file.getAbsolutePath());
		return null;
	}

	@Override
	public Jar getJarInfo(String jarName) {
		return null;
	}

}
