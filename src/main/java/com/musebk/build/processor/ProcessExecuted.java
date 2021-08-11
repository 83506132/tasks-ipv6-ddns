package com.musebk.build.processor;

import com.google.gson.Gson;
import com.musebk.build.tool.StringUtils;
import lombok.extern.slf4j.Slf4j;

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

    protected ProcessExecuted() {
    }

    protected void onSendMessageStop(){}

    public void run() {
        log.info("{} : {}", threads[index].getName(), threads[index].getState());
        try {
            this.entrance();
        } catch (Exception e) {
            log.error("{} throw stop thread : {}", threads[index].getName(), gson.toJson(e));
            onSendMessageStop();
        } finally {
            threads[index] = null;
            for (Thread thread : threads) {
                if (thread != null) {
                    thread.stop();
                }
            }
        }
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
