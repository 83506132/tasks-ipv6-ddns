package com.musebk.resolution.domain.mark;

import com.musebk.resolution.domain.error.InvalidInetAddressException;
import com.musebk.resolution.tool.StringUtils;

import java.net.InetAddress;
import java.util.Arrays;

/**
 * 操作系统类型
 *
 * @Author ZhaoMuse
 * @date 2022/4/28 23:47
 * @Since 1.0
 */
public enum OS {
    Window, Linux {
        @Override
        String filter(String host) {
            int endIndex = host.lastIndexOf("%");
            host = endIndex > 0 ? host.substring(0, endIndex) : host;
            host = host.substring(host.lastIndexOf(":0:") + 3);
            return host;
        }
    }, NON {
        @Override
        public String getLocal(InetAddress address) throws InvalidInetAddressException {
            throw new InvalidInetAddressException("未知系统没有地址筛选");
        }
    };

    private static final OS current = get(System.getProperty("os.name"));

    public static OS get(String os) {
        if (StringUtils.isBlank(os)) {
            return NON;
        }
        String toLowerCase = os.toLowerCase();
        return Arrays.stream(OS.values()).filter(osName -> toLowerCase.startsWith(osName.name().toLowerCase())).findAny().orElseGet(() -> NON);
    }

    public static OS current() {
        return current;
    }

    private static boolean matchingSystem(String host) throws InvalidInetAddressException {
        return host != null && host.startsWith("fe80:");
    }

    public String getLocal(InetAddress address) throws InvalidInetAddressException {
        String hostAddress = address.getHostAddress();
        if (matchingSystem(hostAddress)) {
            throw new InvalidInetAddressException(StringUtils.splicing("地址无效:", address));
        }
        check(hostAddress);
        return filter(hostAddress);
    }

    void check(String host) {
    }

    String filter(String host) {
        return host;
    }
}