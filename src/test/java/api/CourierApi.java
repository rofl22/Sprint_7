package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.Courier;
import models.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierApi extends BaseApi {

    @Step("Создание курьера")
    public Response createCourier(Courier courier) {
        return given()
                .spec(getBaseRequestSpec())
                .body(courier)
                .post("/api/v1/courier");
    }

    @Step("Логин курьера")
    public Response loginCourier(CourierCredentials credentials) {
        return given()
                .spec(getBaseRequestSpec())
                .body(credentials)
                .post("/api/v1/courier/login");
    }

    @Step("Удаление курьера по ID: {courierId}")
    public Response deleteCourier(String courierId) {
        return given()
                .spec(getBaseRequestSpec())
                .delete("/api/v1/courier/" + courierId);
    }

    @Step("Получение ID курьера после логина")
    public String getCourierId(CourierCredentials credentials) {
        Response response = loginCourier(credentials);
        response.then().statusCode(200);
        return response.then().extract().path("id").toString();
    }

    @Step("Создание курьера и получение его ID")
    public String createCourierAndGetId(Courier courier) {
        Response createResponse = createCourier(courier);
        createResponse.then().statusCode(201);

        CourierCredentials credentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        return getCourierId(credentials);
    }
}