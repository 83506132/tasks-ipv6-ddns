package com.musebk.build.tool;

import com.google.gson.Gson;
import com.musebk.build.domain.config.InternetProtocol4;
import com.musebk.build.domain.config.ProjectProfileConfiguration;
import com.musebk.build.domain.response.ResponseCallback;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;

/**
 * 连通网络工具
 *
 * @Author ZhaoMuse
 * @date 2022/4/29 0:21
 * @Since 1.0
 */
@Component
@Slf4j
public class NetUtils {
    /**
     * 检查是否存在网络
     */
    public static final String PING_DOMAIN = "8.8.8.8";
    private static volatile RequestConfig defaultSingle;
    private static final Gson gson = new Gson();
    @Resource
    private HttpClient request;

    @Autowired
    private NetUtils() {
    }

    public static RequestConfig builder() {
        if (defaultSingle == null) {
            synchronized (RequestConfig.class) {
                if (defaultSingle == null) {
                    defaultSingle = RequestConfig.custom().setSocketTimeout(TimeUtils.millis(1)).setConnectTimeout(TimeUtils.millis(2)).setConnectionRequestTimeout(TimeUtils.millis(2)).build();
                }
            }
        }
        return defaultSingle;
    }

    public static boolean ping(String host) {
        try {
            return InetAddress.getByName(host).isReachable(TimeUtils.millis(2));
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean ping() {
        return ping(PING_DOMAIN);
    }

    public ResponseCallback getInternetProtocol() throws IOException {
        return getInternetProtocol(ProjectProfileConfiguration.AUTO_INTERNET_PROTOCOL);
    }

    private ResponseCallback getInternetProtocol(String url) throws IOException {
        HttpGet internetProtocol = new HttpGet(url);
        internetProtocol.setConfig(builder());
        HttpResponse execute = request.execute(internetProtocol);
        try (InputStream content = execute.getEntity().getContent()) {
            return gson.fromJson(new String(content.readAllBytes()).replace("(", "").replace(")", ""), ResponseCallback.class);
        }
    }

    public String getInternetProtocol4() throws IOException {
        return getInternetProtocol(InternetProtocol4.INTERNET_PROTOCOL4).getIp();
    }
}
