package pro.test.ui.page;

import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class RenderedDynamicallyElementPage {
    @Step("Проверка заголовка")
    public void checkHeader() {
        $x("//h4").shouldHave(text("Example 2: Element rendered after the fact"));
    }

    @Step("Нажать кнопку старт")
    public void clickStartButton() {
        $("#start > button").click();
        $("#loading").shouldBe(visible);
    }

    @Step("Ожидать появление элемента {sec} секунд")
    public void waitHelloWorld(int sec) {
        $("#finish > h4").shouldBe(exist, Duration.ofSeconds(sec)).shouldBe(visible);
    }

}
