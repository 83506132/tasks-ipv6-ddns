package com.musebk.resolution.domain.config;

import com.musebk.resolution.domain.mark.RecordType;
import com.musebk.resolution.tool.StringUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

import static com.musebk.resolution.ResolverEntrance.PROPERTY_PREFIX;

/**
 * 配置整个功能实体类
 *
 * @Author ZhaoMuse
 * @date 2022/4/28 23:47
 * @Since 1.0
 */
@Data
@ConfigurationProperties(prefix = PROPERTY_PREFIX + ".config-parse")
public class ProjectProfileConfiguration implements Serializable {
    /**
     * api取动态网络路由器绑定的协议地址
     */
    public static final String AUTO_INTERNET_PROTOCOL = "https://ds.v6ns.jp2.test-ipv6.com/ip/?callback=";
    private static final long serialVersionUID = 3977416243013952133L;
    /**
     * 是否忽略执行时候异常 让其继续运行
     */
    private boolean ignoreExceptionRun = true;
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
