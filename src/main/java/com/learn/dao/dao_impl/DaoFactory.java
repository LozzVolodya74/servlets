package com.learn.dao.dao_impl;

import com.learn.dao.Dao;
import com.learn.jdbc.ConnectionManager;

/**
 * Class содержит метод для получения объекта PersonDao или RoleDao
 *
 * @author Volodymyr Lopachak
 * @version 1.0 01 September 2021
 */
public class DaoFactory {
    /**
     * Метод по соответствующему запросу возвращает объект PersonDao или RoleDao
     * @param daoName - "person" or "role" - по соответствующему запросу возвращаюется объект
     * @return - объект PersonDao или RoleDao в соответсьвии с запросом, или null, ести запрос не правильный
     */
    public static Dao getDao(String daoName) {
        switch (daoName) {
            case "person":
                return new JdbcPersonDao(ConnectionManager.getDataSource());
            case "role":
                return new JdbcRoleDao(ConnectionManager.getDataSource());
        }
        throw new UnsupportedOperationException("Unsupported daoName: " + daoName);
    }
}
