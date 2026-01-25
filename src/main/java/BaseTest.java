import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Courier;
import models.CourierCredentials;
import org.junit.After;
import org.junit.Before;

import static io.restassured.RestAssured.given;

public class BaseTest {

    protected static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    protected String courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @After
    public void tearDown() {
        // Удаление курьера после тестов, если он был создан
        if (courierId != null) {
            given()
                    .delete("/api/v1/courier/" + courierId)
                    .then()
                    .statusCode(200);
        }
    }

    // Вспомогательный метод для создания курьера и получения ID
    protected String createCourierAndGetId(Courier courier) {
        // Сначала создаем курьера
        given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201);

        // Затем логинимся, чтобы получить ID
        CourierCredentials creds = new CourierCredentials(courier.getLogin(), courier.getPassword());
        Response loginResponse = given()
                .header("Content-type", "application/json")
                .body(creds)
                .when()
                .post("/api/v1/courier/login");

        return loginResponse.then().extract().path("id").toString();
    }

    // Вспомогательный метод только для создания курьера (без получения ID)
    protected void createCourier(Courier courier) {
        given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201);
    }

    // Вспомогательный метод для логина и получения ID
    protected String loginAndGetId(String login, String password) {
        CourierCredentials creds = new CourierCredentials(login, password);
        Response loginResponse = given()
                .header("Content-type", "application/json")
                .body(creds)
                .when()
                .post("/api/v1/courier/login");

        return loginResponse.then().extract().path("id").toString();
    }
}