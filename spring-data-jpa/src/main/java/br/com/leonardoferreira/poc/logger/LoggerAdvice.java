package br.com.leonardoferreira.poc.logger;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
public class LoggerAdvice implements MethodInterceptor {

    public static Object of(Object obj) {
        final ProxyFactory proxyFactory = new ProxyFactory(obj);
        proxyFactory.addAdvice(new LoggerAdvice());
        return proxyFactory.getProxy();
    }

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        final Method method = invocation.getMethod();
        if (method.getParameterTypes().length == 0) {
            log.info("info=intercepting, method={}.{}()", invocation.getThis().getClass().getSimpleName(), method.getName());
        } else {
            log.info("info=intercepting, method={}.{}({})", invocation.getThis().getClass().getSimpleName(), method.getName(), method.getParameterTypes());
        }

        if (method.getReturnType().equals(Connection.class)
                || method.getReturnType().equals(PreparedStatement.class)) {
            return of(invocation.proceed());
        }

        return invocation.proceed();
    }

}
