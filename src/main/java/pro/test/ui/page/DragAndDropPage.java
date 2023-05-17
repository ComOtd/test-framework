package pro.test.ui.page;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class DragAndDropPage {

    @Step("Поменять квадрат A и B местами")
    public void dragAndDropAB() {
        $("#column-a > header").shouldHave(text("A"));
        $("#column-b > header").shouldHave(text("B"));
        $("#column-a > header").dragAndDropTo($("#column-b > header"));
        $("#column-a > header").shouldHave(text("B"));
        $("#column-b > header").shouldHave(text("A"));
    }


}
