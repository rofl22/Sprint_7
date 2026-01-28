package api;

import io.restassured.response.Response;
import models.Order;

public class OrderApi {

    public static Response createOrder(Order order) {
        return ApiClient.getRequestSpec(order)
                .when()
                .post("/api/v1/orders");
    }

    public static Response getOrdersList() {
        return ApiClient.getRequestSpec()
                .when()
                .get("/api/v1/orders");
    }

    public static Response getOrdersListWithParams(Integer limit, Integer page, String nearestStation) {
        return ApiClient.getRequestSpec()
                .param("limit", limit)
                .param("page", page)
                .param("nearestStation", nearestStation)
                .when()
                .get("/api/v1/orders");
    }

    public static Response cancelOrder(String trackId) {
        return ApiClient.getRequestSpec()
                .when()
                .put("/api/v1/orders/cancel?track=" + trackId);
    }

    public static Response getOrderByTrack(String trackId) {
        return ApiClient.getRequestSpec()
                .param("t", trackId)
                .when()
                .get("/api/v1/orders/track");
    }
}