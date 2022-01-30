package com.learn.dao.dao_impl;

import com.learn.dao.RoleDao;
import com.learn.exception.MySQLException;
import com.learn.entity.Role;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 * Class содержит методы для манипулирования сущностями типа Role в базе данных
 *
 * @author Volodymyr Lopachak
 * @version 1.1 24 October 2021
 */
public class JdbcRoleDao implements RoleDao {
    public static final Logger LOGGER = Logger.getLogger(JdbcRoleDao.class.getName());

    private final DataSource dataSource;

    public JdbcRoleDao(final DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    /**
     * Метод создаёт новую сущность типа Role
     * управление трансакциями происходит в ручном режиме
     *
     * @param entity - сущность типа Role, которую необходимо создать в базе данных
     */
    @Override
    public void create(Role entity) throws MySQLException {
        final String SQL = "INSERT INTO role VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setLong(1, entity.getId());
                statement.setString(2, entity.getRole());
                statement.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error("ERROR create role " + entity.toString(), e);
            throw new MySQLException("ERROR create role " + entity);

        }
    }

    /**
     * Метод обновляет сущность типа Role в базе данных
     *
     * @param entity - сущность типа Role, которую необходимо обновить в базе данных
     */
    @Override
    public void update(Role entity) throws MySQLException {
        final String SQL = "UPDATE role SET name = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, entity.getRole());
            statement.setLong(2, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("ERROR update role " + entity.toString(), e);
            throw new MySQLException("ERROR update role " + entity);
        }
    }

    /**
     * Метод удаляет сущность типа Role
     *
     * @param entity - сущность типа Role, которую необходимо удалить в базе данных
     */
    @Override
    public void remove(Role entity) throws MySQLException {
        final String SQL = "DELETE FROM role WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setLong(1, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("ERROR remove role " + entity.toString(), e);
            throw new MySQLException("ERROR remove role " + entity.toString());
        }
    }

    /**
     * Метод ищет в базе данных и возвращает все сущности типа Role
     *
     * @return список всех сущностей типа Role, которые хранятся в базе данных
     */
    @Override
    public List findAll() throws MySQLException {
        List<Role> roles = new ArrayList<>();
        final String SQL = "SELECT* FROM role";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL)) {
                while (resultSet.next()) {
                    Role role = new Role(resultSet.getLong(1), resultSet.getString(2));
                    roles.add(role);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("ERROR findAll role", e);
            throw new MySQLException("ERROR findAll role");
        }
        return roles;
    }

    /**
     * Метод ищет в базе данных и возвращает типа Role по её ключу
     *
     * @param id - порядковый номер (primary key) искомой сущности типа Role
     * @return - сущность типа Role
     */
    @Override
    public Role findById(long id) throws MySQLException {
        Role role = null;
        final String SQL = "SELECT* FROM role WHERE id = " + String.valueOf(id);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL)) {
                while (resultSet.next()) {
                    role = new Role(resultSet.getLong(1), resultSet.getString(2));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("ERROR findById role " + id, e);
            throw new MySQLException("ERROR findById role " + id);
        }
        return role;
    }

    /**
     * Метод находит объект типа Role по её имени
     *
     * @param name - имя объекта типа Role
     * @return - объект типа Role, который хнанится в базе данных
     */
    @Override
    public Role findByName(String name) throws MySQLException {
        Role role = null;
        final String SQL = "SELECT* FROM role WHERE name = " + "'" + name + "'";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL)) {
                while (resultSet.next()) {
                    role = new Role(resultSet.getLong(1), resultSet.getString(2));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("ERROR findByName role " + name, e);
            throw new MySQLException("ERROR findByName role " + name);
        }
        return role;
    }
}
