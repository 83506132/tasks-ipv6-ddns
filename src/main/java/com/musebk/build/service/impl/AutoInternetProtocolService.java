package com.musebk.build.service.impl;

import com.google.gson.Gson;
import com.musebk.build.domain.mark.RecordType;
import com.musebk.build.domain.response.ResponseCallback;
import com.musebk.build.service.other.AddressRefresh;
import com.musebk.build.service.other.HaveLock;
import com.musebk.build.service.other.LocalHost;
import com.musebk.build.service.other.RecordAssign;
import com.musebk.build.tool.NetUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;

/**
 * description:
 *
 * @Author ZhaoMuse
 * @date 2022/4/30 10:02
 * @Since
 */
@Service
@Slf4j
public class AutoInternetProtocolService implements LocalHost, RecordAssign, AddressRefresh, HaveLock {
    private volatile String local;
    @Resource
    private NetUtils netUtils;
    @Resource
    private Gson gson;

    @Override
    public RecordType getRecordType() {
        return RecordType.AUTO;
    }

    @Override
    public String local() {
        return local;
    }

    @Override
    public boolean refresh() {
        try {
            ResponseCallback internetProtocol = netUtils.getInternetProtocol();
            if (!Objects.equals(internetProtocol.getIp(), local())) {
                local = internetProtocol.getIp();
                getRecordType().refreshCode(internetProtocol.getType());
                return true;
            }
        } catch (IOException e) {
            log.error("错误信息: {}", gson.toJson(e));
        }
        return false;
    }
}
