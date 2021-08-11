package com.musebk.build.tool;

/**
 * description:
 *
 * @Author ZhaoMuse
 * @date 2022/4/30 14:39
 * @Since
 */
public class TimeUtils {
    public static int millis(int second) {
        return second * 1000;
    }

    public static long nanos(int second) {
        return second * 1000000000L;
    }
    public static long minuteToMillis(int minute) {
        return minute * millis(60);
    }

    private TimeUtils() {
    }
}
