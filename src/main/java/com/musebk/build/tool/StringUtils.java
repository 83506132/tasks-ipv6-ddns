package com.musebk.build.tool;

/**
 * 字符串帮助工具
 *
 * @Author ZhaoMuse
 * @date 2022/4/29 0:10
 * @Since 1.0
 */
public class StringUtils {
    public static boolean isBlank(String object) {
        return object == null || object.trim().equals("");
    }

    public static boolean isEmpty(String object) {
        return object == null || object.equals("");
    }

    public static String splicing(String s1, String s2, String... objects) {
        StringBuilder builder = new StringBuilder(s1).append(s2);
        for (String object : objects) {
            builder.append(object);
        }
        return builder.toString();
    }    public static String splicing(Object s1, Object s2, Object... objects) {
        StringBuilder builder = new StringBuilder().append(s1).append(s2);
        for (Object object : objects) {
            builder.append(object);
        }
        return builder.toString();
    }

    private StringUtils() {
    }
}
