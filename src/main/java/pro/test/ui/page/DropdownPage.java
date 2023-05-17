package pro.test.ui.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DropdownPage {
    @Step("Проверка заголовка")
    public void checkHeader() {
        $x("//h3").shouldHave(text("Dropdown List"));
    }

    @Step("Установить значение {value} у выпадающего списка")
    public void setDropdown(String value) {
        SelenideElement element = $(By.id("dropdown"));
        element.selectOption(value);
        element.shouldHave(text(value));

    }

}
