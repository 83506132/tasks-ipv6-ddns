package com.musebk.resolution.domain.config;

import lombok.*;

import java.io.Serializable;

/**
 * 配置网络协议6功能实体类
 *
 * @Author ZhaoMuse
 * @date 2022/4/28 23:47
 * @Since 1.0
 */
@Data
public class InternetProtocol4 implements Serializable {
    private static final long serialVersionUID = -5579525523061527440L;
    /**
     * 本机地址
     */
    private volatile String value;

    public static final String INTERNET_PROTOCOL4 = "https://ipv4.jp2.test-ipv6.com/ip/?callback=";
}
