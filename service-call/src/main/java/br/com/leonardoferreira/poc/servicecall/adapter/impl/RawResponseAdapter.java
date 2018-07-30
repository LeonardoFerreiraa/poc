package br.com.leonardoferreira.poc.servicecall.adapter.impl;

import br.com.leonardoferreira.poc.servicecall.adapter.ResponseAdapter;

public class RawResponseAdapter<T> implements ResponseAdapter<T, T> {

    @Override
    public T adapt(T obj) {
        return obj;
    }

}
