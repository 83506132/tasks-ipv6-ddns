package com.musebk.resolution.service.impl;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.musebk.resolution.other.builder.AddDomainRecordRequestBuilder;
import com.musebk.resolution.other.builder.DeleteDomainRecordRequestBuilder;
import com.musebk.resolution.other.builder.DescribeDomainRecordsRequestBuilder;
import com.musebk.resolution.other.builder.UpdateDomainRecordRequestBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 解析服务
 *
 * @Author ZhaoMuse
 * @date 2022/4/29 15:18
 * @Since 1.0
 */
@Service
@Slf4j
public class DynamicDomainNameServer {
    @Resource
    private AddDomainRecordRequestBuilder addBuild;
    @Resource
    private DeleteDomainRecordRequestBuilder delBuild;
    @Resource
    private DescribeDomainRecordsRequestBuilder selBuild;
    @Resource
    private UpdateDomainRecordRequestBuilder updBuild;
    @Resource
    private IAcsClient parseClient;

    public String add() throws ClientException {
        return parseClient.getAcsResponse(addBuild.add()).getRecordId();
    }

    public Map<String, String> useSelect() throws ClientException {
        List<DescribeDomainRecordsResponse.Record> domainRecords = parseClient.getAcsResponse(selBuild.select()).getDomainRecords();
        if (domainRecords != null) {
            return domainRecords.stream().collect(Collectors.toMap(DescribeDomainRecordsResponse.Record::getRecordId, DescribeDomainRecordsResponse.Record::getValue));
        }
        return Collections.emptyMap();
    }

    public String useSelectFirst() throws ClientException {
        List<DescribeDomainRecordsResponse.Record> domainRecords = parseClient.getAcsResponse(selBuild.select()).getDomainRecords();
        if (domainRecords != null) {
            return domainRecords.stream().map(DescribeDomainRecordsResponse.Record::getRecordId).findAny().orElse(null);
        }
        return null;
    }

    public void update(String recordId) throws ClientException {
        parseClient.getAcsResponse(updBuild.update(recordId));
    }

    public void delete(Collection<String> recordIds) throws ClientException {
        for (String recordId : recordIds) {
            delete(recordId);
        }
    }

    public void delete(String recordId) throws ClientException {
        parseClient.getAcsResponse(delBuild.build(recordId));
    }
}
