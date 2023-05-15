package pro.axenix.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.axenix.configuration.DataBaseConfiguration;
import pro.axenix.configuration.EnvironmentConfiguration;

import javax.sql.DataSource;

@Slf4j
@AllArgsConstructor
public class DataSourceFactory {

    public final EnvironmentConfiguration environmentConfiguration;

    public DataSource create(String dbName) {
        log.info("Create new DataSource for {}", dbName);
        return new HikariDataSource(hikariConfig(dbName));

    }

    private HikariConfig hikariConfig(String dbName) {
        DataBaseConfiguration databaseConfiguration = environmentConfiguration.getDatabaseConfiguration(dbName);
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername(databaseConfiguration.getUsername());
        hikariConfig.setPassword(databaseConfiguration.getPassword());
        hikariConfig.setJdbcUrl(databaseConfiguration.getUrl());
        return hikariConfig;
    }
}
