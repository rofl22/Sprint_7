

import api.CourierApi;
import io.qameta.allure.junit4.DisplayName;
import models.Courier;
import models.CourierCredentials;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class CourierCreateTest extends BaseTest {
    private CourierApi courierApi = new CourierApi();

    @Test
    @DisplayName("Успешное создание курьера")
    public void testCreateCourierSuccess() {
        String login = "ninja_" + System.currentTimeMillis();
        Courier courier = new Courier(login, "1234", "saske");

        courierApi.createCourier(courier)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));

        // Сохраняем ID для удаления в tearDown
        courierId = courierApi.getCourierId(
                new CourierCredentials(login, "1234")
        );
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров возвращает ошибку")
    public void testCreateDuplicateCourier() {
        String uniqueLogin = "duplicate_" + System.currentTimeMillis();
        Courier courier = new Courier(uniqueLogin, "1234", "saske");

        // Первое создание
        courierApi.createCourier(courier)
                .then()
                .statusCode(201);

        // Сохраняем ID для удаления
        courierId = courierApi.getCourierId(
                new CourierCredentials(uniqueLogin, "1234")
        );

        // Второе создание - должно вернуть ошибку
        courierApi.createCourier(courier)
                .then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера без логина возвращает ошибку")
    public void testCreateCourierWithoutLogin() {
        Courier invalidCourier = new Courier(null, "1234", "saske");

        courierApi.createCourier(invalidCourier)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля возвращает ошибку")
    public void testCreateCourierWithoutPassword() {
        Courier invalidCourier = new Courier("ninja_" + System.currentTimeMillis(), null, "saske");

        courierApi.createCourier(invalidCourier)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без имени возвращает успех")
    public void testCreateCourierWithoutFirstName() {
        String login = "noname_" + System.currentTimeMillis();
        Courier courier = new Courier(login, "1234", null);

        courierApi.createCourier(courier)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));

        // Сохраняем ID для удаления
        courierId = courierApi.getCourierId(
                new CourierCredentials(login, "1234")
        );
    }
}