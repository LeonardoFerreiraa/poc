package br.com.leonardoferreira.poc.jpasearch;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class CleanDatabase {

    @Autowired
    private DataSource dataSource;

    @SneakyThrows
    public void clean() {
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()) {

            Set<String> tables = findAllTables(connection);
            Map<String, String> primaryKeys = findAllPrimaryKeys(connection);

            stmt.executeUpdate("SET REFERENTIAL_INTEGRITY FALSE");
            for (String table : tables) {
                stmt.executeUpdate("TRUNCATE TABLE " + table);
                if (primaryKeys.containsKey(table)) {
                    stmt.executeUpdate("ALTER TABLE " + table + " ALTER COLUMN " + primaryKeys.get(table) + " RESTART WITH 1");
                }
            }
            stmt.executeUpdate("SET REFERENTIAL_INTEGRITY TRUE");
        }
    }

    @SneakyThrows
    private Map<String, String> findAllPrimaryKeys(final Connection connection) {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM INFORMATION_SCHEMA.CONSTRAINTS")) {
            Map<String, String> pks = new HashMap<>();

            while (rs.next()) {
                String constraintType = rs.getString("CONSTRAINT_TYPE");
                if ("PRIMARY_KEY".equalsIgnoreCase(constraintType)) {
                    pks.put(rs.getString("TABLE_NAME"), rs.getString("COLUMN_LIST"));
                }
            }

            return pks;
        }
    }

    @SneakyThrows
    private Set<String> findAllTables(final Connection connection) {
        Set<String> tables = new HashSet<>();

        DatabaseMetaData metaData = connection.getMetaData();
        try (ResultSet rs = metaData.getTables(null, null, "%", null)) {
            while (rs.next()) {
                if ("TABLE".equalsIgnoreCase(rs.getString(4))) {
                    tables.add(rs.getString(3));
                }
            }
        }

        return tables;
    }
}