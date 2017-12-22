package br.com.leonardoferreira.hello;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by lferreira on 6/30/17.
 */
@RunWith(Cucumber.class)
@CucumberOptions(format = "pretty", features = "src/test/resources/features")
public class CucumberTest {
}
