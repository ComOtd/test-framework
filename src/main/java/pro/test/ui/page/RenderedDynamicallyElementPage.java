package pro.test.ui.page;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class RenderedDynamicallyElementPage {

    @Step("Нажать кнопку старт")
    public void clickStartButton() {
        $("#start > button").click();
        $("#loading").shouldBe(visible);
    }

}
