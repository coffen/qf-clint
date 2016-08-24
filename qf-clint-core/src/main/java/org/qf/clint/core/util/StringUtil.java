package org.qf.clint.core.util;

/**
 * <p>
 * Project Name: C2C商城
 * <br>
 * Description: 字符串工具类
 * <br>
 * File Name: StringUtil.java
 * <br>
 * Copyright: Copyright (C) 2015 All Rights Reserved.
 * <br>
 * Company: 杭州偶尔科技有限公司
 * <br>
 * @author 穷奇
 * @create time：2016年7月2日 上午10:12:25 
 * @version: v1.0
 *
 */
public class StringUtil {
	
	/**
	 * 验证字符串是否为空
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isBlank(String text) {
        int strLen;
        if (text == null || (strLen = text.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(text.charAt(i)) == false) {
                return false;
            }
        }
        return true;
	}
	
}
