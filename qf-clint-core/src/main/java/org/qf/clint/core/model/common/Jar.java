package org.qf.clint.core.model.common;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: Jar详情描述
 * <br>
 * File Name: Jar.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年9月5日 上午11:16:07 
 * @version: v1.0
 *
 */
public class Jar implements Serializable {

	private static final long serialVersionUID = 2261548091627293628L;

	private String fullName;	
	private Long size;
	
	private List<MavenDependency> mvnDependencies;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public List<MavenDependency> getMvnDependencies() {
		return mvnDependencies;
	}

	public void setMvnDependencies(List<MavenDependency> mvnDependencies) {
		this.mvnDependencies = mvnDependencies;
	}
	
}
