package br.com.leonardoferreira.test.factory.util;


import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BeanUtils extends org.springframework.beans.BeanUtils {
    public static void copyPropertiesNotNull(Object source, Object target) {
        final Class<?> targetClass = target.getClass();
        final PropertyDescriptor[] targetPds = getPropertyDescriptors(targetClass);
        final List<String> ignoreProperties = new ArrayList<String>();

        try {
            for (PropertyDescriptor t : targetPds) {
                final Method method = targetClass.getMethod("get" + StringUtils.capitalize(t.getName()));
                if (method.invoke(target) != null) {
                    ignoreProperties.add(t.getName());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        copyProperties(source, target, ignoreProperties.toArray(new String[0]));
    }
}
