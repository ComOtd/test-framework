package pro.test.ui;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

@Slf4j
public class AllureUILogger implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(false));
    }
}
