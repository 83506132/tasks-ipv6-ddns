package com.musebk.resolution.service.other;

/**
 * 本地地址组合行为接口
 *
 * @Author ZhaoMuse
 * @date 2022/4/29 17:34
 * @Since 1.0
 */
@FunctionalInterface
public interface LocalHost {
    /**
     * 获取本地地址
     *
     * @return AddressInternetProtocol
     */
    String local();
}
