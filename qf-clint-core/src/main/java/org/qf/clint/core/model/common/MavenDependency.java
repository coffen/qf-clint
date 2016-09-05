package org.qf.clint.core.model.common;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: Maven依赖属性
 * <br>
 * File Name: MavenDependency.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年9月5日 上午11:27:33 
 * @version: v1.0
 *
 */
public class MavenDependency implements Serializable {
	
	private static final long serialVersionUID = 5558625909049288421L;
	
	private String groupId;
	private String artifactId;
	private String classifier;
	private String version;
	private String type = "jar";
	private String scope = "compile";
	private String packaging = "jar";
	private Boolean optional;
	private List<MavenDependency> exclusions;
	
	public String getGroupId() {
		return groupId;
	}
	
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getArtifactId() {
		return artifactId;
	}
	
	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}
	
	public String getClassifier() {
		return classifier;
	}
	
	public void setClassifier(String classifier) {
		this.classifier = classifier;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	public String getPackaging() {
		return packaging;
	}
	
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	
	public Boolean getOptional() {
		return optional;
	}
	
	public void setOptional(Boolean optional) {
		this.optional = optional;
	}
	
	public List<MavenDependency> getExclusions() {
		return exclusions;
	}
	
	public void setExclusions(List<MavenDependency> exclusions) {
		this.exclusions = exclusions;
	}
	
}
