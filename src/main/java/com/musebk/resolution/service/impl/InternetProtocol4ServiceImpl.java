package com.musebk.resolution.service.impl;

import com.google.gson.Gson;
import com.musebk.resolution.domain.config.ProjectProfileConfiguration;
import com.musebk.resolution.service.InternetProtocol4Service;
import com.musebk.resolution.tool.NetUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author ZhaoMuse
 * @date 2022/4/29 17:36
 * @Since 1.0
 */
@Service
@Slf4j
public class InternetProtocol4ServiceImpl implements InternetProtocol4Service {

    @Resource
    ProjectProfileConfiguration configuration;

    @Resource
    NetUtils netUtils;

    @Resource
    private Gson gson;

    @Override
    public String local() {
        return configuration.getProtocol4().getValue();
    }

    private void local(String ipv4) {
        configuration.getProtocol4().setValue(ipv4);
    }

    @Override
    public boolean refresh() {
        try {
            String ipv4 = netUtils.getInternetProtocol4();
            if (!Objects.equals(ipv4, local())) {
                local(ipv4);
                return true;
            }
        } catch (IOException e) {
            log.error("错误信息: {}", gson.toJson(e));
        }
        return false;
    }
}
