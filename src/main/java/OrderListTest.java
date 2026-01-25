import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class OrderListTest extends BaseTest {

    @Test
    @DisplayName("Получение списка заказов")
    public void testGetOrdersList() {
        given()
                .when()
                .get("/api/v1/orders")
                .then()
                .statusCode(200)
                .body("orders", notNullValue())
                .body("pageInfo", notNullValue())
                .body("orders.size()", greaterThan(0));
    }

    @Test
    @DisplayName("Получение списка заказов с лимитом")
    public void testGetOrdersListWithLimit() {
        given()
                .param("limit", 10)
                .when()
                .get("/api/v1/orders")
                .then()
                .statusCode(200)
                .body("orders.size()", lessThanOrEqualTo(10))
                .body("pageInfo.limit", equalTo(10));
    }

    @Test
    @DisplayName("Получение списка заказов с указанием страницы")
    public void testGetOrdersListWithPage() {
        given()
                .param("page", 1)
                .param("limit", 5)
                .when()
                .get("/api/v1/orders")
                .then()
                .statusCode(200)
                .body("pageInfo.page", equalTo(1));
    }

    @Test
    @DisplayName("Получение списка заказов с фильтрацией по станциям метро")
    public void testGetOrdersListWithMetroStationFilter() {
        given()
                .param("nearestStation", "[\"1\", \"2\"]")
                .when()
                .get("/api/v1/orders")
                .then()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}