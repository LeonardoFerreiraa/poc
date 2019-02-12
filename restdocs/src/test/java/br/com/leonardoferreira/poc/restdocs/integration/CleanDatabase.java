package br.com.leonardoferreira.poc.restdocs.integration;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Component
public class CleanDatabase {

    @Autowired
    private DataSource dataSource;

    @SneakyThrows
    public void clean() {
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("SET REFERENTIAL_INTEGRITY = 0");
            truncateTables(connection, stmt);
            restartSequences(connection, stmt);
            stmt.executeUpdate("SET REFERENTIAL_INTEGRITY = 1");
        }
    }

    @SneakyThrows
    private void truncateTables(final Connection connection, final Statement stmt) {
        try (ResultSet rs = connection.createStatement().executeQuery("SHOW TABLES")) {
            while (rs.next()) {
                stmt.executeUpdate("TRUNCATE TABLE " + rs.getString("TABLE_NAME"));
            }
        }
    }

    @SneakyThrows
    private void restartSequences(final Connection connection, final Statement stmt) {
        try (ResultSet rs = connection.createStatement().executeQuery("SELECT SEQUENCE_NAME FROM INFORMATION_SCHEMA.SEQUENCES")) {
            while (rs.next()) {
                stmt.executeUpdate("ALTER SEQUENCE " + rs.getString("SEQUENCE_NAME") + " RESTART WITH 1");
            }
        }
    }
}