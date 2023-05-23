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
import java.sql.Timestamp;

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
        String queryUser = FileUtil.getTextFileContent("sql/tableUser.sql");
        connectionSource.statement(st -> {
            st.execute(queryUser);
        });
        String queryDocuments = FileUtil.getTextFileContent("sql/tableDocuments.sql");
        connectionSource.statement(st -> {
            st.execute(queryDocuments);
        });
        String queryEmployee = FileUtil.getTextFileContent("sql/tableEmployee.sql");
        connectionSource.statement(st -> {
            st.execute(queryEmployee);
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

    @Test
    @Tag("SQL")
    void checkSQLJoin() {
        Allure.step("Заполнить таблицы", () -> {
            connectionSource.statement(st -> {
                String insertUser = "INSERT INTO user (name,surname,doc_id) VALUES ('Иван','Иванов',777)";
                Allure.addAttachment("insertUser", insertUser);
                st.execute(insertUser);
            });
            connectionSource.statement(st -> {
                String insertDocument = "INSERT INTO documents (doc_id, doc_series,doc_num,employee_id) VALUES (777,4444,123456,12345)";
                Allure.addAttachment("insertDocument", insertDocument);
                st.execute(insertDocument);
            });
            connectionSource.statement(st -> {
                String insertEmployee = "INSERT INTO employee (fullname,employee_id) VALUES ('Миханюк Лариса Аромовна',12345)";
                Allure.addAttachment("insertEmployee", insertEmployee);
                st.execute(insertEmployee);
            });

        });
        Allure.step("Проверить поля с default значениями", () ->
                connectionSource.statement(st -> {
                    String selectDefaultFields =
                            "SELECT user.create_date,documents.original_flg,employee.license_flg\n" +
                                    "FROM user\n" +
                                    "JOIN documents ON documents.doc_id = user.doc_id\n" +
                                    "JOIN employee ON employee.employee_id = documents.employee_id " +
                                    "WHERE user.name='Иван' ";
                    Allure.addAttachment("selectDefaultFields", selectDefaultFields);
                    ResultSet resultSet = st.executeQuery(selectDefaultFields);
                    Assertions.assertTrue(resultSet.next(), "Пустой результат");
                    Assertions.assertEquals(Timestamp.valueOf("1995-01-01 00:00:00"), resultSet.getTimestamp("create_date"), "Дефолтная дата create_date не соответствует ожидаемой");
                    Assertions.assertFalse(resultSet.getBoolean("original_flg"), "Дефолтное значение original_flg не соответствует ожидаемому");
                    Assertions.assertFalse(resultSet.getBoolean("license_flg"), "Дефолтное значение license_flg не соответствует ожидаемому");
                }));

    }

    @Test
    @Tag("SQL")
    void deleteSQL() {
        Allure.step("Заполнить таблицу user", () -> {
            Allure.step("Выполнить инсерт", () -> connectionSource.statement(st -> {
                String insertUser = "INSERT INTO user (name,surname,doc_id) VALUES ('Виктор','Иванов',777)";
                Allure.addAttachment("insertUser", insertUser);
                st.execute(insertUser);
            }));
            Allure.step("Проверить количество записей", () -> {

                connectionSource.statement(st -> {
                    String countUser = "SELECT COUNT(*) as count FROM user WHERE name = 'Виктор'";
                    Allure.addAttachment("countUser", countUser);
                    ResultSet resultSet = st.executeQuery(countUser);
                    Assertions.assertTrue(resultSet.next(), "Пустой результат");
                    Assertions.assertEquals(1, resultSet.getInt("count"), "Неверное количество записей");
                });
            });
        });
        Allure.step("Удалить запись", () -> {

            connectionSource.statement(st -> {
                String deleteUser =
                        "DELETE FROM user WHERE name ='Виктор'";
                Allure.addAttachment("deleteUser", deleteUser);
                st.execute(deleteUser);
            });
        });
        Allure.step("Проверить количество записей", () -> {
            connectionSource.statement(st -> {
                String countUser = "SELECT COUNT(*) as count FROM user WHERE name = 'Виктор'";
                Allure.addAttachment("countUser", countUser);
                ResultSet resultSet = st.executeQuery(countUser);
                Assertions.assertTrue(resultSet.next(), "Пустой результат");
                Assertions.assertEquals(0, resultSet.getInt("count"), "Неверное количество записей");
            });
        });
    }

    @Test
    @Tag("SQL")
    void updateSQL() {
        String oldName = "Владимир";
        String newName = "Всеволод";
        Allure.step("Заполнить таблицу user", () -> connectionSource.statement(st -> {
            String insertUser = String.format("INSERT INTO user (name,surname,doc_id) VALUES ('%s','Иванов',9999)", oldName);
            Allure.addAttachment("insertUser", insertUser);
            st.execute(insertUser);
        }));
        Allure.step("Изменить имя", () -> {
            connectionSource.statement(st -> {
                String updateUser =
                        String.format("UPDATE user SET name = '%s' WHERE name='%s'", newName, oldName);
                Allure.addAttachment("updateUser", updateUser);
                st.execute(updateUser);
            });
        });
        Allure.step("Проверить новое имя", () -> {
            connectionSource.statement(st -> {
                String selectNameUser = "SELECT name FROM user WHERE doc_id = 9999";
                Allure.addAttachment("selectNameUser", selectNameUser);
                ResultSet resultSet = st.executeQuery(selectNameUser);
                Assertions.assertTrue(resultSet.next(), "Пустой результат");
                Assertions.assertEquals(newName, resultSet.getString("name"), "Имя не соответствует ожидаемому");
            });
        });
    }
}
