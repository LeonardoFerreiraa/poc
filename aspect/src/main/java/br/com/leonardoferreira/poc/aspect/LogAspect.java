package br.com.leonardoferreira.poc.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Before("within(br.com.leonardoferreira.poc.service.*)")
    public void before(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String fullName = className + "#" + methodName;

        log.info("Method={}, Args={}", fullName, joinPoint.getArgs());
    }
}
