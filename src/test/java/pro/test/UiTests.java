package pro.test;

import com.codeborne.selenide.Condition;
import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import pro.test.configuration.*;
import pro.test.ui.AllureUILogger;
import pro.test.ui.page.LoginPage;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Epic("Test for test-framework ")
@Feature("UI tests")
@Owner("Osipov Semen")
@DisplayName("Первые UI тесты")
@ExtendWith({CredentialsParameter.class, AllureUILogger.class})
class UiTests {
    private static EnvironmentConfiguration configuration;
    private final Credentials credentials;

    UiTests(Credentials credentials) {
        this.credentials = credentials;
    }

    @BeforeAll
    static void before() throws IOException {
        configuration = new EnvironmentConfigurationProvider().get();
    }


    @Test
    @Link(type = "tms", name = "b9c6565c-3b25-40b1-a7b1-8f37bc31c1a5", value = "Тест из TMS")
    @Link(type = "manual", name = "1414895", value = "Ручной тест")
    @Tag("UI")
    @Story("Simple Login")
    @DisplayName("Проверка успешной авторизации")
    void testLogin() {
        LoginPage loginPage = Allure.step("Открыть страницу авторизации",
                () -> open(configuration.getHttpHostUrl("login"), LoginPage.class));
        User admin = credentials.getUserByRole("Admin");
        loginPage.login(admin);
        Allure.step("Проверить успешность авторизации", () -> {
            $(By.id("flash")).shouldHave(Condition.text("You logged into a secure area!"));
        });
    }
}
