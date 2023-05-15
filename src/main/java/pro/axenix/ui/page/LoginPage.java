package pro.axenix.ui.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pro.axenix.configuration.User;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    @Step("Авторизоваться под пользователем")
    public void login(User user) {
        $("#username").setValue(user.getName());
        $("#password").setValue(user.getPassword());
        $(By.tagName("button")).click();
    }
}
