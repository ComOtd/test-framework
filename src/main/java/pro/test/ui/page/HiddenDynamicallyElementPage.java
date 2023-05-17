package pro.test.ui.page;

import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class HiddenDynamicallyElementPage {
    @Step("Проверка заголовка")
    public void checkHeader() {
        $x("//h4").shouldHave(text("Example 1: Element on page that is hidden"));
    }

    @Step("Нажать кнопку старт")
    public void clickStartButton() {
        $("#start > button").click();
        $("#loading").shouldBe(visible);
    }

    @Step("Ожидать появление элемента {sec} секунж")
    public void waitHelloWorld(int sec) {
        $("#finish > h4").shouldBe(visible, Duration.ofSeconds(sec));
    }

}
