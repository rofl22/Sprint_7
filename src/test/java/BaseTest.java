
import api.CourierApi;
import io.qameta.allure.Step;
import models.Courier;
import models.CourierCredentials;
import org.junit.After;
import org.junit.Before;

public class BaseTest {
    protected CourierApi courierApi = new CourierApi();
    protected String courierId;
    protected String testCourierLogin;

    @Before
    public void setUp() {
        // Базовая инициализация
    }

    @After
    public void tearDown() {
        cleanupCourier();
    }

    @Step("Очистка: удаление курьера если он был создан")
    protected void cleanupCourier() {
        if (courierId != null && !courierId.isEmpty()) {
            try {
                courierApi.deleteCourier(courierId)
                        .then()
                        .statusCode(200);
            } catch (AssertionError e) {
                // Если курьер не найден (404) или другая ошибка - просто логируем
                System.err.println("Не удалось удалить курьера с ID " + courierId +
                        ". Возможно, он уже был удален или не существует.");
            } catch (Exception e) {
                System.err.println("Ошибка при удалении курьера: " + e.getMessage());
            }
        }
    }

    @Step("Создание тестового курьера и получение его ID")
    protected String createAndGetCourierId(String login, String password, String firstName) {
        Courier courier = new Courier(login, password, firstName);

        // Создаем курьера
        courierApi.createCourier(courier)
                .then()
                .statusCode(201);

        // Получаем ID
        CourierCredentials credentials = new CourierCredentials(login, password);
        courierId = courierApi.getCourierId(credentials);
        return courierId;
    }

    @Step("Логин и получение ID курьера")
    protected String loginAndGetId(String login, String password) {
        CourierCredentials credentials = new CourierCredentials(login, password);
        courierId = courierApi.getCourierId(credentials);
        return courierId;
    }
}