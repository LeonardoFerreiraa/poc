package br.com.leonardoferreira.poc.config;

import lombok.Data;

@Data
class TypeMetadata {

    static final TypeMetadata EMPTY = new TypeMetadata();

    private final ListenerMethod[] listenerMethods;

    private TypeMetadata() {
        this.listenerMethods = new ListenerMethod[0];
    }

    TypeMetadata(final ListenerMethod[] methods) {
        this.listenerMethods = methods;
    }

}
