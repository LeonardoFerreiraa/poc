package br.com.leonardoferreira.poc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

class CustomHandlerAdviceTest {

    @Test
    void testBla() {
        final DefaultPropertyMapping defaultPropertyMapping = new SpringApplicationBuilder(SampleConfiguration.class)
                .web(WebApplicationType.NONE)
                .run("--spring.jmx.enabled=false",
                        "my-properties.properties.firstCustom.firstProperty=teste",
                        "my-properties.properties.firstCustom.secondProperty=false",
                        "my-properties.properties.secondCustom.firstProperty=etset",
                        "my-properties.properties.secondCustom.forthProperty=1226",
                        "my-properties.default.secondProperty=true",
                        "my-properties.default.forthProperty=2612"
                )
                .getBean(DefaultPropertyMapping.class);

        final CustomProperty firstCustom = defaultPropertyMapping.getProperties().get("firstCustom");
        Assertions.assertAll("firstCustom",
                () -> Assertions.assertEquals("teste", firstCustom.getFirstProperty()),
                () -> Assertions.assertFalse(firstCustom.isSecondProperty()),
                () -> Assertions.assertEquals(300, firstCustom.getThirdProperty()),
                () -> Assertions.assertEquals(Long.valueOf(2612), firstCustom.getForthProperty())
        );

        final CustomProperty secondCustom = defaultPropertyMapping.getProperties().get("secondCustom");
        Assertions.assertAll("secondCustom",
                () -> Assertions.assertEquals("etset", secondCustom.getFirstProperty()),
                () -> Assertions.assertTrue(secondCustom.isSecondProperty()),
                () -> Assertions.assertEquals(300, secondCustom.getThirdProperty()),
                () -> Assertions.assertEquals(Long.valueOf(1226), secondCustom.getForthProperty())
        );
    }

}

@SpringBootApplication
@EnableConfigurationProperties(DefaultPropertyMapping.class)
class SampleConfiguration {
}
