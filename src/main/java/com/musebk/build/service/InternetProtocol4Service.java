package com.musebk.build.service;

import com.musebk.build.domain.mark.RecordType;
import com.musebk.build.service.other.AddressRefresh;
import com.musebk.build.service.other.LocalHost;
import com.musebk.build.service.other.RecordAssign;

/**
 * description:
 *
 * @Author ZhaoMuse
 * @date 2022/4/29 15:18
 * @Since 1.0
 */
public interface InternetProtocol4Service extends LocalHost, RecordAssign, AddressRefresh {
    @Override
    default RecordType getRecordType(){
        return RecordType.IPV4;
    }
}
