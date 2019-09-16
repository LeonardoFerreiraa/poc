package br.com.leonardoferreira.poc.restdocs.testexecutionlistener;

import io.restassured.RestAssured;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

public class RestAssuredTestExecutionListener implements TestExecutionListener {

    @Override
    public void beforeTestExecution(final TestContext testContext) throws Exception {
        final Environment environment = testContext.getApplicationContext().getEnvironment();

        RestAssured.port = environment.getProperty("local.server.port", Integer.class, RestAssured.UNDEFINED_PORT);
        RestAssured.basePath = environment.getProperty("server.servlet.context-path", "/");
    }

}
