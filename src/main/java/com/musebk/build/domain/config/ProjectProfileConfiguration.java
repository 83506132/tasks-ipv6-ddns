package com.musebk.build.domain.config;

import com.musebk.build.domain.mark.RecordType;
import com.musebk.build.tool.StringUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * 配置整个功能实体类
 *
 * @Author ZhaoMuse
 * @date 2022/4/28 23:47
 * @Since 1.0
 */
@ConfigurationProperties(prefix = "muse.config-parse")
@Setter
@Getter()
public class ProjectProfileConfiguration implements Serializable {
    /**
     * 检查是否存在网络
     */
    public static final String PING_DOMAIN = "8.8.8.8";
    /**
     * api取动态网络路由器绑定的协议地址
     */
    public static final String AUTO_INTERNET_PROTOCOL = "https://ds.v6ns.jp2.test-ipv6.com/ip/?callback=";
    public static final String INTERNET_PROTOCOL4 = "https://ipv4.jp2.test-ipv6.com/ip/?callback=";

    private static final long serialVersionUID = 3977416243013952133L;
    /**
     * 采取获取地址解析模式
     */
    private RecordType recordType = RecordType.IPV4;

    /**
     * 区域
     */
    private String regionId = "cn-shanghai";

    /**
     * 域名前缀
     */
    private String prefixDomain = "dynamic";

    /**
     * 域名
     */
    private String domainName = "musebk.cn";

    /**
     * mobile
     */
    private String line = "default";


    /**
     * TTL second
     */
    private long ttl = 10 * 60;

    /**
     * 密钥编号
     */
    private String accessKeyId;

    /**
     * 秘决密钥
     */
    private String accessKeySecret;

    /**
     * 配置ipv4属性配置项
     */
    private InternetProtocol4 protocol4 = new InternetProtocol4();

    /**
     * 配置ipv6属性配置项
     */
    private InternetProtocol6 protocol6 = new InternetProtocol6();

    /**
     * 通知邮箱前缀
     */
    private String mailPrefix = "83506132";

    /**
     * 通知邮箱后缀
     */
    private String mailSuffix = "qq.com";

    public String getMail() {
        return StringUtils.splicing(mailPrefix, MailProfileProperties.MAIL_SYMBOL, mailSuffix);
    }


    public String local() {
        return recordType.local();
    }

}
