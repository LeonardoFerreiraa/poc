package br.com.leonardoferreira.poc.feign.config;

import feign.InvocationHandlerFactory;
import feign.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Data
@AllArgsConstructor
public class ReactorInvocationHandler implements InvocationHandler {

    private Target target;

    private Map<Method, InvocationHandlerFactory.MethodHandler> dispatch;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("equals".equals(method.getName())) {
            try {
                Object otherHandler = args.length > 0 && args[0] != null ? Proxy.getInvocationHandler(args[0]) : null;
                return equals(otherHandler);
            } catch (IllegalArgumentException e) {
                return false;
            }
        } else if ("hashCode".equals(method.getName())) {
            return hashCode();
        } else if ("toString".equals(method.getName())) {
            return toString();
        }

        if (Mono.class.isAssignableFrom(method.getReturnType())) {
            return Mono.create(sink -> {
                try {
                    sink.success(dispatch.get(method).invoke(args));
                } catch (Throwable throwable) {
                    sink.error(throwable);
                }
            }).subscribeOn(Schedulers.parallel());
        } else {
            return dispatch.get(method).invoke(args);
        }
    }

}
