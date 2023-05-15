package pro.test.http;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static io.restassured.RestAssured.given;

public class HttpBaseSpecification implements BeforeAllCallback {
    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        RestAssured.requestSpecification = given()
                .relaxedHTTPSValidation()
                .urlEncodingEnabled(false)
                .filters(new RequestLoggingFilter(), new ResponseLoggingFilter())
                .filter(new AllureRestAssured());
    }
}
