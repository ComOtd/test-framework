package pro.test.ui.page;

import io.qameta.allure.Step;

import java.io.File;
import java.io.FileNotFoundException;

import static com.codeborne.selenide.Selenide.$x;

public class DownloadPage {

    @Step("Скачать файл {fileName}")
    public File downloadFile(String fileName) {
        try {
            return $x(String.format("//a[text()='%s']", fileName)).download();
        } catch (FileNotFoundException e) {
            throw new AssertionError("Возникла ошибка при скачивании файла", e);
        }
    }
}
