package br.com.leonardoferreira.poc.testlibrary.testexecutionlistener;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

@Slf4j
@Component
public class CleanDatabaseTestExecutionListener implements TestExecutionListener {

    @Override
    public void beforeTestMethod(final TestContext testContext) throws Exception {
        try {
            final DataSource dataSource = testContext.getApplicationContext()
                    .getBean(DataSource.class);
            cleanDatabase(dataSource);
        } catch (final NoSuchBeanDefinitionException e) {
            log.error("Method=beforeTestMethod", e);
        }
    }

    private void cleanDatabase(final DataSource dataSource) throws SQLException {
        try (final Connection connection = dataSource.getConnection()) {
            try (final Statement stmt = connection.createStatement()) {
                truncateTables(connection, stmt);
            }
        }
    }

    private void truncateTables(final Connection connection, final Statement stmt) throws SQLException {
        final DatabaseMetaData metaData = connection.getMetaData();
        try (final ResultSet rs = metaData.getTables(null, null, null, new String[]{"TABLE"})) {
            while (rs.next()) {
                final String tableName = rs.getString("TABLE_NAME");
                if (!"flyway_schema_history".equalsIgnoreCase(tableName)) {
                    stmt.executeUpdate("TRUNCATE TABLE " + tableName + " RESTART IDENTITY CASCADE");
                }
            }
        }
    }

}
