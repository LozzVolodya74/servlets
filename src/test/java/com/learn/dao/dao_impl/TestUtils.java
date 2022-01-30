package com.learn.dao.dao_impl;

import com.github.database.rider.core.api.configuration.DBUnit;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import javax.sql.DataSource;

import static java.util.Objects.requireNonNull;

/**
 * Class содержит утилитные методы для тестирования
 *
 * @author Volodymyr Lopachak
 */
public class TestUtils {

    public static DataSource createTestDataSource(Class<?> testClass) {
        final DBUnit dbUnit = testClass.getAnnotation(DBUnit.class);
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDefaultAutoCommit(true);
        dataSource.setDriverClassName(dbUnit.driver());
        dataSource.setUrl(dbUnit.url());
        dataSource.setUsername(dbUnit.user());
        dataSource.setPassword(dbUnit.password());
        return dataSource;
    }

    public static void executeSQLFromClasspath(DataSource dataSource,
                                               String classPathResource) throws SQLException {
        String sql;
        try (Scanner scanner = new Scanner(requireNonNull(TestUtils.class.getClassLoader().getResourceAsStream(classPathResource)))) {
            sql = scanner.useDelimiter("\\A").next();
        }

        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                final String[] commands = sql.split(";");
                for (final String command : commands) {
                    statement.executeUpdate(command);
                }
            }
        }
    }
}
