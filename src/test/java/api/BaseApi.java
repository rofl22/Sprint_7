package api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.config.HttpClientConfig.httpClientConfig;

public class BaseApi {
    protected static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    static {
        // Устанавливаем baseUri один раз для всего проекта
        RestAssured.baseURI = BASE_URL;

        // Настраиваем таймауты
        RestAssured.config = RestAssuredConfig.config()
                .httpClient(httpClientConfig()
                        .setParam("http.connection.timeout", 30000)  // 30 секунд
                        .setParam("http.socket.timeout", 30000)      // 30 секунд
                        .setParam("http.connection-manager.timeout", 30000));
    }

    protected RequestSpecification getBaseRequestSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URL)
                .setConfig(RestAssuredConfig.config()
                        .httpClient(httpClientConfig()
                                .setParam("http.connection.timeout", 30000)
                                .setParam("http.socket.timeout", 30000)
                                .setParam("http.connection-manager.timeout", 30000)))
                // Добавляем фильтры для логирования в Allure
                .addFilter(new AllureRestAssured())
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }
}