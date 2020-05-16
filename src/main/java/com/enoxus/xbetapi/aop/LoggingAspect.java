package com.enoxus.xbetapi.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* com.enoxus.xbetapi.service..*.*(..)))")
    public void logService(JoinPoint joinPoint) {
        LocalDateTime date = LocalDateTime.now();

        log.info("Service method called at: " + date.toString());
        log.info("Service: " + joinPoint.getTarget().getClass().getName());
        log.info("Method name: " + joinPoint.getSignature().getName());
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            log.info("Argument #" + i + ": " + joinPoint.getArgs()[i].toString());
        }
    }
}
