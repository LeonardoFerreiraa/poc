package br.com.leonardoferreira.poc.testlibrary.testexecutionlistener;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

@Slf4j
@Component
public class RestAssuredExecutionListener implements TestExecutionListener {

    @Override
    public void beforeTestMethod(final TestContext testContext) throws Exception {
        RestAssured.port = testContext.getApplicationContext()
                .getEnvironment()
                .getProperty("local.server.port", Integer.class, RestAssured.DEFAULT_PORT);

        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

}
