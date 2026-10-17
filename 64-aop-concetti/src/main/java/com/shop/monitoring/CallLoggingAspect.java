package com.shop.monitoring;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CallLoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(CallLoggingAspect.class);
    @Before("execution(* com.shop.catalog.ProductService.*(..))")
    public void logCall(JoinPoint joinPoint) {
        log.info("Calling {}", joinPoint.getSignature().toShortString());
    }
}
