package com.musebk.build;

import com.musebk.build.domain.config.MailProfileProperties;
import com.musebk.build.domain.config.ProjectProfileConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 项目启动入口
 */
@SpringBootApplication(proxyBeanMethods = false)
@EnableAspectJAutoProxy
@EnableConfigurationProperties({ProjectProfileConfiguration.class, MailProfileProperties.class})
class ApplicationEntrance {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationEntrance.class, args);
    }
}