package com.musebk.resolution.configure;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.musebk.resolution.domain.config.ProjectProfileConfiguration;
import com.musebk.resolution.domain.mark.OS;
import com.musebk.resolution.processor.ProcessExecuted;
import com.musebk.resolution.service.other.RecordAssign;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 项目核心配置
 *
 * @Author ZhaoMuse
 * @date 2022/4/29 22:12
 * @Since 1.0
 */
@Configuration
@Slf4j
public class DynamicCoreConfiguration implements ApplicationRunner {
    private Thread[] threads;

    @Autowired
    protected DynamicCoreConfiguration() {
    }

    @Bean("initial")
    String dynamicCoreConfigurationInitial(final List<RecordAssign> records) {
        RecordAssign.triggerAssign(records);
        return "initial";
    }

    @Bean
    Thread[] pool(final List<ProcessExecuted> executes) {
        this.threads = new Thread[executes.size()];
        for (int i = 0; i < threads.length; i++) {
            ProcessExecuted target = executes.get(i);
            target.poolInjection(threads, i);
        }
        return this.threads;
    }

    @Override
    public void run(ApplicationArguments args) {
        long millis = System.currentTimeMillis();
        log.info("===================>> start ProcessDynamicPool of Object <<===================");
        for (Thread thread : threads) {
            log.info("{} : {}", thread.getName(), thread.getState());
            thread.start();
        }
        log.info("===================>> end ProcessDynamicPool of Object 耗时: {}millis <<===================", System.currentTimeMillis() - millis);
    }

    @Bean
    HttpClient request() {
        return HttpClientBuilder.create().setMaxConnTotal(20).build();
    }

    @Bean("os")
    OS os() {
        return OS.current();
    }

    @Bean
    IAcsClient parseClient(final ProjectProfileConfiguration configuration) {
        IClientProfile profile = DefaultProfile.getProfile(configuration.getRegionId(), configuration.getAccessKeyId(), configuration.getAccessKeySecret());
        return new DefaultAcsClient(profile);
    }
}
