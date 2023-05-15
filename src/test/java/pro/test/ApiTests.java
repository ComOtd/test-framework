package pro.test;

import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pro.test.configuration.EnvironmentConfiguration;
import pro.test.configuration.EnvironmentConfigurationParameter;
import pro.test.http.HttpBaseSpecification;

import static com.github.fge.jsonschema.SchemaVersion.DRAFTV4;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Epic("Test for test-framework")
@Feature("Http tests")
@Tag("First")
@Owner("Osipov Semen")
@ExtendWith({HttpBaseSpecification.class,
        EnvironmentConfigurationParameter.class})
@DisplayName("Проверка petstore API")
class ApiTests {
    private final EnvironmentConfiguration configuration;


    public ApiTests(EnvironmentConfiguration configuration) {
        this.configuration = configuration;
    }

    @Test
    @Tag("Api")
    @Story("Test Pets By Active Status")
    @DisplayName("Проверка findByStatus со статусом available")
    void testPetsByActiveStatusWithSchema() {
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder()
                .setValidationConfiguration(ValidationConfiguration.newBuilder().setDefaultVersion(DRAFTV4).freeze())
                .freeze();
        Response response = Allure.step("Отправить запрос", () -> given()
                .baseUri(configuration.getHttpHostUrl("pets_by_status"))
                .param("status", "available")
                .get());
        Allure.step("Проверить статус ответа", () -> response
                .then()
                .statusCode(200));
        Allure.step("Проверить по json схеме", () -> response.then()
                .body(matchesJsonSchemaInClasspath("pet/schema.json").using(jsonSchemaFactory)));
    }
}
