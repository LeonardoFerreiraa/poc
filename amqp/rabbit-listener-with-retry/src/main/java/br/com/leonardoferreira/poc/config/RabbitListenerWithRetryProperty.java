package br.com.leonardoferreira.poc.config;

import lombok.Data;

@Data
class RabbitListenerWithRetryProperty {

    private String exchange;

    private String queue;

    private String routingKey = "";

    private String retry;

    private String dlq;

    private int concurrency = 1;

    private boolean createDlq = true;

    private boolean createRetry = true;

    public String getRetry() {
        return retry == null ? queue + ".retry" : retry;
    }

    public String getDlq() {
        return dlq == null ? queue + ".dlq" : dlq;
    }
}
