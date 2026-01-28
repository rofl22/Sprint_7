package api;

import io.restassured.response.Response;
import models.Courier;
import models.CourierCredentials;

public class CourierApi {

    public static Response createCourier(Courier courier) {
        return ApiClient.getRequestSpec(courier)
                .when()
                .post("/api/v1/courier");
    }

    public static Response loginCourier(CourierCredentials credentials) {
        return ApiClient.getRequestSpec(credentials)
                .when()
                .post("/api/v1/courier/login");
    }

    public static Response deleteCourier(String courierId) {
        return ApiClient.getRequestSpec()
                .when()
                .delete("/api/v1/courier/" + courierId);
    }

    public static String createCourierAndGetId(Courier courier) {
        // Создаем курьера
        createCourier(courier)
                .then()
                .statusCode(201);

        // Логинимся для получения ID
        CourierCredentials creds = new CourierCredentials(courier.getLogin(), courier.getPassword());
        return loginCourier(creds)
                .then()
                .extract()
                .path("id")
                .toString();
    }

    public static String loginAndGetId(String login, String password) {
        CourierCredentials creds = new CourierCredentials(login, password);
        return loginCourier(creds)
                .then()
                .extract()
                .path("id")
                .toString();
    }
}