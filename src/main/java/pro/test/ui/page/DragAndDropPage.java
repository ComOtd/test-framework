package pro.test.ui.page;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class DragAndDropPage {
    @Step("Проверка заголовка")
    public void checkHeader() {
        $x("//h3").shouldHave(text("Drag and Drop"));
    }

    @Step("Поменять квадрат A и B местами")
    public void dragAndDropAB() {
        $("#column-a > header").shouldHave(text("A"));
        $("#column-b > header").shouldHave(text("B"));
        $("#column-a > header").dragAndDropTo($("#column-b > header"));
        $("#column-a > header").shouldHave(text("B"));
        $("#column-b > header").shouldHave(text("A"));
    }


}
