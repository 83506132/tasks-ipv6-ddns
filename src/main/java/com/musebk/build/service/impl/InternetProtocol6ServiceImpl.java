package com.musebk.build.service.impl;

import com.google.gson.Gson;
import com.musebk.build.domain.config.ProjectProfileConfiguration;
import com.musebk.build.domain.error.NoNetworkCardException;
import com.musebk.build.domain.mark.OS;
import com.musebk.build.service.InternetProtocol6Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.*;
import java.util.Enumeration;
import java.util.Objects;

/**
 * @Author ZhaoMuse
 * @date 2022/4/29 17:36
 * @Since 1.0
 */
@Service
@Slf4j
public class InternetProtocol6ServiceImpl implements InternetProtocol6Service {

    @Resource
    ProjectProfileConfiguration configuration;
    @Resource
    private Gson gson;

    @Resource
    private OS os;

    @Override
    public String local() {
        return configuration.getProtocol6().getValue();
    }

    public void local(String ipv6) {
        configuration.getProtocol6().setValue(ipv6);
    }

    @Override
    public boolean refresh() {
        String local = os.getLocal(ipv6());
        if (!Objects.equals(local(), local)) {
            local(local);
        }
        return false;
    }

    private InetAddress ipv6() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ipv4Local = InetAddress.getLocalHost();
            get:
            while (networkInterfaces.hasMoreElements()) {
                boolean flag = false;
                for (InterfaceAddress interfaceAddress : networkInterfaces.nextElement().getInterfaceAddresses()) {
                    InetAddress address = interfaceAddress.getAddress();
                    if (flag || (flag = address.equals(ipv4Local))) {
                        if (address instanceof Inet6Address) {
                            return address;
                        }
                    } else if (address instanceof Inet6Address) {
                        break;
                    }
                }
            }
            throw new NoNetworkCardException("没有找到相应的ipv6地址");
        } catch (SocketException | UnknownHostException e) {
            throw NoNetworkCardException.DEFAULT_SINGLE;
        }
    }

}
