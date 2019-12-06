package br.com.leonardoferreira.amqp.config;

import java.util.Optional;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomTypeMapper extends DefaultJackson2JavaTypeMapper {

    @Override
    public void fromClass(final Class<?> clazz, final MessageProperties properties) {
        super.fromClass(clazz, properties);

        Optional.ofNullable(clazz.getAnnotation(TypeId.class))
                .map(TypeId::value)
                .ifPresent(typeId -> properties.getHeaders().put(getClassIdFieldName(), typeId));
    }

}
