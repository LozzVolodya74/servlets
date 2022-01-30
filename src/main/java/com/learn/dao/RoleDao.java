package com.learn.dao;

import com.learn.entity.Role;
import com.learn.exception.MySQLException;

/**
 * Interface содержит набор специфических методов для получения значений
 * с базы данных объектов типа {@link Role}
 *
 * @author Volodymyr Lopachak
 * @version 1.1 24 October 2021
 */
public interface RoleDao extends Dao<Role> {

    /**
     * Метод находит объект типа Role по её имени
     *
     * @param name - имя объекта типа Role
     * @return - объект типа Role, который хнанится в базе данных
     */
    Role findByName(String name) throws MySQLException;
}
