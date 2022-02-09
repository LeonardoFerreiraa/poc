package br.com.leonardoferreira.poc;

public interface Cache<T> {

    boolean contains(T t);

    void add(T t);

}
