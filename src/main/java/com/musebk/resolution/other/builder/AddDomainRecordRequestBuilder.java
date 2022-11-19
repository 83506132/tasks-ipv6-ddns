package com.musebk.resolution.other.builder;

import com.aliyuncs.alidns.model.v20150109.AddDomainRecordRequest;
import com.musebk.resolution.domain.config.ProjectProfileConfiguration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * description:
 *
 * @Author ZhaoMuse
 * @date 2022/4/29 21:52
 * @Since
 */
@Component
public class AddDomainRecordRequestBuilder {
    @Resource
    ProjectProfileConfiguration configuration;

    public AddDomainRecordRequest add() {
        AddDomainRecordRequest request = new AddDomainRecordRequest();
        request.setDomainName(configuration.getDomainName());
        request.setLine(configuration.getLine());
        request.setTTL(configuration.getTtl());
        request.setRR(configuration.getPrefixDomain());
        request.setType(configuration.getRecordType().getValue());
        request.setValue(configuration.getRecordType().local());
        return request;
    }
}
