package com.learn.servlets;

import com.learn.service.impl.XssFixerImpl;
import com.learn.dao.dao_impl.DaoFactory;
import com.learn.dao.dao_impl.JdbcPersonDao;
import com.learn.entity.Person;
import com.learn.exception.MySQLException;
import com.learn.utils.RoutingUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Сервлет, получающий данные с admin-main.jsp и обрабатывает их
 *
 * @author Volodymyr Lopachak
 * @version 1.0 08 September 2021
 */
@WebServlet(value = "/admin/delete")
public class DeleteServlet extends HttpServlet {
    public static final Logger LOGGER = Logger.getLogger(DeleteServlet.class.getName());

    XssFixerImpl fixer = new XssFixerImpl();

    /**
     * Получает данные со страницы sure.jsp как подтверждённые и удаляет указанного пользователя с БД
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(fixer.fix(req.getParameter("id")));
        JdbcPersonDao personDao = (JdbcPersonDao) DaoFactory.getDao("person");

        // получаем id обрабатываемого пользователя
        Person person = null;
        try {
            person = personDao.findById(id);
            personDao.remove(person);
        } catch (MySQLException e) {
            LOGGER.error(e.getMessage());
        }

        LOGGER.info("DELETE method POST " + person);
        RoutingUtils.redirect("/admin/refresh", resp);
    }

}
