package com.learn.jdbc;

import com.learn.service.ServiceManager;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import javax.sql.DataSource;

/**
 * Class содержит методы для получения пула соединения с базой данных
 *
 * @author Volodymyr Lopachak
 */
public class ConnectionManager {
    public static final Logger LOGGER = Logger.getLogger(ConnectionManager.class.getName());

    private ConnectionManager() {
    }

    // переменная для хранения сингелтона dataSource
    private static BasicDataSource dataSource;

    /**
     * Фабрика для подключения источника данных,
     * которые находятся в пуле соединений
     * Все настойки считываются с файла jdbc.properties
     */
    public static void initDataSource() {
        ServiceManager manager = new ServiceManager();
        dataSource = new BasicDataSource();
        dataSource.setDefaultAutoCommit(true);
        dataSource.setDriverClassName(manager.getApplicationProperty("db.driver"));
        dataSource.setUrl(manager.getApplicationProperty("db.url"));
        dataSource.setUsername(manager.getApplicationProperty("db.username"));
        dataSource.setPassword(manager.getApplicationProperty("db.password"));
        dataSource.setInitialSize(Integer.parseInt(manager.getApplicationProperty("db.pool.initSize")));
        dataSource.setMaxTotal(Integer.parseInt(manager.getApplicationProperty("db.pool.maxSize")));
    }

    /**
     * Очистка ресурсов
     */
    public static void releaseDataSource() {
        if (dataSource != null) {
            try {
                dataSource.close();
            } catch (SQLException e) {
                LOGGER.error("Can't release datasource: " + e.getMessage(), e);
            }
        }
    }

    /**
     * Возвращает объект DataSource
     */
    public static DataSource getDataSource() {
        return dataSource;
    }
}
