package test.java.order;

import api.OrderApi;
import io.qameta.allure.junit4.DisplayName;
import models.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import test.java.BaseTest;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class OrderCreateTest extends BaseTest {

    private final Order order;
    private final String description;

    public OrderCreateTest(Order order, String description) {
        this.order = order;
        this.description = description;
    }

    @Parameterized.Parameters(name = "Тест создания заказа: {1}")
    public static Object[][] getTestData() {
        return new Object[][] {
                {
                        new Order("Naruto", "Uchiha", "Konoha, 142 apt.", "4",
                                "+7 800 355 35 35", 5, "2024-06-06",
                                "Saske, come back to Konoha", Arrays.asList("BLACK")),
                        "С цветом BLACK"
                },
                // ... остальные тестовые данные
        };
    }

    @Test
    @DisplayName("Создание заказа с разными параметрами")
    public void testCreateOrderWithDifferentColors() {
        OrderApi.createOrder(order)
                .then()
                .statusCode(201)
                .body("track", notNullValue());
    }
}