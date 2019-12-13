package br.com.leonardoferreira.webflux.domain;

import lombok.Data;

@Data
public class AnythingResponse {

    private Json json;

    @Data
    public static class Json {
        private Integer number;
    }
}
