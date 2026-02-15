import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldSuccessfullySubmitForm() {
        String date = generateDate(4); // дата через 4 дня от текущей

        // Заполняем город
        $("[data-test-id='city'] input").setValue("Казань");

        // Заполняем дату
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date);

        // Заполняем имя
        $("[data-test-id='name'] input").setValue("Иванов Иван");

        // Заполняем телефон
        $("[data-test-id='phone'] input").setValue("+79000000000");

        // Ставим галочку согласия
        $("[data-test-id='agreement']").click();

        // Отправляем форму
        $$("button").find(exactText("Забронировать")).click();

        // Проверяем успешную отправку
        $("[data-test-id='notification']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Успешно! Встреча успешно забронирована"));
    }
}