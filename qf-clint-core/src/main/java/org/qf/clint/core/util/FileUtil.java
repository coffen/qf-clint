package org.qf.clint.core.util;

import java.io.File;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: 文件工具类
 * <br>
 * File Name: FileUtil.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年9月5日 下午4:20:40 
 * @version: v1.0
 *
 */
public class FileUtil {
	
	/**
	 * 获取文件所属的文件夹路径
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getDirPath(String filePath) {
		if (StringUtil.isBlank(filePath)) {
			return null;
		}
		filePath = new File(filePath).getAbsolutePath();
		if (filePath.endsWith(File.separator)) {
			return filePath;
		}
		int firstIndex = filePath.lastIndexOf(System.getProperty("path.separator")) + 1;
		int lastIndex = filePath.lastIndexOf(File.separator) + 1;
		return filePath.substring(firstIndex, lastIndex);
	}
	
}
