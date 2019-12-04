package br.com.leonardoferreira.poc.config.database;

import org.testcontainers.containers.PostgreSQLContainer;

public final class PostgresContainer extends PostgreSQLContainer {

    public static final PostgresContainer INSTANCE;

    private PostgresContainer() {
        super("postgres:10.9");
    }

    static {
        PostgresContainer singleton = new PostgresContainer();
        INSTANCE = singleton;
        singleton.withDatabaseName("poc_spring_data_jpa");
        singleton.start();
    }

}
