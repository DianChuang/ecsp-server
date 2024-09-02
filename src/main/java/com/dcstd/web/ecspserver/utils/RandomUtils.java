package com.dcstd.web.ecspserver.utils;

public class RandomUtils {
    RandomUtils() {

    }

    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        return getRandomString(length, str);
    }

    /**
     * 随机生成整数
     * @param max 最大范围
     * @return (Integer)整数
     */
    public static Integer getRandomIntegerNumber(int max) {
        return (int) (Math.random() * max);
    }

    /**
     * 随机生成整数
     * @param min 最小值
     * @param max 最大值
     * @return (Integer)整数
     */
    public static Integer getRandomIntegerNumber(int min, int max) {
        return min + (int) (Math.random() * (max - min));
    }


    /**
     * 随机生成指定长度的数字
     * @param length 长度
     * @return (Integer)整数
     */
    public static Integer getTargetLengthRandomNumber(int length) {
        String strLib = "0123456789";
        return Integer.parseInt(getRandomString(length, strLib));
    }

    /**
     * 根据字符库随机生成字符串
     * @param length 字符串长度
     * @param strLib 字符库
     * @return (String)字符串
     */
    public static String getRandomString(int length, String strLib) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = (int) (Math.random() * strLib.length());
            sb.append(strLib.charAt(number));
        }
        return sb.toString();
    }
}
