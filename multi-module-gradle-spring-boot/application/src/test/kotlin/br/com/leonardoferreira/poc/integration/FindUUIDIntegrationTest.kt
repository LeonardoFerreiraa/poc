package br.com.leonardoferreira.poc.integration

import io.restassured.RestAssured
import org.apache.http.HttpStatus.SC_OK
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class FindUUIDIntegrationTest {

    @Test
    fun `should return UUID`() {
//        HttpBinClient
        RestAssured
            .given()
                .log().all()
            .`when`()
                .get("/uuid")
            .then()
                .log().all()
                .statusCode(SC_OK)
    }

}
