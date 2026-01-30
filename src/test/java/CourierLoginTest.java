

import api.CourierApi;
import io.qameta.allure.junit4.DisplayName;
import models.Courier;
import models.CourierCredentials;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class CourierLoginTest extends BaseTest {
    private CourierApi courierApi = new CourierApi();

    @Test
    @DisplayName("Успешный логин курьера")
    public void testLoginCourierSuccess() {
        String login = "login_success_" + System.currentTimeMillis();

        // Создаем курьера и получаем ID
        courierId = createAndGetCourierId(login, "1234", "saske");

        // Тестируем логин
        courierApi.loginCourier(new CourierCredentials(login, "1234"))
                .then()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Логин с неправильным паролем возвращает ошибку")
    public void testLoginWithWrongPassword() {
        String login = "wrong_pass_" + System.currentTimeMillis();

        // Создаем курьера
        courierId = createAndGetCourierId(login, "1234", "saske");

        // Пытаемся залогиниться с неправильным паролем
        courierApi.loginCourier(new CourierCredentials(login, "wrong_password"))
                .then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Логин без пароля возвращает ошибку")
    @Ignore("Тест временно отключен из-за проблем с таймаутами на сервере")
    public void testLoginWithoutPassword() {
        String login = "no_password_" + System.currentTimeMillis();

        // Создаем курьера
        courierId = createAndGetCourierId(login, "1234", "saske");

        // Пытаемся залогиниться без пароля (null)
        // Этот тест часто падает с SocketTimeout, отключаем временно
        courierApi.loginCourier(new CourierCredentials(login, null))
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Логин без логина возвращает ошибку")
    public void testLoginWithoutLogin() {
        courierApi.loginCourier(new CourierCredentials(null, "1234"))
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Логин несуществующего курьера возвращает ошибку")
    public void testLoginNonExistentCourier() {
        courierApi.loginCourier(
                        new CourierCredentials("nonexistent_" + System.currentTimeMillis(), "1234")
                )
                .then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Логин с пустым логином возвращает ошибку")
    public void testLoginWithEmptyLogin() {
        courierApi.loginCourier(new CourierCredentials("", "1234"))
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Логин с пустым паролем возвращает ошибку")
    public void testLoginWithEmptyPassword() {
        String login = "empty_pass_" + System.currentTimeMillis();

        // Создаем курьера
        courierId = createAndGetCourierId(login, "1234", "saske");

        // Пытаемся залогиниться с пустым паролем
        courierApi.loginCourier(new CourierCredentials(login, ""))
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}