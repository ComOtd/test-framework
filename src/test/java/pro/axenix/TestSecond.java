package pro.axenix;

import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pro.axenix.configuration.EnvironmentConfiguration;
import pro.axenix.configuration.EnvironmentConfigurationParameter;
import pro.axenix.http.HttpBaseSpecification;

import static com.github.fge.jsonschema.SchemaVersion.DRAFTV4;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Epic("Test for test-framework")
@Feature("Http tests")
@Tag("First")
@Owner("Osipov Semen")
@ExtendWith({HttpBaseSpecification.class,
        EnvironmentConfigurationParameter.class})
class TestSecond {
    private final EnvironmentConfiguration configuration;


    public TestSecond(EnvironmentConfiguration configuration) {
        this.configuration = configuration;
    }

    @Test
    @Tag("Api")
    @Story("Test Pets By Active Status")
    void testPetsByActiveStatusWithSchema() {
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder()
                .setValidationConfiguration(ValidationConfiguration.newBuilder().setDefaultVersion(DRAFTV4).freeze())
                .freeze();
        given()
                .baseUri(configuration.getHttpHostUrl("pets_by_status"))
                .param("status", "available")
                .get()
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("pet/schema.json").using(jsonSchemaFactory));
    }
}
