package org.qf.clint.core.resource;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: 操作系统资源
 * <br>
 * File Name: OSResource.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年6月16日 下午10:32:06 
 * @version: v1.0
 *
 */
public class OSResource implements Resource {
	
	// 操作系统名称
	private String name;
	// 版本号
	private String version;
	// 架构
	private String arch;
	// CPU数量
	private int availableProcessors;
	// 系统加载平均值
	private double systemLoadAverage;
    // 总的物理内存
    private long totalPhysicalMemorySize;
    // 剩余的物理内存
    private long freePhysicalMemorySize;
    // 已使用的物理内存
    private long usedPhysicalMemorySize;
    // 总的缓存
    private long totalSwapMemorySize;
    // 剩余的缓存
    private long freeSwapMemorySize;
    // 已使用的缓存
    private long usedSwapMemorySize;
    // cpu使用率
    private double cpuRatio;	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getArch() {
		return arch;
	}

	public void setArch(String arch) {
		this.arch = arch;
	}

	public int getAvailableProcessors() {
		return availableProcessors;
	}

	public void setAvailableProcessors(int availableProcessors) {
		this.availableProcessors = availableProcessors;
	}

	public double getSystemLoadAverage() {
		return systemLoadAverage;
	}

	public void setSystemLoadAverage(double systemLoadAverage) {
		this.systemLoadAverage = systemLoadAverage;
	}

	public long getTotalPhysicalMemorySize() {
		return totalPhysicalMemorySize;
	}

	public void setTotalPhysicalMemorySize(long totalPhysicalMemorySize) {
		this.totalPhysicalMemorySize = totalPhysicalMemorySize;
	}

	public long getFreePhysicalMemorySize() {
		return freePhysicalMemorySize;
	}

	public void setFreePhysicalMemorySize(long freePhysicalMemorySize) {
		this.freePhysicalMemorySize = freePhysicalMemorySize;
	}

	public long getUsedPhysicalMemorySize() {
		return usedPhysicalMemorySize;
	}

	public void setUsedPhysicalMemorySize(long usedPhysicalMemorySize) {
		this.usedPhysicalMemorySize = usedPhysicalMemorySize;
	}

	public long getTotalSwapMemorySize() {
		return totalSwapMemorySize;
	}

	public void setTotalSwapMemorySize(long totalSwapMemorySize) {
		this.totalSwapMemorySize = totalSwapMemorySize;
	}

	public long getFreeSwapMemorySize() {
		return freeSwapMemorySize;
	}

	public void setFreeSwapMemorySize(long freeSwapMemorySize) {
		this.freeSwapMemorySize = freeSwapMemorySize;
	}

	public long getUsedSwapMemorySize() {
		return usedSwapMemorySize;
	}

	public void setUsedSwapMemorySize(long usedSwapMemorySize) {
		this.usedSwapMemorySize = usedSwapMemorySize;
	}

	public double getCpuRatio() {
		return cpuRatio;
	}

	public void setCpuRatio(double cpuRatio) {
		this.cpuRatio = cpuRatio;
	}
	
	
}
