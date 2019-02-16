package br.com.leonardoferreira.poc.httpclient.request.impl;

import br.com.leonardoferreira.poc.httpclient.domain.RequestMethod;
import br.com.leonardoferreira.poc.httpclient.request.RequestAttributes;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.util.UriComponentsBuilder;

public class SpringRequestAttributes implements RequestAttributes {

    private Object body;

    private Map<String, String> headers;

    private Map<String, Object> params;

    private Map<String, Object> pathVariables;

    private String uri;

    private RequestMethod method;

    public SpringRequestAttributes(RequestMapping requestMapping,
                                   Method method,
                                   Object[] args) {
        initAttributes();
        extractParameters(method, args);
        buildMethod(requestMapping);
        buildUri(requestMapping);
    }

    @Override
    public RequestMethod getMethod() {
        return this.method;
    }

    @Override
    public String getUri() {
        return this.uri;
    }

    @Override
    public Object getBody() {
        return this.body;
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.params;
    }

    private void initAttributes() {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.pathVariables = new HashMap<>();
    }

    private void extractParameters(Method method, Object[] args) {
        Parameter[] parameters = method.getParameters();

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];

            if (parameter.isAnnotationPresent(RequestBody.class)) {
                this.body = BodyInserters.fromObject(args[i]);
            } else if (parameter.isAnnotationPresent(RequestHeader.class)) {
                addHeader(parameter, args[i]);
            } else if (parameter.isAnnotationPresent(RequestParam.class)) {
                addParameter(parameter, args[i]);
            } else if (parameter.isAnnotationPresent(PathVariable.class)) {
                addPathVariable(parameter, args[i]);
            }
        }
    }

    private void addPathVariable(Parameter parameter, Object arg) {
        PathVariable pathVariable = AnnotationUtils
                .findAnnotation(parameter, PathVariable.class);
        pathVariables.put(Objects.requireNonNull(pathVariable).value(), arg);
    }

    private void addParameter(Parameter parameter, Object arg) {
        RequestParam param = AnnotationUtils
                .findAnnotation(parameter, RequestParam.class);
        params.put(Objects.requireNonNull(param).value(), arg);
    }

    private void addHeader(Parameter parameter, Object arg) {
        RequestHeader header = AnnotationUtils
                .findAnnotation(parameter, RequestHeader.class);
        headers.put(Objects.requireNonNull(header).value(), arg.toString());
    }

    private void buildMethod(RequestMapping requestMapping) {
        this.method =
                requestMapping.method().length <= 1 ? RequestMethod.GET
                        : RequestMethod.valueOf(
                        Objects.requireNonNull(HttpMethod.resolve(requestMapping.method()[0].name())).name());
    }

    private void buildUri(RequestMapping requestMapping) {
        this.uri = UriComponentsBuilder
                .fromUriString(requestMapping.value().length == 0 ? "/" : requestMapping.value()[0])
                .build(this.pathVariables)
                .toString();
    }

}
