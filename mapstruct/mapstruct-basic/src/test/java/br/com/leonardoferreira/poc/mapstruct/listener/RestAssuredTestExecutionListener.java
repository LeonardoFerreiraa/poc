package br.com.leonardoferreira.poc.mapstruct.listener;

import io.restassured.RestAssured;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

public class RestAssuredTestExecutionListener implements TestExecutionListener {

    @Override
    public void beforeTestMethod(final TestContext testContext) throws Exception {
        RestAssured.port = testContext.getApplicationContext()
                .getEnvironment()
                .getProperty("local.server.port", Integer.class, 0);
    }

}
