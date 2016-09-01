package org.qf.clint.core.util;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: Byte工具类
 * <br>
 * File Name: ByteUtil.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年9月1日 下午3:35:26 
 * @version: v1.0
 *
 */
public class ByteUtil {

	public static String substring(String src, int start_idx, int end_idx) {
		byte[] b = src.getBytes();
		String tgt = "";
		for (int i = start_idx; i <= end_idx; i++) {
			tgt += (char) b[i];
		}
		return tgt;
	}

}
