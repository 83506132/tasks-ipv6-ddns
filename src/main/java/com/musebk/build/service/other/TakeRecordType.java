package com.musebk.build.service.other;

import com.musebk.build.domain.mark.RecordType;

/**
 * 取值行为集合接口
 *
 * @Author ZhaoMuse
 * @date 2022/4/30 16:12
 * @Since
 */
public interface TakeRecordType {

    /**
     * 获得协议类型
     *
     * @return 协议
     */
    RecordType getRecordType();
}
