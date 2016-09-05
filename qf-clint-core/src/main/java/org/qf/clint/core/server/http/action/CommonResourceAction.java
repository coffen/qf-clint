package org.qf.clint.core.server.http.action;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.qf.clint.core.server.http.HttpRequest;
import org.qf.clint.core.server.http.RequestPath;
import org.qf.clint.core.server.http.Result;
import org.qf.clint.core.server.http.impl.AbstractJMXAction;
import org.qf.clint.core.util.StringUtil;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: 常规资源控制器
 * <br>
 * File Name: CommonResourceAction.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年9月3日 下午5:03:48 
 * @version: v1.0
 *
 */
public class CommonResourceAction extends AbstractJMXAction {
	
	@RequestPath(url = "/info")
	public Object getInfo(HttpRequest request) {
		String resource = request.getParamter("resource");
		if (StringUtil.isBlank(resource)) {
			return new Result<MBeanInfo>(501, "参数resource为空");
		}
		String domain = "common";
		MBeanServer mbs = getMBeanServer(domain);
		StringBuilder builder = new StringBuilder(domain);
		builder.append(":type=").append(resource).append("ResourceMBean");
		try {
			MBeanInfo info = mbs.getMBeanInfo(new ObjectName(builder.toString()));
			return new Result<MBeanInfo>(200, info);
		} 
		catch (IntrospectionException | InstanceNotFoundException | MalformedObjectNameException | ReflectionException e) {
			return new Result<MBeanInfo>(502, "未找到指定的ResourceMBean: " + resource);
		}
	}
	
	@RequestPath(url = "/attribute")
	public Object getAttribute(HttpRequest request) {
		String resource = request.getParamter("resource");
		String attribute = request.getParamter("attribute");
		if (StringUtil.isBlank(resource) || StringUtil.isBlank(attribute)) {
			return new Result<MBeanInfo>(501, "参数resource或attribute为空");
		}
		String domain = "common";
		MBeanServer mbs = getMBeanServer(domain);
		StringBuilder builder = new StringBuilder(domain);
		builder.append(":type=").append(resource).append("ResourceMBean");
		try {
			ObjectName objName = new ObjectName(builder.toString());
			MBeanInfo info = mbs.getMBeanInfo(objName);
			MBeanAttributeInfo attr = null;
			if (info != null && info.getAttributes() != null) {
				for (MBeanAttributeInfo tmp : info.getAttributes()) {
					if (attribute.equals(tmp.getName())) {
						attr = tmp;
						break;
					}
				}
			}
			if (attr == null) {
				return new Result<MBeanInfo>(502, "Attribute不存在: " + resource + "." + attribute);
			}
			if (!attr.isReadable()) {
				return new Result<MBeanInfo>(502, "Attribute不可访问: " + resource + "." + attribute);
			}
			return new Result<Object>(200, mbs.getAttribute(objName, attribute));
		} 
		catch (IntrospectionException | InstanceNotFoundException | MalformedObjectNameException | ReflectionException | AttributeNotFoundException | MBeanException e) {
			return new Result<MBeanInfo>(502, "未找到指定的ResourceMBean: " + resource);
		}
	}
	
	@RequestPath(url = "/attributes")
	public Object getAttributes(HttpRequest request) {
		String resource = request.getParamter("resource");
		String attributes = request.getParamter("attributes");
		if (StringUtil.isBlank(resource) || StringUtil.isBlank(attributes)) {
			return new Result<MBeanInfo>(501, "参数resource或attributes为空");
		}
		String[] attrArr = attributes.split(",");
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for (String attr : attrArr) {
			map.put(attr, null);
		}
		String domain = "common";
		MBeanServer mbs = getMBeanServer(domain);
		StringBuilder builder = new StringBuilder(domain);
		builder.append(":type=").append(resource).append("ResourceMBean");
		try {
			ObjectName objName = new ObjectName(builder.toString());
			MBeanInfo info = mbs.getMBeanInfo(objName);
			if (info != null && info.getAttributes() != null) {
				for (MBeanAttributeInfo tmp : info.getAttributes()) {
					if (map.containsKey(tmp.getName()) && tmp.isReadable()) {
						map.put(tmp.getName(), mbs.getAttribute(objName, tmp.getName()));
					}
				}
			}
			return new Result<Object>(200, map);
		} 
		catch (IntrospectionException | InstanceNotFoundException | MalformedObjectNameException | ReflectionException | AttributeNotFoundException | MBeanException e) {
			return new Result<MBeanInfo>(502, "未找到指定的ResourceMBean: " + resource);
		}
	}

}
