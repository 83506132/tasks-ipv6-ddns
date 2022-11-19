package com.musebk.resolution.processor;

import com.google.gson.Gson;
import com.musebk.resolution.domain.DataError;
import com.musebk.resolution.domain.config.ProjectProfileConfiguration;
import com.musebk.resolution.service.InformService;
import com.musebk.resolution.tool.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import javax.annotation.Resource;

/**
 * description:
 *
 * @Author ZhaoMuse
 * @date 2022/4/29 23:04
 * @Since
 */
@Slf4j
public abstract class ProcessExecuted implements Runnable {
    protected final Gson gson = new Gson();
    protected Thread[] threads;
    protected int index;
    @Resource
    protected InformService informService;

    @Resource
    protected ProjectProfileConfiguration configuration;

    protected ProcessExecuted() {
    }

    protected void onSendMessageStop(Exception e) {
        try {
            informService.send(new DataError("域名解析器服务停止运行", e.getMessage()));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void run() {
        log.info("{} : {}", threads[index].getName(), threads[index].getState());
        try {
            this.entrance();
        } catch (Exception e) {
            log.error("{} throw stop thread", threads[index].getName(), e);
            onSendMessageStop(e);
        } finally {
            threads[index] = null;
            for (Thread thread : threads) {
                if (thread != null) {
                    thread.stop();
                }
            }
        }
    }

    protected void logStop(Logger log, Exception e) {
        log.error("{} thread throw is {} stop", threads[index].getName(), configuration.isIgnoreExceptionRun(), e);
    }

    /**
     * 功能入口
     *
     * @throws Exception 抛出所有错误
     */
    abstract void entrance() throws Exception;

    public Thread binder() {
        return threads[index];
    }

    public final void poolInjection(Thread[] threads, int index) {
        setPool(threads, index);
        configureThread(threads[index] = new Thread(this, StringUtils.splicing("ProcessDynamic(", index + 1, ")")));
    }

    public void configureThread(Thread thread) {
        thread.setDaemon(false);
    }

    public final void setPool(Thread[] threads, int index) {
        this.index = index;
        this.threads = threads;
    }
}
