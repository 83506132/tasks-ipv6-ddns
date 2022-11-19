package com.musebk.resolution.processor;

import com.aliyuncs.exceptions.ClientException;
import com.google.gson.Gson;
import com.musebk.resolution.domain.config.ProjectProfileConfiguration;
import com.musebk.resolution.domain.mark.RecordType;
import com.musebk.resolution.service.impl.DynamicDomainNameServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.LockSupport;

/**
 * 执行解析动态地址中心
 *
 * @Author ZhaoMuse
 * @date 2022/4/29 23:32
 * @Since 1.0
 */
@Slf4j
@Component
public class DynamicUpdatedProcess extends ProcessExecuted {

    @Resource
    private ProjectProfileConfiguration configuration;
    @Resource
    private DynamicDomainNameServer dynamicDomainNameServer;
    @Resource
    private Gson gson;
    private volatile String id;

    private RecordType getCurrentModel() {
        return configuration.getRecordType();
    }

    @Override
    void entrance() throws Exception {
        prepareDynamic();
        do {
            try {
                for (; ; ) {
                    LockSupport.park();
                    refreshDynamic(id);
                }
            } catch (Exception e) {
                logStop(log, e);
            }
        } while (configuration.isIgnoreExceptionRun());
    }


    public void refreshDynamic(String id) throws ClientException {
        if (getCurrentModel().refresh()) {
            try {
                dynamicDomainNameServer.update(id);
            } catch (ClientException e) {
                log.error("调用用刷新动态解析发生异常", e);
                if ("SDK.ServerUnreachable".equals(e.getErrCode())) {
                    return;
                }
                prepareDynamic();
            }
        }
    }

    public void prepareDynamic() throws ClientException {
        getCurrentModel().refresh();
        Map<String, String> useSelect = dynamicDomainNameServer.useSelect();
        if (useSelect.size() == 1) {
            Map.Entry<String, String>[] objects = useSelect.entrySet().toArray(new Map.Entry[0]);
            id = objects[0].getKey();
            if (!Objects.equals(configuration.local(), objects[0].getValue())) {
                dynamicDomainNameServer.update(id);
            }
        } else {
            dynamicDomainNameServer.delete(useSelect.keySet());
            id = dynamicDomainNameServer.add();
        }
    }

    public void notice() {
        if (this.binder() == Thread.currentThread()) {
            throw new RuntimeException("绑定线程禁止调用");
        }
        LockSupport.unpark(this.binder());
    }

}
