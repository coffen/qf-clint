package org.qf.clint.core.mbean;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: 操作系统资源MBean
 * <br>
 * File Name: OSResourceMBean.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年9月1日 下午2:37:26 
 * @version: v1.0
 *
 */
public interface OSResourceMBean {
	
	public String getName();

	public String getVersion();
	
	public String getArch();
	
	public Integer getAvailableProcessors();

	public Double getSystemLoadAverage();

	public Long getTotalPhysicalMemorySize();

	public Long getFreePhysicalMemorySize();

	public Long getUsedPhysicalMemorySize();

	public Long getTotalSwapMemorySize();

	public Long getFreeSwapMemorySize();

	public Long getUsedSwapMemorySize();
	
	public Double getCpuRatio();
	
	public Double getDiskUsage();
	
	public Double getNetUsage();

}
