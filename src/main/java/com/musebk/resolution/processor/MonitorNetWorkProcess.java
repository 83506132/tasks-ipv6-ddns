package com.musebk.resolution.processor;

import com.musebk.resolution.tool.NetUtils;
import com.musebk.resolution.tool.TimeUtils;
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
@Slf4j
@Component
public class MonitorNetWorkProcess extends ProcessExecuted {
    @Resource
    private DynamicUpdatedProcess updatedProcess;

    @Override
    void entrance() {
        for (long time = sleepDeduction(); ; LockSupport.parkNanos(TimeUtils.nanos(3))) {
            analysis:
            {
                //网络监听
                if (!NetUtils.ping()) {
                    do {
                        LockSupport.parkNanos(TimeUtils.nanos(15));
                    } while (!NetUtils.ping());
                    break analysis;
                }
                //定时执行一次防止遗失
                if (time > System.currentTimeMillis()) {
                    continue;
                }
            }
            updatedProcess.notice();
            time = sleepDeduction();
        }
    }

    private long sleepDeduction() {
        return System.currentTimeMillis() + TimeUtils.minuteToMillis(10);
    }

}