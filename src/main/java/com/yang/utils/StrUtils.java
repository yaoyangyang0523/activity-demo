package com.yang.utils;

import java.util.Random;

/**
 * explain：一个字符串相关的工具类
 *
 * @author yang
 * @date 2021/1/4
 */
public class StrUtils {

    /**
     * 生成随机工单号
     * @param length
     * @return
     */
    public static String randomSqNo(int length) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return "W" + sb.toString();
    }

}
