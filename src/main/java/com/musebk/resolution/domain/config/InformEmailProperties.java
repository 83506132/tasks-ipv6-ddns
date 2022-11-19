package com.musebk.resolution.domain.config;

import com.sun.mail.iap.Protocol;
import com.sun.mail.util.MailSSLSocketFactory;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Properties;

import static com.musebk.resolution.ResolverEntrance.PROPERTY_PREFIX;

/**
 * @Author ZhaoMuse
 * @date 2022/11/18
 * @Since
 */
@Data
@ConfigurationProperties(prefix = PROPERTY_PREFIX + ".inform-email")
public class InformEmailProperties implements Serializable {
    private static final long serialVersionUID = 3791076526278016939L;

    /**
     * 发送邮箱
     */
    private String email;

    /**
     * 发送邮箱认证密码
     */
    private String pwd;

    /**
     * 邮箱通信域名
     */
    private String host = "smtp.qq.com";

    /**
     * 邮箱通信端口
     */
    private Integer port = 465;

    /**
     * 邮箱协议
     */
    private Protocol protocol = Protocol.smtp;

    /**
     * 是否启用密码授权模式
     */
    private boolean auth = true;

    /**
     * 是否启用SSL加密
     */
    private boolean sslEnable = true;

    @Setter(AccessLevel.NONE)
    private URL templatePath = ClassLoader.getSystemClassLoader().getResource("/mail");

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Properties info;

    public Properties instanceProperty() throws GeneralSecurityException {
        Properties info = this.info;
        if (info == null) {
            synchronized (InformEmailProperties.class) {
                info = this.info;
                if (info == null) {
                    info = toProperty();
                }
            }
        }
        return info;
    }

    private void checkRequireParam() {

    }

    public Properties toProperty() throws GeneralSecurityException {
        checkRequireParam();
        Properties mailInfo = new Properties();
        mailInfo.setProperty("mail.host", host);
        mailInfo.setProperty(" mail.smtp.starttls.enable", "true");
        mailInfo.setProperty("mail.transport.protocol", protocol.name());
        mailInfo.setProperty("mail.smtp.auth", Boolean.toString(auth));
        mailInfo.put("mail.smtp.ssl.enable", Boolean.toString(sslEnable));
        mailInfo.put("mail.smtp.port", port.toString());
        if (sslEnable) {
            MailSSLSocketFactory ssl = new MailSSLSocketFactory();
            ssl.setTrustAllHosts(true);
            mailInfo.put("mail.smtp.ssl.socketFactory", ssl);
        }
        return mailInfo;
    }

    public enum Protocol {
        smtp
    }
}
