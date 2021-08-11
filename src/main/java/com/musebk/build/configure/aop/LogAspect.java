package com.musebk.build.configure.aop;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * description:
 *
 * @Author ZhaoMuse
 * @date 2022/4/29 22:35
 * @Since
 */
@Aspect()
@Slf4j
@Component
public class LogAspect {
    private final Gson gson = new Gson();

    @Around("execution(* com.musebk.build.service..*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        long currentTimeMillis = System.currentTimeMillis();
        if (joinPoint.getArgs().length > 0) {
            log.info("{}(): require: {}", joinPoint.getSignature().getName(), gson.toJson(joinPoint.getArgs()));
        }
        Object result = joinPoint.proceed(joinPoint.getArgs());
        if (result != null) {
            log.info("{}(): execute {}millis result: {}", joinPoint.getSignature().getName(), System.currentTimeMillis() - currentTimeMillis, gson.toJson(result));
        } else {
            log.info("{}(): execute {}millis", joinPoint.getSignature().getName(), System.currentTimeMillis() - currentTimeMillis);
        }
        return result;
    }
}
