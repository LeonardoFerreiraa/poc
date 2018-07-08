package br.com.leonardoferreira.hello.feature;

import cucumber.api.java.Before;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class CleanDatabaseHook {

    @Autowired
    private DataSource dataSource;

    @Before
    public void setup() {
        try {
            Connection connection = dataSource.getConnection();
            Statement stmt = connection.createStatement();
            Set<String> tables = findAllTables(connection);

            stmt.execute("SET REFERENTIAL_INTEGRITY FALSE");
            for (String table : tables) {
                stmt.executeUpdate("TRUNCATE TABLE " + table);
            }
            stmt.execute("SET REFERENTIAL_INTEGRITY TRUE");
        } catch (SQLException e) {
            log.error("Method=setup, e={}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }


    private Set<String> findAllTables(final Connection connection) throws SQLException {
        Set<String> tables = new HashSet<>();

        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet rs = metaData.getTables(null, null, "%", null);
        while (rs.next()) {
            if ("TABLE".equalsIgnoreCase(rs.getString(4))) {
                tables.add(rs.getString(3));
            }
        }

        return tables;
    }
}