package com.musebk.resolution.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

import static com.musebk.resolution.ResolverEntrance.PROPERTY_PREFIX;

/**
 * description:
 *
 * @Author ZhaoMuse
 * @date 2022/4/29 14:34
 * @Since
 */
@Data
@ConfigurationProperties(prefix = PROPERTY_PREFIX + ".send.mail")
public class MailProfileProperties implements Serializable {
    public final static String MAIL_SYMBOL = "@";
    /**
     * 是否需要邮件通知
     */
    private boolean enable;

    private String email;
}
