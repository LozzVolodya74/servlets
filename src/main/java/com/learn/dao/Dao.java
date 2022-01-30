package com.learn.dao;

import com.learn.exception.MySQLException;

import java.util.List;

/**
 * Interface содержит набор общих методов для получения значений с базы данных
 *
 * @author Volodymyr Lopachak
 * @version 1.0 28 August 2021
 */
public interface Dao<E> {

    /**
     * Метод создаёт новую сущность
     *
     * @param entity - сущность, которую необходимо создать в базе данных
     */
    void create(E entity) throws Exception;

    /**
     * Метод обновляет сущность
     *
     * @param entity - сущность, которую необходимо обновить в базе данных
     */
    void update(E entity) throws MySQLException;

    /**
     * Метод удаляет сущность
     *
     * @param entity - сущность, которую необходимо удалить в базе данных
     */
    void remove(E entity) throws MySQLException;

    /**
     * Метод ищет в базе данных и возвращает все сущности указанного типа
     *
     * @return список всех сущностей указанного типа
     */
    List<E> findAll() throws MySQLException;

    /**
     * Метод ищет в базе данных и возвращает одну сущность указанного типа
     *
     * @param id - порядковый номер (primary key) искомой сущности
     * @return - сущность указанного типа
     */
    E findById(long id) throws MySQLException;
}
