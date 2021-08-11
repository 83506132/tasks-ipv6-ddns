package com.musebk.build.domain.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * description:
 *
 * @Author ZhaoMuse
 * @date 2022/4/29 14:34
 * @Since
 */
@ConfigurationProperties(prefix = "muse.send.mail")
@Setter
@Getter()
public class MailProfileProperties implements Serializable {
    private static final long serialVersionUID = 3791076526278016939L;
    public final static String MAIL_SYMBOL = "@";

    /**
     * 是否需要邮件通知
     */
    private boolean enable;
    
}
