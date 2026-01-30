package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.Order;

import static io.restassured.RestAssured.given;

public class OrderApi extends BaseApi {

    @Step("Создание заказа")
    public Response createOrder(Order order) {
        return given()
                .spec(getBaseRequestSpec())
                .body(order)
                .post("/api/v1/orders");
    }

    @Step("Получение списка заказов")
    public Response getOrdersList() {
        return given()
                .spec(getBaseRequestSpec())
                .when()
                .get("/api/v1/orders");
    }

    @Step("Получение списка заказов с лимитом: {limit}")
    public Response getOrdersListWithLimit(Integer limit) {
        return given()
                .spec(getBaseRequestSpec())
                .param("limit", limit)
                .when()
                .get("/api/v1/orders");
    }

    @Step("Получение списка заказов с параметрами: страница {page}, лимит {limit}")
    public Response getOrdersListWithParams(Integer page, Integer limit) {
        return given()
                .spec(getBaseRequestSpec())
                .param("page", page)
                .param("limit", limit)
                .when()
                .get("/api/v1/orders");
    }

    @Step("Получение списка заказов с фильтрацией по станциям метро: {nearestStation}")
    public Response getOrdersListWithMetroStationFilter(String nearestStation) {
        return given()
                .spec(getBaseRequestSpec())
                .param("nearestStation", nearestStation)
                .when()
                .get("/api/v1/orders");
    }
}