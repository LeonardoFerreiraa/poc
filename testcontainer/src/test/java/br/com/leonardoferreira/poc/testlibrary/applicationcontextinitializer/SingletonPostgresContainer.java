package br.com.leonardoferreira.poc.testlibrary.applicationcontextinitializer;

import org.testcontainers.containers.PostgreSQLContainer;

public class SingletonPostgresContainer extends PostgreSQLContainer<SingletonPostgresContainer> {

    private static final SingletonPostgresContainer INSTANCE = new SingletonPostgresContainer();

    private SingletonPostgresContainer() {
        super("postgres:10.9");
        withDatabaseName("poc_testcontainer_test");
        start();
    }

    public static SingletonPostgresContainer getInstance() {
        return INSTANCE;
    }

}
