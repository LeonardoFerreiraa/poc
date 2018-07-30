package br.com.leonardoferreira.poc.servicecall.adapter;

@FunctionalInterface
public interface ResponseAdapter<RES, REQ> {

    RES adapt(REQ obj);

}
