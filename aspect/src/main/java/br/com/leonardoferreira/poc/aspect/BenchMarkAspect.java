package br.com.leonardoferreira.poc.aspect;

import br.com.leonardoferreira.poc.service.BenchMarkService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class BenchMarkAspect {

    @Autowired
    private BenchMarkService benchMarkService;

    @Around("@annotation(br.com.leonardoferreira.poc.aspect.annotation.BenchMark)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object ret = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();

        String className = proceedingJoinPoint.getSignature()
            .getDeclaringType().getSimpleName();
        String methodName = proceedingJoinPoint.getSignature().getName();
        String fullName = className + "#" + methodName;

        String bm = (end - start) + "ms";
        log.info("BenchMark => {} [{}]", fullName, bm);

        benchMarkService.create(fullName, (end - start));

        return ret;
    }
}
