package com.musebk.build.other.builder;

import com.aliyuncs.alidns.model.v20150109.DeleteDomainRecordRequest;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.stereotype.Component;

/**
 * description:
 *
 * @Author ZhaoMuse
 * @date 2022/4/29 21:53
 * @Since
 */
@Component
public class DeleteDomainRecordRequestBuilder {

    public DeleteDomainRecordRequest build(String recordId) throws ClientException {
        DeleteDomainRecordRequest request = new DeleteDomainRecordRequest();
        request.setRecordId(recordId);
        return request;
    }
}
