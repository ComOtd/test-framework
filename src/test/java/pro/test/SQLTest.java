package pro.test;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import pro.test.configuration.EnvironmentConfiguration;
import pro.test.configuration.EnvironmentConfigurationParameter;
import pro.test.configuration.EnvironmentConfigurationProvider;
import pro.test.sql.ConnectionSource;
import pro.test.sql.DataSourceFactory;
import pro.test.util.FileUtil;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@Epic("Test for test-framework ")
@Feature("SQL tests")
@Owner("Osipov Semen")
@DisplayName("Первые SQL тесты")
@ExtendWith(EnvironmentConfigurationParameter.class)
class SQLTest {
    private static ConnectionSource connectionSource;

    @BeforeAll
    static void before() throws IOException, SQLException {
        EnvironmentConfiguration configuration = new EnvironmentConfigurationProvider().get();
        DataSource dbTest = new DataSourceFactory(configuration).create("db_test");
        connectionSource = new ConnectionSource(dbTest);
        String query = FileUtil.getTextFileContent("sql/table.sql");
        connectionSource.statement(st -> {
            st.execute(query);
        });
        connectionSource.statement(st -> {
            st.execute("INSERT INTO staff (id, name) VALUES (1, 'Иван')");
        });
    }


    @Test
    @Tag("SQL")
    void checkSQL() {
        Allure.step("Проверить имя сотрудника", () -> connectionSource.statement(st -> {
            String sql = "SELECT * FROM staff";
            Allure.addAttachment("sql", sql);
            ResultSet resultSet = st.executeQuery(sql);
            Assertions.assertTrue(resultSet.next(), "Пустой результат");
            Assertions.assertEquals("Иван", resultSet.getString("name"), "Имя не соответствует");
        }));
    }
}
