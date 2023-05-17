package pro.test.ui.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class CheckBoxesPage {
    @Step("Проверка заголовка")
    public void checkHeader() {
        $x("//h3").shouldHave(text("Checkboxes"));
    }

    @Step("Установить checkBox{checkBoxNumber} в состояние {state}")
    public void setCheckBox(boolean state, int checkBoxNumber) {
        SelenideElement element = $x(String.format("//*[@id='checkboxes']/input[%d]", checkBoxNumber));
        if (state) {
            element.setSelected(state);
            element.shouldBe(selected);
            element.shouldBe(checked);

        } else {
            element.setSelected(state);
            element.shouldNotBe(selected);
            element.shouldNotBe(checked);
        }
    }
}
