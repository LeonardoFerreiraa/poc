package br.com.leonardoferreira.poc.httpclient;

import br.com.leonardoferreira.poc.httpclient.annotation.Body;
import br.com.leonardoferreira.poc.httpclient.annotation.Client;
import br.com.leonardoferreira.poc.httpclient.annotation.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GenericClientImpl implements InvocationHandler {

    private final OkHttpClient client;
    private final Gson gson;

    public GenericClientImpl() {
        this.client = new OkHttpClient();
        this.gson = new GsonBuilder()
                .create();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(HttpRequest.class)) {
            HttpRequest httpRequest = method.getAnnotation(HttpRequest.class);
            Client clientAnnotation = method.getDeclaringClass().getAnnotation(Client.class);

            String uri = clientAnnotation.url() + httpRequest.url();
            Response response = doRequest(uri, httpRequest.method().name(), buildRequestBody(method, args));
            return buildResponseBody(method, response);
        }

        return null;
    }

    private Object buildResponseBody(Method method, Response response) throws IOException {
        ResponseBody body = response.body();
        String bodyString = body == null ? null : body.string();

        if (method.getReturnType().equals(String.class)) {
            return bodyString;
        } else if (method.getReturnType().equals(Response.class)) {
            return response;
        }

        return body == null ? null : gson.fromJson(bodyString, method.getReturnType());
    }

    private Response doRequest(String uri, String method, RequestBody body) throws IOException {
        Request req = new Request.Builder()
                .url(uri)
                .method(method, body)
                .build();

        return client.newCall(req).execute();
    }

    private RequestBody buildRequestBody(Method method, Object[] args) {
        Parameter[] parameters = method.getParameters();

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (parameter.isAnnotationPresent(Body.class)) {
                return RequestBody.create(MediaType.get("application/json"), gson.toJson(args[i]));
            }
        }

        return null;
    }

}
