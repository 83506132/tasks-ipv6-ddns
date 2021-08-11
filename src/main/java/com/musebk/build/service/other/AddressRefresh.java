package com.musebk.build.service.other;

/**
 * 协议地址刷新器
 *
 * @Author ZhaoMuse
 * @date 2022/4/30 10:05
 * @Since
 */
@FunctionalInterface
public interface AddressRefresh {
    /**
     * 刷新地址
     * @return 成功
     */
    boolean refresh();
}
