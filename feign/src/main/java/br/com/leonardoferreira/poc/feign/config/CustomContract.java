package br.com.leonardoferreira.poc.feign.config;

import feign.MethodMetadata;
import feign.Util;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomContract extends SpringMvcContract {

    @Autowired(required = false)
    public CustomContract(ConversionService feignConversionService,
                          List<AnnotatedParameterProcessor> parameterProcessors) {
        super(parameterProcessors, feignConversionService);
    }

    @Override
    public List<MethodMetadata> parseAndValidatateMetadata(Class<?> targetType) {
        return super.parseAndValidatateMetadata(targetType)
                .stream().peek(metadata -> {
                    Type type = metadata.returnType();
                    if (type instanceof ParameterizedType && ((ParameterizedType) type).getRawType().equals(Mono.class)) {
                        metadata.returnType(Util.resolveLastTypeParameter(type, Mono.class));
                    }
                }).collect(Collectors.toList());

    }
}
