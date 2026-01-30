

import api.OrderApi;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class OrderListTest extends BaseTest {
    private OrderApi orderApi = new OrderApi();

    @Test
    @DisplayName("Получение списка заказов")
    public void testGetOrdersList() {
        orderApi.getOrdersList()
                .then()
                .statusCode(200)
                .body("orders", notNullValue())
                .body("pageInfo", notNullValue())
                .body("orders.size()", greaterThan(0));
    }

    @Test
    @DisplayName("Получение списка заказов с лимитом")
    public void testGetOrdersListWithLimit() {
        orderApi.getOrdersListWithLimit(10)
                .then()
                .statusCode(200)
                .body("orders.size()", lessThanOrEqualTo(10))
                .body("pageInfo.limit", equalTo(10));
    }

    @Test
    @DisplayName("Получение списка заказов с указанием страницы")
    public void testGetOrdersListWithPage() {
        orderApi.getOrdersListWithParams(1, 5)
                .then()
                .statusCode(200)
                .body("pageInfo.page", equalTo(1));
    }

    @Test
    @DisplayName("Получение списка заказов с фильтрацией по станциям метро")
    public void testGetOrdersListWithMetroStationFilter() {
        orderApi.getOrdersListWithMetroStationFilter("[\"1\", \"2\"]")
                .then()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}