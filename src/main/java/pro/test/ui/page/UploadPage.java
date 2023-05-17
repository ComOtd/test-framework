package pro.test.ui.page;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import java.io.File;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class UploadPage {
    @Step("Проверка заголовка")
    public void checkHeader() {
        $x("//h3").shouldHave(text("File Uploader"));
    }

    @Step("Загрузить файл на сайт")
    public void uploadFile(String path, String filename) {
        $("#file-upload").uploadFile(new File(path, filename));
    }

    @Step("Нажать кнопку upload")
    public void clickUploadButton() {
        $("#file-submit").click();
    }

    @Step("Проверить, что файл загружен успешно")
    public void checkUploadFile(String fileName) {
        $("h3").shouldHave(text("File Uploaded!"));
        Assertions.assertTrue($("#uploaded-files").getText().contains(fileName), "Имена файлов не совпадают");
    }

}
