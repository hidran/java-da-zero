package com.shop.audit;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {
    private static final Logger log = LoggerFactory.getLogger(AuditAspect.class);
    @AfterReturning("@annotation(audited)")
    public void audit(JoinPoint joinPoint, Audited audited) {
        log.info("AUDIT {} {}", audited.value(), Arrays.toString(joinPoint.getArgs()));
    }
}
