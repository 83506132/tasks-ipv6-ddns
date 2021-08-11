package com.musebk.build.other.builder;

import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordRequest;
import com.musebk.build.domain.config.ProjectProfileConfiguration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * description:
 *
 * @Author ZhaoMuse
 * @date 2022/4/29 21:53
 * @Since
 */
@Component
public class UpdateDomainRecordRequestBuilder {
    @Resource
    ProjectProfileConfiguration configuration;

    public UpdateDomainRecordRequest update(String recordId) {
        UpdateDomainRecordRequest request = new UpdateDomainRecordRequest();
        request.setRecordId(recordId);
        request.setRR(configuration.getPrefixDomain());
        request.setLine(configuration.getLine());
        request.setTTL(configuration.getTtl());
        request.setType(configuration.getRecordType().getValue());
        request.setValue(configuration.local());
        return request;
    }
}
