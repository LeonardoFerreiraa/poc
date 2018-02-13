package br.com.leonardoferreira.test.assertion;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;

/**
 * Created by lferreira on 2/13/18
 */
public class ValidationAssert extends AbstractAssert<ValidationAssert, Class<?>> {

    private ValidationAssert(Class<?> actual) {
        super(actual, ValidationAssert.class);
    }

    public static ValidationAssert assertThat(Class<?> actual) {
        return new ValidationAssert(actual);
    }


    public ValidationAssert hasNotNullIn(String field, String msg) {
        NotNull annotation = getAnnotation(field, NotNull.class);
        if (annotation == null) {
            failWithMessage("file <%s> dont have NotNull validation", field);
        }

        Assertions.assertThat(annotation.message())
                .isEqualTo(msg);

        return this;
    }

    public ValidationAssert hasNotNullIn(String field) {
        boolean annotationPresent = hasAnnotation(field, NotNull.class);
        if (!annotationPresent) {
            failWithMessage("file <%s> dont have NotNull validation", field);
        }

        return this;
    }

    public ValidationAssert hasNotEmptyIn(String field, String msg) {
        NotEmpty annotation = getAnnotation(field, NotEmpty.class);
        if (annotation == null) {
            failWithMessage("file <%s> dont have NotEmpty validation", field);
        }

        Assertions.assertThat(annotation.message())
                .isEqualTo(msg);

        return this;
    }

    public ValidationAssert hasNotEmptyIn(String field) {
        boolean annotationPresent = hasAnnotation(field, NotEmpty.class);
        if (!annotationPresent) {
            failWithMessage("file <%s> dont have NotEmpty validation", field);
        }

        return this;
    }

    private boolean hasAnnotation(String field, Class<? extends Annotation> annotation) {
        try {
            return actual.getDeclaredField(field)
                    .isAnnotationPresent(annotation);
        } catch (NoSuchFieldException e) {
            failWithMessage("field <%s> not found", field);
        }
        return false;
    }

    private <T extends Annotation> T getAnnotation(String field, Class<T> annotation) {
        try {
            return actual.getDeclaredField(field)
                    .getAnnotation(annotation);
        } catch (NoSuchFieldException e) {
            failWithMessage("field <%s> not found", field);
        }
        return null;
    }
}
