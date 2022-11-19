package com.musebk.resolution;

import com.musebk.resolution.domain.config.InformEmailProperties;
import com.musebk.resolution.domain.config.MailProfileProperties;
import com.musebk.resolution.domain.config.ProjectProfileConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 项目启动入口
 */
@SpringBootApplication(proxyBeanMethods = false)
@EnableAspectJAutoProxy
@EnableConfigurationProperties({ProjectProfileConfiguration.class, MailProfileProperties.class,InformEmailProperties.class})
@Slf4j
public class ResolverEntrance {

    public static final String PROPERTY_PREFIX = "muse";

    public static void main(String[] args) {
        log.info("==============>>欢迎使用ip域名解析系统 java {}<<==============",Runtime.version());
        SpringApplication.run(ResolverEntrance.class, args);
    }
}