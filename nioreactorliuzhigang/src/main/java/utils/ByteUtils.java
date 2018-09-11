/**
 * Meituan.com Inc.
 * Copyright (c) 2010-2018 All Rights Reserved.
 */
package utils;

/**
 * <p></p>
 *
 * @author liuzhigang04@meitun.com
 * @version $Id: ByteUtils.java, v 0.1 2018-09-06 下午2:19 liuzhigang04 $$
 */
public class ByteUtils {
    /**
     * 字节转字符串
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src){

        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();

    }
}