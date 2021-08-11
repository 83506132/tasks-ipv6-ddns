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
 * @Since
 */
public interface InternetProtocol6Service extends LocalHost, RecordAssign, AddressRefresh {
    @Override
    default RecordType getRecordType(){
        return RecordType.IPV6;
    }
}
