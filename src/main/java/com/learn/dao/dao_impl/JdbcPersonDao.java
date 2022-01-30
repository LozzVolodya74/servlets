package com.learn.dao.dao_impl;

import com.learn.exception.MySQLException;
import com.learn.dao.UserDao;
import com.learn.entity.Person;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;


/**
 * Class содержит методы для
 *
 * @author Volodymyr Lopachak
 * @version 1.1 24 October 2021
 */
public class JdbcPersonDao implements UserDao {
    public static final Logger LOGGER = Logger.getLogger(JdbcPersonDao.class.getName());

    private final DataSource dataSource;

    public JdbcPersonDao(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Метод создаёт новую сущность типа Person
     *
     * @param entity - объект типа Person, который необходимо записать в базу данных
     */
    @Override
    public void create(Person entity) throws MySQLException {
        final String SQL = "INSERT INTO person VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setLong(1, entity.getId());
            statement.setLong(2, entity.getRoleId());
            statement.setString(3, entity.getFirstName());
            statement.setString(4, entity.getLastName());
            statement.setString(5, entity.getLogin());
            statement.setDate(6, (Date) entity.getDob());
            statement.setString(7, entity.getPassword());
            statement.setString(8, entity.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("ERROR create person " + entity.toString(), e);
            throw new MySQLException("ERROR create person " + entity.toString());
        }
    }

    /**
     * Метод обновляет сущность типа Person
     *
     * @param entity - сущность типа Person, которую необходимо обновить в базе данных
     */
    @Override
    public void update(Person entity) throws MySQLException {
        final String SQL = "UPDATE person SET role_id = ?, first_name = ?, last_name = ?, " +
                "login = ?, dob = ?, password = ?, email = ?  WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setLong(1, entity.getRoleId());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            statement.setString(4, entity.getLogin());
            statement.setDate(5, (Date) entity.getDob());
            statement.setString(6, entity.getPassword());
            statement.setString(7, entity.getEmail());
            statement.setLong(8, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("ERROR update person " + entity.toString(), e);
            throw new MySQLException("ERROR update person " + entity);
        }
    }

    /**
     * Метод удаляет сущность типа Person
     *
     * @param entity - сущность типа Person, которую необходимо удалить в базе данных
     */
    @Override
    public void remove(Person entity) throws MySQLException {
        final String SQL = "DELETE FROM person WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setLong(1, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("ERROR remove person " + entity.toString(), e);
            throw new MySQLException("ERROR remove person " + entity.toString());
        }
    }

    /**
     * Метод ищет в базе данных и возвращает все сущности типа Person,
     * котрые хранятся в базе данных
     *
     * @return список всех сущностей указанного типа
     */
    @Override
    public List<Person> findAll() throws MySQLException {
        List<Person> persons = new ArrayList<>();

        final String SQL = "SELECT* FROM person";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL)) {
                while (resultSet.next()) {
                    Person person = new Person(resultSet.getLong(1), resultSet.getLong(2),
                            resultSet.getString(3), resultSet.getString(4),
                            resultSet.getString(5), resultSet.getDate(6),
                            resultSet.getString(7), resultSet.getString(8));
                    persons.add(person);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("ERROR findAll person", e);
            throw new MySQLException("ERROR findAll person");
        }
        return persons;
    }

    /**
     * Метод ищет в базе данных и возвращает одну сущность типа Person
     *
     * @param id - порядковый номер (primary key) искомой сущности типа Person
     * @return - сущность типа Person
     */
    @Override
    public Person findById(long id) throws MySQLException {
        Person person = null;
        final String SQL = "SELECT* FROM person WHERE id = " + String.valueOf(id);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            person = getNewPerson(statement, SQL);
        } catch (SQLException e) {
            LOGGER.error("ERROR findById person " + id, e);
            throw new MySQLException("ERROR findById person " + id);
        }
        return person;
    }

    /**
     * Метод находит объект типа Person по его email
     *
     * @param email - електронная пошта пользователя
     * @return - объект типа Person, который хнанится в базе данных
     */
    @Override
    public Person findByEmail(String email) throws MySQLException {
        Person person = null;
        final String SQL = "SELECT* FROM person WHERE email = " + "'" + email + "'";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            person = getNewPerson(statement, SQL);
        } catch (SQLException e) {
            LOGGER.error("ERROR findByEmail person " + email, e);
            throw new MySQLException("ERROR findByEmail person " + email);
        }
        return person;
    }

    /**
     * Метод находит объект типа Person по его login
     *
     * @param login - логин пользователя
     * @return - объект типа Person, который хнанится в базе данных
     */
    @Override
    public Person findByLogin(String login) throws MySQLException {
        Person person = null;
        final String SQL = "SELECT* FROM person WHERE login = " + "'" + login + "'";

        try (Statement statement = dataSource.getConnection().createStatement()) {
            person = getNewPerson(statement, SQL);
        } catch (SQLException e) {
            LOGGER.error("ERROR findByLogin person " + login, e);
            throw new MySQLException("ERROR findByLogin person " + login);
        }
        return person;
    }

    /**
     * Метод создаёт пользователя на основании данных, полученных с базы данных
     *
     * @param statement - объект interface Statement
     * @param SQL       - строковое представление запроса
     * @return
     * @throws SQLException - в случае ошибки обращения к базе данных
     */
    private Person getNewPerson(Statement statement, String SQL) throws SQLException {
        Person person = null;
        try (ResultSet resultSet = statement.executeQuery(SQL)) {
            while (resultSet.next()) {
                person = new Person(resultSet.getLong(1), resultSet.getLong(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getDate(6),
                        resultSet.getString(7), resultSet.getString(8));
            }
        }
        return person;
    }

    /**
     * @return слудующий id в базе данных для записи объекта Person
     */
    public long findMaxPersonId() throws MySQLException {
        final String SQL = "SELECT max(id) AS max_id FROM person;";
        long id = 0;
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(SQL)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    id = resultSet.getLong("max_id") + 1;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("ERROR findMaxPersonId on the person", e);
            throw new MySQLException("ERROR findMaxPersonId on the person");
        }
        return id;
    }
}
