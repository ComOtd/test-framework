package pro.test.sql;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@AllArgsConstructor
public class ConnectionSource {
    @Getter
    private final DataSource dataSource;

    @FunctionalInterface
    public interface SQLFunction<T, R> {
        R apply(T t) throws SQLException;
    }

    @FunctionalInterface
    public interface SQLConsumer<T> {
        void accept(T t) throws SQLException;
    }

    public void connection(SQLConsumer<? super Connection> consumer) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            consumer.accept(connection);
        }
    }

    public <R> R connection(SQLFunction<? super Connection, ? extends R> function) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            return function.apply(connection);
        }
    }

    public void statement(SQLConsumer<? super Statement> consumer) throws SQLException {
        connection(con -> {
            try (Statement statement = con.createStatement()) {
                consumer.accept(statement);
            }
        });
    }

    public <R> R statement(SQLFunction<? super Statement, ? extends R> function) throws SQLException {
        return connection(con -> {
            try (Statement statement = con.createStatement()) {
                return function.apply(statement);
            }
        });
    }

}
