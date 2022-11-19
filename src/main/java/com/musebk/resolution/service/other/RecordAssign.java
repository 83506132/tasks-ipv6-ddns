package com.musebk.resolution.service.other;

import com.musebk.resolution.domain.mark.RecordType;

import java.util.Iterator;
import java.util.List;

/**
 * 记录分配器
 *
 * @Author ZhaoMuse
 * @date 2022/4/29 22:08
 * @Since 1.0
 */
public interface RecordAssign extends LocalHost, TakeRecordType,AddressRefresh{
    /**
     * 触发器 运行赋值
     *
     * @param records 记录赋值器集合
     */
    static void triggerAssign(List<RecordAssign> records) {
        for (RecordType value : RecordType.values()) {
            Iterator<RecordAssign> iterator = records.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().binderRecordType(value)) {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * RecordType和RecordAssign进行binder
     *
     * @param type 解析类型
     * @return 是否成功
     */
    default boolean binderRecordType(RecordType type) {
        if (type == getRecordType()) {
            type.setInitial(this);
            return true;
        }
        return false;
    }
}
