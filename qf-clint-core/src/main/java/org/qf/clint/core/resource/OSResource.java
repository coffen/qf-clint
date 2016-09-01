package org.qf.clint.core.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.util.logging.Logger;

import org.qf.clint.core.mbean.OSResourceMBean;
import org.qf.clint.core.util.ByteUtil;

import com.sun.management.OperatingSystemMXBean;

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
public class OSResource implements Resource, OSResourceMBean {
	
	private final Logger log = Logger.getLogger(getClass().getName());
	
	// Cpu测量时间
	private static final int CPUTIME = 5000;  
    
    private static final int PERCENT = 100;  
  
    private static final int FAULTLENGTH = 10;
    
    // 网络带宽
    private final static float totalBandwidth = 1000; 
	
	// 操作系统名称
	private String name;
	// 版本号
	private String version;
	// 架构
	private String arch;
	// CPU数量
	private Integer availableProcessors;
	// 系统加载平均值
	private Double systemLoadAverage;
    // 总的物理内存
    private Long totalPhysicalMemorySize;
    // 剩余的物理内存
    private Long freePhysicalMemorySize;
    // 已使用的物理内存
    private Long usedPhysicalMemorySize;
    // 总的缓存
    private Long totalSwapMemorySize;
    // 剩余的缓存
    private Long freeSwapMemorySize;
    // 已使用的缓存
    private Long usedSwapMemorySize;
    // cpu使用率
    private Double cpuRatio;
    // Disk使用率
    private Double diskUsage;
    // 网络使用率
    private Double netUsage;
    
    private OSResource() {}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

	public String getArch() {
		return arch;
	}

	public Integer getAvailableProcessors() {
		return availableProcessors;
	}

	public Double getSystemLoadAverage() {
		return systemLoadAverage;
	}

	public Long getTotalPhysicalMemorySize() {
		return totalPhysicalMemorySize;
	}

	public Long getFreePhysicalMemorySize() {
		return freePhysicalMemorySize;
	}

	public Long getUsedPhysicalMemorySize() {
		return usedPhysicalMemorySize;
	}

	public Long getTotalSwapMemorySize() {
		return totalSwapMemorySize;
	}

	public Long getFreeSwapMemorySize() {
		return freeSwapMemorySize;
	}

	public Long getUsedSwapMemorySize() {
		return usedSwapMemorySize;
	}

	public Double getCpuRatio() {
		return cpuRatio;
	}
	
	public Double getDiskUsage() {
		return diskUsage;
	}
	
	public Double getNetUsage() {
		return netUsage;
	}
	
	public static OSResource getInstance() {
		OSResource resource = new OSResource();
		
		OperatingSystemMXBean osmxb = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
		int kb = 1024;
		// 构造数据
		resource.name = osmxb.getName();
		resource.version = osmxb.getVersion();
		resource.arch = osmxb.getArch();
		resource.availableProcessors = osmxb.getAvailableProcessors() / kb;
		resource.systemLoadAverage = osmxb.getSystemLoadAverage();
		resource.totalPhysicalMemorySize = osmxb.getTotalPhysicalMemorySize() / kb;
		resource.freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize() / kb;
		resource.usedPhysicalMemorySize = (osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize()) / kb;
		resource.totalSwapMemorySize = osmxb.getTotalSwapSpaceSize() / kb;
		resource.freeSwapMemorySize = osmxb.getFreeSwapSpaceSize() / kb;
		resource.usedSwapMemorySize = (osmxb.getTotalSwapSpaceSize() - osmxb.getFreeSwapSpaceSize()) / kb;
		String osName = resource.name == null ? "" : resource.name.toLowerCase();
		if (osName.startsWith("windows")) {
			// 测量CPU使用率
			resource.cpuRatio = resource.getCpuRatioForWindows();
        }
		else if (osName.startsWith("linux")) {
			// 测量CPU使用率
			resource.cpuRatio = resource.getCpuRatioForLinux();
			// 测量磁盘IO（Linux）
			resource.diskUsage = resource.getDiskUsageForLinux();
			// 测量网络占用率（Linux）
			resource.netUsage = resource.getNetUsageForLinux();
		}
		return resource;
	}
	
