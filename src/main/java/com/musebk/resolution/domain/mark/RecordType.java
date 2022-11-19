package com.musebk.resolution.domain.mark;

import com.musebk.resolution.domain.error.RefreshException;
import com.musebk.resolution.service.other.HaveLock;
import com.musebk.resolution.service.other.RecordAssign;

/**
 * 记录解析模式
 *
 * @Author ZhaoMuse
 * @date 2022/4/28 23:47
 * @Since 1.0
 */
public enum RecordType {
    IPV4("A"), IPV6("AAAA"), AUTO() {
        private HaveLock lock;

        @Override
        public void setInitial(RecordAssign recordAssign) {
            if (recordAssign instanceof HaveLock) {
                this.lock = (HaveLock) recordAssign;
            }
            super.setInitial(recordAssign);
        }

        @Override
        public String getValue() {
            return super.getValue();
        }

        @Override
        public void refreshCode(String nameLower) {
            for (RecordType value : RecordType.values()) {
                if (value.name().toLowerCase().equals(nameLower)){
                    this.code = value.code;
                    return;
                }
            }
            super.refreshCode(nameLower);
        }
    };

    /**
     * 解析类型
     */
    protected String code;
    /**
     * 获取地址
     */
    private RecordAssign recordAssign;

    RecordType() {
    }

    RecordType(String code) {
        this.code = code;
    }

    public static RecordType get(String name) {
        for (RecordType value : RecordType.values()) {
            if (value.name().toLowerCase().equals(name)) {
                return value;
            }
        }
        return null;
    }

    public void setInitial(RecordAssign recordAssign) {
        this.recordAssign = recordAssign;
    }

    public String getValue() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }

    public String local() {
        return recordAssign.local();
    }

    public void refreshCode(String nameLower) {
        throw RefreshException.DEFAULT_SINGLE;
    }

    public boolean refresh() {
        return recordAssign.refresh();
    }
}
