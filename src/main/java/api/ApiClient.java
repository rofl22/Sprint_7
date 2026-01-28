package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiClient {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private static boolean isBaseUriSet = false;

    static {
        if (!isBaseUriSet) {
            RestAssured.baseURI = BASE_URL;
            isBaseUriSet = true;
        }
    }

    public static RequestSpecification getRequestSpec() {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .log().all(); // Опционально, для логирования
    }

    public static RequestSpecification getRequestSpec(Object body) {
        return getRequestSpec()
                .body(body);
    }
}