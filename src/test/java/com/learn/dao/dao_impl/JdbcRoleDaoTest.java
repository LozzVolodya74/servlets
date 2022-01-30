package com.learn.dao.dao_impl;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.learn.entity.Role;
import com.learn.exception.MySQLException;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.SQLException;
import java.util.Arrays;
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
public class JdbcRoleDaoTest {

    private static DataSource dataSource;

    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.instance();

    private final JdbcRoleDao dao = new JdbcRoleDao(dataSource);

    @BeforeClass
    public static void beforeAll() throws SQLException {
        dataSource = TestUtils.createTestDataSource(JdbcRoleDaoTest.class);
        TestUtils.executeSQLFromClasspath(dataSource, "role-dao/ddl.sql");
    }

    @Test
    @DataSet("role-dao/init-db.yml")
    @ExpectedDataSet("role-dao/result-create.yml")
    public void testCreate() throws MySQLException {
        dao.create(new Role(3, "TEST"));
    }

    @Test
    @DataSet("role-dao/init-db.yml")
    @ExpectedDataSet("role-dao/result-update.yml")
    public void testUpdate() throws MySQLException {
        dao.update(new Role(2, "TEST"));
    }

    @Test
    @DataSet("role-dao/init-db.yml")
    @ExpectedDataSet("role-dao/result-remove.yml")
    public void testRemove() throws MySQLException {
        dao.remove(new Role(2, "ADMIN"));
    }

    @Test
    @DataSet("role-dao/init-db.yml")
    @ExpectedDataSet("role-dao/init-db.yml")
    public void testFindAll() throws MySQLException {
        final List all = dao.findAll();
        assertEquals(
                Arrays.asList(new Role(1, "USER"), new Role(2, "ADMIN")),
                all
        );
    }

    @Test
    @DataSet("role-dao/init-db.yml")
    @ExpectedDataSet("role-dao/init-db.yml")
    public void testFindByIdSuccess() throws MySQLException {
        final Role role = dao.findById(1);
        assertEquals(
                new Role(1, "USER"),
                role
        );
    }

    @Test
    @DataSet("role-dao/init-db.yml")
    @ExpectedDataSet("role-dao/init-db.yml")
    public void testFindByIdFailed() throws MySQLException {
        final Role role = dao.findById(-1);
        assertNull(role);
    }

    @Test
    @DataSet("role-dao/init-db.yml")
    @ExpectedDataSet("role-dao/init-db.yml")
    public void testFindByNameSuccess() throws MySQLException {
        final Role role = dao.findByName("USER");
        assertEquals(
                new Role(1, "USER"),
                role
        );
    }

    @Test
    @DataSet("role-dao/init-db.yml")
    @ExpectedDataSet("role-dao/init-db.yml")
    public void testFindByNameFailed() throws MySQLException {
        final Role role = dao.findByName("TEST");
        assertNull(role);
    }
}
