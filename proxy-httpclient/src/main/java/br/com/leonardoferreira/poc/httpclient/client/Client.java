package br.com.leonardoferreira.poc.httpclient.client;

import br.com.leonardoferreira.poc.httpclient.request.RequestAttributes;

public interface Client<T> {

    T doRequest(RequestAttributes requestAttributes);

}
