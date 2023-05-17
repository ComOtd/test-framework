package pro.test.ui.page;

import io.qameta.allure.Step;

import java.io.File;

import static com.codeborne.selenide.Selenide.$;

public class UploadPage {

    @Step("Загрузить файл на сайт")
    public void uploadFile(String path, String filename) {
        $("#file-upload").uploadFile(new File(path, filename));
    }


}
