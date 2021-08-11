package com.musebk.build.processor;

import com.musebk.build.domain.config.ProjectProfileConfiguration;
import com.musebk.build.tool.NetUtils;
import com.musebk.build.tool.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.locks.LockSupport;

/**
 * 网络波动监听中心
 *
 * @Author ZhaoMuse
 * @date 2022/4/29 23:29
 * @Since 1.0
 */
@Component
@Slf4j
public class MonitorNetWorkProcess extends ProcessExecuted {

    @Resource
    ProjectProfileConfiguration configuration;
    @Resource
    private DynamicUpdatedProcess updatedProcess;

    @Override
    void entrance() throws Exception {
        long time = sleepDeduction();
        for (; ; ) {
            if (!NetUtils.ping()) {
                while (!NetUtils.ping()) {
                    LockSupport.parkNanos(TimeUtils.nanos(30));
                }
                updatedProcess.notice();
                time = sleepDeduction();
            } else if (time == System.currentTimeMillis()) {
                updatedProcess.notice();
                time = sleepDeduction();
            }
            LockSupport.parkNanos(TimeUtils.nanos(3));
        }
    }

    private long sleepDeduction() {
        return System.currentTimeMillis() + TimeUtils.minuteToMillis(10);
    }

}