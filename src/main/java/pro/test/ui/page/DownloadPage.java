package pro.test.ui.page;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.FileNotFoundException;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;

public class DownloadPage {
    @Step("Проверка заголовка")
    public void checkHeader() {
        $x("//h3").shouldHave(text("File Downloader"));
    }

    @Step("Загрузить файл {fileName}")
    public void downloadFile(String fileName) {
        try {
            File file = $x(String.format("//a[text()='%s']", fileName)).download();
            Assertions.assertEquals(file.getName(), fileName, "Имена файлов не совпадают");
        } catch (FileNotFoundException e) {
            Assertions.fail("Ошибка загрузки файла");
        }
    }
}
