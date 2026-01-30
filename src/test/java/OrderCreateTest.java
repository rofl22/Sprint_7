

import api.OrderApi;
import io.qameta.allure.junit4.DisplayName;
import models.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class OrderCreateTest extends BaseTest {
    private final Order order;
    private final String description;
    private OrderApi orderApi = new OrderApi();

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
                {
                        new Order("Sasuke", "Uchiha", "Konoha, 143 apt.", "5",
                                "+7 800 355 35 36", 3, "2024-06-07",
                                "Waiting for Naruto", Arrays.asList("GREY")),
                        "С цветом GREY"
                },
                {
                        new Order("Sakura", "Haruno", "Konoha, 144 apt.", "6",
                                "+7 800 355 35 37", 4, "2024-06-08",
                                "Medical ninja", Arrays.asList("BLACK", "GREY")),
                        "С двумя цветами"
                },
                {
                        new Order("Kakashi", "Hatake", "Konoha, 145 apt.", "7",
                                "+7 800 355 35 38", 2, "2024-06-09",
                                "Copy ninja", null),
                        "Без указания цвета"
                }
        };
    }

    @Test
    @DisplayName("Создание заказа с разными параметрами")
    public void testCreateOrderWithDifferentColors() {
        try {
            orderApi.createOrder(order)
                    .then()
                    .statusCode(201)
                    .body("track", notNullValue());
        } catch (Exception e) {
            // Если произошел таймаут, повторяем попытку
            System.out.println("Повторная попытка создания заказа после таймаута...");
            orderApi.createOrder(order)
                    .then()
                    .statusCode(201)
                    .body("track", notNullValue());
        }
    }
}