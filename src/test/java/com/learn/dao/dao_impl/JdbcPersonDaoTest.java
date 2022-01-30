package com.learn.dao.dao_impl;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.learn.entity.Person;
import com.learn.exception.MySQLException;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Class содержит методы для тестирования
 *
 * @author Volodymyr Lopachak
 */
@RunWith(JUnit4.class)
@DBUnit(url = "jdbc:h2:mem:book_shop", driver = "org.h2.Driver", user = "Vovik", password = "Kharkiv_2021")
public class JdbcPersonDaoTest {

    private static final Person ROB = new Person(
            2, 2, "Rob", "Red", "rob-red",
            Date.valueOf("1990-09-09"), "password2", "rob-red@gmail.com"
    );

    private static final Person DEN = new Person(
            1, 1, "Den", "Brown", "den-brown",
            Date.valueOf("1980-08-08"), "password1", "den-brown@gmail.com"
    );

    private static DataSource dataSource;

    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.instance();

    private final JdbcPersonDao dao = new JdbcPersonDao(dataSource);

    @BeforeClass
    public static void beforeAll() throws SQLException {
        dataSource = TestUtils.createTestDataSource(JdbcPersonDaoTest.class);
        TestUtils.executeSQLFromClasspath(dataSource, "person-dao/ddl.sql");
    }

    @Test
    @DataSet("person-dao/init-db.yml")
    @ExpectedDataSet("person-dao/result-create.yml")
    public void testCreate() throws MySQLException {
        dao.create(ROB);
    }

    @Test
    @DataSet("person-dao/init-db.yml")
    @ExpectedDataSet("person-dao/result-update.yml")
    public void testUpdate() throws MySQLException {
        dao.update(
                new Person(
                        1, 2, "Rob", "Red", "rob-red",
                        Date.valueOf("1990-09-09"), "password2", "rob-red@gmail.com"
                )
        );
    }

    @Test
    @DataSet("person-dao/init-db.yml")
    @ExpectedDataSet("person-dao/result-remove.yml")
    public void testRemove() throws MySQLException {
        dao.remove(DEN);
    }

    @Test
    @DataSet("person-dao/init-db.yml")
    @ExpectedDataSet("person-dao/init-db.yml")
    public void testFindAll() throws MySQLException {
        final List all = dao.findAll();
        assertEquals(Collections.singletonList(DEN), all);
    }

    @Test
    @DataSet("person-dao/init-db.yml")
    @ExpectedDataSet("person-dao/init-db.yml")
    public void testFindByIdSuccess() throws MySQLException {
        final Person person = dao.findById(1);
        assertEquals(DEN, person);
    }

    @Test
    @DataSet("person-dao/init-db.yml")
    @ExpectedDataSet("person-dao/init-db.yml")
    public void testFindByIdFailed() throws MySQLException {
        final Person person = dao.findById(-1);
        assertNull(person);
    }

    @Test
    @DataSet("person-dao/init-db.yml")
    @ExpectedDataSet("person-dao/init-db.yml")
    public void testFindByLoginSuccess() throws MySQLException {
        final Person person = dao.findByLogin("den-brown");
        assertEquals(DEN, person);
    }

    @Test
    @DataSet("person-dao/init-db.yml")
    @ExpectedDataSet("person-dao/init-db.yml")
    public void testFindByLoginFailed() throws MySQLException {
        final Person person = dao.findByLogin("not-found");
        assertNull(person);
    }

    @Test
    @DataSet("person-dao/init-db.yml")
    @ExpectedDataSet("person-dao/init-db.yml")
    public void testFindByEmailSuccess() throws MySQLException {
        final Person person = dao.findByEmail("den-brown@gmail.com");
        assertEquals(DEN, person);
    }

    @Test
    @DataSet("person-dao/init-db.yml")
    @ExpectedDataSet("person-dao/init-db.yml")
    public void testFindByEmailFailed() throws MySQLException {
        final Person person = dao.findByEmail("not-found");
        assertNull(person);
    }

    @Test
    @DataSet("person-dao/init-db.yml")
    @ExpectedDataSet("person-dao/init-db.yml")
    public void testFindMaxPersonId() throws MySQLException {
        final long id = dao.findMaxPersonId();
        assertEquals(2, id);
    }
}