	/**
	 * 获取LINUX系统下CPU的使用率
	 */
	public Double getCpuRatioForLinux() {
		log.info("开始收集cpu使用率");
		double cpuUsage = 0;
		Process pro1, pro2;
		Runtime r = Runtime.getRuntime();
		try {
			String command = "cat /proc/stat";
			// 第一次采集CPU时间
			pro1 = r.exec(command);
			BufferedReader in1 = new BufferedReader(new InputStreamReader(pro1.getInputStream()));
			String line = null;
			long idleCpuTime1 = 0, totalCpuTime1 = 0; // 分别为系统启动后空闲的CPU时间和总的CPU时间
			while ((line = in1.readLine()) != null) {
				if (line.startsWith("cpu")) {
					line = line.trim();
					String[] temp = line.split("\\s+");
					idleCpuTime1 = Long.parseLong(temp[4]);
					for (String s : temp) {
						if (!s.equals("cpu")) {
							totalCpuTime1 += Long.parseLong(s);
						}
					}
					log.info("IdleCpuTime: " + idleCpuTime1 + ", " + "TotalCpuTime" + totalCpuTime1);
					break;
				}
			}
			in1.close();
			pro1.destroy();
			try {
				Thread.sleep(100);
			} 
			catch (InterruptedException e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				log.severe("CpuUsage休眠时发生InterruptedException. " + e.getMessage());
				log.severe(sw.toString());
			}
			// 第二次采集CPU时间
			pro2 = r.exec(command);
			BufferedReader in2 = new BufferedReader(new InputStreamReader(pro2.getInputStream()));
			long idleCpuTime2 = 0, totalCpuTime2 = 0; // 分别为系统启动后空闲的CPU时间和总的CPU时间
			while ((line = in2.readLine()) != null) {
				if (line.startsWith("cpu")) {
					line = line.trim();
					String[] temp = line.split("\\s+");
					idleCpuTime2 = Long.parseLong(temp[4]);
					for (String s : temp) {
						if (!s.equals("cpu")) {
							totalCpuTime2 += Long.parseLong(s);
						}
					}
					log.info("IdleCpuTime: " + idleCpuTime2 + ", " + "TotalCpuTime" + totalCpuTime2);
					break;
				}
			}
			if (idleCpuTime1 != 0 && totalCpuTime1 != 0 && idleCpuTime2 != 0 && totalCpuTime2 != 0) {
				cpuUsage = 1 - (float) (idleCpuTime2 - idleCpuTime1) / (float) (totalCpuTime2 - totalCpuTime1);
				log.info("本节点CPU使用率为: " + cpuUsage);
			}
			in2.close();
			pro2.destroy();
		} 
		catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			log.severe("CpuUsage发生InstantiationException. " + e.getMessage());
			log.severe(sw.toString());
		}
		return cpuUsage;
	}
	
	/**
	 * 获取WIN系统下CPU的使用率
	 */
	private Double getCpuRatioForWindows() {
		try {
			String procCmd = System.getenv("windir") + "//system32//wbem//wmic.exe process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
			// 获取进程信息
			long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
			Thread.sleep(CPUTIME);
			long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
			if (c0 != null && c1 != null) {
				long idletime = c1[0] - c0[0];
				long busytime = c1[1] - c0[1];
				return Double.valueOf(PERCENT * (busytime) / (busytime + idletime)).doubleValue();
			} 
			else {
				return 0.0;
			}
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			return 0.0;
		}
	}

	/**
	 * 读取CPU信息
	 */
	private long[] readCpu(final Process proc) {
		long[] retn = new long[2];
		try {
			proc.getOutputStream().close();
			InputStreamReader ir = new InputStreamReader(proc.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			String line = input.readLine();
			if (line == null || line.length() < FAULTLENGTH) {
				return null;
			}
			int capidx = line.indexOf("Caption");
			int cmdidx = line.indexOf("CommandLine");
			int rocidx = line.indexOf("ReadOperationCount");
			int umtidx = line.indexOf("UserModeTime");
			int kmtidx = line.indexOf("KernelModeTime");
			int wocidx = line.indexOf("WriteOperationCount");
			long idletime = 0;
			long kneltime = 0;
			long usertime = 0;
			while ((line = input.readLine()) != null) {
				if (line.length() < wocidx) {
					continue;
				}
				// 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount,
				// ThreadCount,UserModeTime,WriteOperation
				String caption = ByteUtil.substring(line, capidx, cmdidx - 1).trim();
				String cmd = ByteUtil.substring(line, cmdidx, kmtidx - 1).trim();
				if (cmd.indexOf("wmic.exe") >= 0) {
					continue;
				}
				// log.info("line="+line);
				if (caption.equals("System Idle Process") || caption.equals("System")) {
					idletime += Long.valueOf(ByteUtil.substring(line, kmtidx, rocidx - 1).trim()).longValue();
					idletime += Long.valueOf(ByteUtil.substring(line, umtidx, wocidx - 1).trim()).longValue();
					continue;
				}

				kneltime += Long.valueOf(ByteUtil.substring(line, kmtidx, rocidx - 1).trim()).longValue();
				usertime += Long.valueOf(ByteUtil.substring(line, umtidx, wocidx - 1).trim()).longValue();
			}
			retn[0] = idletime;
			retn[1] = kneltime + usertime;
			return retn;
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		} 
		finally {
			try {
				proc.getInputStream().close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 获取LINUX系统下磁盘IO
	 */
	public Double getDiskUsageForLinux() {
		log.info("开始收集磁盘IO使用率");
		double ioUsage = 0.0;
		Process pro = null;
		Runtime r = Runtime.getRuntime();
		try {
			String command = "iostat -d -x";
			pro = r.exec(command);
			BufferedReader in = new BufferedReader(new InputStreamReader(pro.getInputStream()));
			String line = null;
			int count = 0;
			while ((line = in.readLine()) != null) {
				if (++count >= 4) {
					// log.info(line);
					String[] temp = line.split("\\s+");
					if (temp.length > 1) {
						float util = Float.parseFloat(temp[temp.length - 1]);
						ioUsage = (ioUsage > util) ? ioUsage : util;
					}
				}
			}
			if (ioUsage > 0) {
				log.info("本节点磁盘IO使用率为: " + ioUsage);
				ioUsage /= 100;
			}
			in.close();
			pro.destroy();
		} 
		catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			log.severe("IoUsage发生InstantiationException. " + e.getMessage());
			log.severe(sw.toString());
		}
		return ioUsage;
	}
	
	/**
	 * 获取LINUX系统下网络IO
	 */
	public Double getNetUsageForLinux() {
		log.info("开始收集网络带宽使用率");
		double netUsage = 0.0;
		Process pro1, pro2;
		Runtime r = Runtime.getRuntime();
		try {
			String command = "cat /proc/net/dev";
			// 第一次采集流量数据
			long startTime = System.currentTimeMillis();
			pro1 = r.exec(command);
			BufferedReader in1 = new BufferedReader(new InputStreamReader(pro1.getInputStream()));
			String line = null;
			long inSize1 = 0, outSize1 = 0;
			while ((line = in1.readLine()) != null) {
				line = line.trim();
				if (line.startsWith("eth0")) {
					log.info(line);
					String[] temp = line.split("\\s+");
					inSize1 = Long.parseLong(temp[0].substring(5)); // Receive bytes,单位为Byte
					outSize1 = Long.parseLong(temp[8]); // Transmit bytes,单位为Byte
					break;
				}
			}
			in1.close();
			pro1.destroy();
			try {
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				log.severe("NetUsage休眠时发生InterruptedException. " + e.getMessage());
				log.severe(sw.toString());
			}
			// 第二次采集流量数据
			long endTime = System.currentTimeMillis();
			pro2 = r.exec(command);
			BufferedReader in2 = new BufferedReader(new InputStreamReader(pro2.getInputStream()));
			long inSize2 = 0, outSize2 = 0;
			while ((line = in2.readLine()) != null) {
				line = line.trim();
				if (line.startsWith("eth0")) {
					log.info(line);
					String[] temp = line.split("\\s+");
					inSize2 = Long.parseLong(temp[0].substring(5));
					outSize2 = Long.parseLong(temp[8]);
					break;
				}
			}
			if (inSize1 != 0 && outSize1 != 0 && inSize2 != 0 && outSize2 != 0) {
				float interval = (float) (endTime - startTime) / 1000;
				// 网口传输速度,单位为bps
				float curRate = (float) (inSize2 - inSize1 + outSize2 - outSize1) * 8 / (1000000 * interval);
				netUsage = curRate / totalBandwidth;
				log.info("本节点网口速度为: " + curRate + "Mbps");
				log.info("本节点网络带宽使用率为: " + netUsage);
			}
			in2.close();
			pro2.destroy();
		} 
		catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			log.severe("NetUsage发生InstantiationException. " + e.getMessage());
			log.severe(sw.toString());
		}
		return netUsage;
	}
	
	public static void main(String[] args) {
		OSResource resource = OSResource.getInstance();
		System.out.println(resource.getCpuRatio());
	}
	
}
