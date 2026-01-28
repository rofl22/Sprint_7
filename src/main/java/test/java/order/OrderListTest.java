package test.java.order;

import api.OrderApi;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import test.java.BaseTest;

import static org.hamcrest.Matchers.*;

public class OrderListTest extends BaseTest {

    @Test
    @DisplayName("Получение списка заказов")
    public void testGetOrdersList() {
        OrderApi.getOrdersList()
                .then()
                .statusCode(200)
                .body("orders", notNullValue())
                .body("pageInfo", notNullValue())
                .body("orders.size()", greaterThan(0));
    }

    @Test
    @DisplayName("Получение списка заказов с лимитом")
    public void testGetOrdersListWithLimit() {
        OrderApi.getOrdersListWithParams(10, null, null)
                .then()
                .statusCode(200)
                .body("orders.size()", lessThanOrEqualTo(10))
                .body("pageInfo.limit", equalTo(10));
    }

    @Test
    @DisplayName("Получение списка заказов с указанием страницы")
    public void testGetOrdersListWithPage() {
        OrderApi.getOrdersListWithParams(5, 1, null)
                .then()
                .statusCode(200)
                .body("pageInfo.page", equalTo(1));
    }

    @Test
    @DisplayName("Получение списка заказов с фильтрацией по станциям метро")
    public void testGetOrdersListWithMetroStationFilter() {
        OrderApi.getOrdersListWithParams(null, null, "[\"1\", \"2\"]")
                .then()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}