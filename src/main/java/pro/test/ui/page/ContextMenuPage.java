package pro.test.ui.page;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class ContextMenuPage {
    @Step("Проверка заголовка")
    public void checkHeader() {
        $x("//h3").shouldHave(text("Context Menu"));
    }

    @Step("Вызвать контекстное меню")
    public void callContextMenu() {
        $(By.id("hot-spot")).contextClick();
    }

    @Step("Проверить всплывающее окно")
    public void checkPopUp() {
        Assertions.assertEquals(switchTo().alert().getText(), "You selected a context menu");
    }

    @Step("Принять всплывающее окно")
    public void acceptPopUp() {
        switchTo().alert().accept();
    }
}
