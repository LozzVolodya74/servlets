package com.learn.dao;

import com.learn.exception.MySQLException;
import com.learn.entity.Person;

/**
 * Interface содержит набор специфических методов для получения значений
 * с базы данных объектов типа {@link Person}
 *
 * @author Volodymyr Lopachak
 * @version 1.1 24 October 2021
 */
public interface UserDao extends Dao<Person> {

    /**
     * Метод находит объект типа Person по его email
     *
     * @param email - електронная пошта пользователя
     * @return - объект типа Person, который хнанится в базе данных
     */
    Person findByEmail(String email) throws MySQLException;

    /**
     * Метод находит объект типа Person по его login
     *
     * @param login - логин пользователя
     * @return - объект типа Person, который хнанится в базе данных
     */
    Person findByLogin(String login) throws MySQLException;
}
