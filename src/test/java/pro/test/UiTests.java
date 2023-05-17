package pro.test;

import com.codeborne.selenide.Condition;
import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import pro.test.configuration.*;
import pro.test.ui.AllureUILogger;
import pro.test.ui.page.*;

import java.io.IOException;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

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

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    @Tag("UI")
    @Story("Checkboxes")
    @DisplayName("Проверка работы Checkboxes")
    void testCheckboxes(int checkboxNumber) {
        CheckBoxesPage checkBoxesPage = Allure.step("Открыть страницу checkBoxes",
                () -> open(configuration.getHttpHostUrl("checkboxes"), CheckBoxesPage.class));
        $x("//h3").shouldHave(text("Checkboxes"));
        Allure.step("Проставить checkboxes в состояние false", () -> checkBoxesPage.setCheckBox(false, checkboxNumber));
        Allure.step("Проставить checkboxes в состояние true", () -> {
            checkBoxesPage.setCheckBox(true, checkboxNumber);
        });
    }

    @Test
    @Tag("UI")
    @Story("Dropdown List")
    @DisplayName("Проверка работы Dropdown")
    void testDropdown() {
        DropdownPage dropdownPage = Allure.step("Открыть страницу dropdown",
                () -> open(configuration.getHttpHostUrl("dropdown"), DropdownPage.class));
        $x("//h3").shouldHave(text("Dropdown List"));
        Allure.step("Заполнить dropdown элемент значением Option 1", () -> dropdownPage.setDropdown("Option 1"));
        Allure.step("Заполнить dropdown элемент значением Option 2", () -> dropdownPage.setDropdown("Option 2"));
    }

    @Test
    @Tag("UI")
    @Story("Drag and Drop")
    @DisplayName("Проверка работы DragAndDrop")
    void testDragAndDrop() {
        DragAndDropPage dragAndDropPage = Allure.step("Открыть страницу dragAndDrop",
                () -> open(configuration.getHttpHostUrl("drag_and_drop"), DragAndDropPage.class));
        $x("//h3").shouldHave(text("Drag and Drop"));
        Allure.step("Переместить А к В", dragAndDropPage::dragAndDropAB);
    }

    @Test
    @Tag("UI")
    @Story("Context Menu")
    @DisplayName("Проверка работы с ContextMenu")
    void testContextMenu() {
        ContextMenuPage contextMenuPage = Allure.step("Открыть страницу ContextMenu",
                () -> open(configuration.getHttpHostUrl("context_menu"), ContextMenuPage.class));
        $x("//h3").shouldHave(text("Context Menu"));
        Allure.step("Вызвать контекстное меню", contextMenuPage::callContextMenu);
        Allure.step("Проверить текст всплывающего окна", contextMenuPage::checkPopUp);
        Allure.step("Закрыть всплывающее окно", contextMenuPage::acceptPopUp);
    }

    @Test
    @Tag("UI")
    @Story("Hidden Element")
    @DisplayName("Проверка работы со скрытыми элементами")
    void testHidden() {
        int waitingTime = 30;
        HiddenDynamicallyElementPage hiddenElementPage = Allure.step("Открыть страницу HiddenDynamicallyElement",
                () -> open(configuration.getHttpHostUrl("hidden_element"), HiddenDynamicallyElementPage.class));
        $x("//h4").shouldHave(text("Example 1: Element on page that is hidden"));
        Allure.step("Нажать на кнопку Старт", hiddenElementPage::clickStartButton);
        Allure.step("После окончания загрузки, проверить наличие записи 'Hello World'", () -> {$("#finish > h4").shouldBe(visible, Duration.ofSeconds(waitingTime));});
    }

    @Test
    @Tag("UI")
    @Story("Rendered element")
    @DisplayName("Проверка работы c подгружаемыми элементами")
    void testRendered() {
        int waitingTime = 30;
        RenderedDynamicallyElementPage renderedElementPage = Allure.step("Открыть страницу RenderedDynamicallyElement",
                () -> open(configuration.getHttpHostUrl("rendered_element"), RenderedDynamicallyElementPage.class));
        $x("//h4").shouldHave(text("Example 2: Element rendered after the fact"));
        Allure.step("Нажать на кнопку Старт", renderedElementPage::clickStartButton);
        Allure.step("После окончания загрузки, проверить наличие записи 'Hello World'", () -> {
            $("#finish > h4").shouldBe(exist, Duration.ofSeconds(waitingTime)).shouldBe(visible);
        });
    }

    @Test
    @Tag("UI")
    @Story("Upload file")
    @DisplayName("Проверка работы c выгрузкой файлов")
    void testUpload() {
        String path = "src/test/resources/ui/";
        String filename = "sample.txt";
        UploadPage uploadPage = Allure.step("Открыть страницу File Uploader",
                () -> open(configuration.getHttpHostUrl("upload"), UploadPage.class));
        $x("//h3").shouldHave(text("File Uploader"));
        Allure.step("Загрузить файл на сайт", () -> uploadPage.uploadFile(path, filename));
        Allure.step("Нажать на кнопку upload", uploadPage::clickUploadButton);
        Allure.step("Проверить, что файл загружен успешно", () -> uploadPage.checkUploadFile(filename));
    }

    @Test
    @Tag("UI")
    @Story("Download file")
    @DisplayName("Проверка работы c загрузкой файлов")
    void testDownload() {
        String filename = "testUpload.json";
        DownloadPage downloadPage = Allure.step("Открыть страницу File Uploader",
                () -> open(configuration.getHttpHostUrl("download"), DownloadPage.class));
        $x("//h3").shouldHave(text("File Downloader"));
        Allure.step("Загрузить файл на сайт", () -> downloadPage.downloadFile(filename));
    }
}

