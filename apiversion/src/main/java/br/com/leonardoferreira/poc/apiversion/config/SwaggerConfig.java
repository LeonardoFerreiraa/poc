package br.com.leonardoferreira.poc.apiversion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.condition.NameValueExpression;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiV1Url() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api-v1-url")
                .select()
                .paths(PathSelectors.regex("/v1/*"))
                .build();
    }

    @Bean
    public Docket apiV1Header() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api-v1-header")
                .select()
                .apis(it -> {
                    if (it != null && it.headers() != null) {
                        for (NameValueExpression<String> header : it.headers()) {
                            if (header.getName().equals("Api-Version") && header.getValue().equals("1")) {
                                return true;
                            } else if (header.getName().equals("Accept") && header.getValue().equals("application/vnd.poc.v1+json")) {
                                return true;
                            }
                        }
                    }
                    return false;
                })
                .build();
    }

    @Bean
    public Docket apiV2Url() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api-v2-url")
                .select()
                .paths(PathSelectors.regex("/v2/*"))
                .build();
    }

    @Bean
    public Docket apiV2Header() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api-v2-header")
                .select()
                .apis(it -> {
                    if (it != null && it.headers() != null) {
                        for (NameValueExpression<String> header : it.headers()) {
                            if (header.getName().equals("Api-Version") && header.getValue().equals("2")) {
                                return true;
                            } else if (header.getName().equals("Accept") && header.getValue().equals("application/vnd.poc.v2+json")) {
                                return true;
                            }
                        }
                    }
                    return false;
                })
                .build();
    }

}
