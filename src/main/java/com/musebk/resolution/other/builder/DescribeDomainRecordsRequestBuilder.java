package com.musebk.resolution.other.builder;

import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsRequest;
import com.musebk.resolution.domain.config.ProjectProfileConfiguration;
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
public class DescribeDomainRecordsRequestBuilder {
    @Resource
    ProjectProfileConfiguration configuration;

    public DescribeDomainRecordsRequest select()  {
        DescribeDomainRecordsRequest request = new DescribeDomainRecordsRequest();
        request.setDomainName(configuration.getDomainName());
        request.setRRKeyWord(configuration.getPrefixDomain());
        request.setType(configuration.getRecordType().getValue());
        return request;
    }
}
