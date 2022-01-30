package com.learn.servlets;

import com.learn.dao.dao_impl.DaoFactory;
import com.learn.dao.dao_impl.JdbcPersonDao;
import com.learn.entity.Person;
import com.learn.exception.MySQLException;
import com.learn.utils.RoutingUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Сервлет, получающий данные с create-form.jsp  и с SaveServlet для обновления данных на странице
 *
 * @author Volodymyr Lopachak
 * @version 1.0 08 September 2021
 */

@WebServlet(value = "/admin/refresh")
public class RefreshDataAdminServlet extends HttpServlet {
    public static final Logger LOGGER = Logger.getLogger(RefreshDataAdminServlet.class.getName());

    /**
     * Метод обновляет список пользователей для страницы
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // получаем текущего пользователя
        req.setAttribute("name", req.getSession().getAttribute("currentUser"));
        try {
            req.setAttribute("person", refresh());
        } catch (MySQLException e) {
            LOGGER.error(e.getMessage());
        }
        RoutingUtils.forwardToPage("admin-main.jsp", req, resp);
    }

    /**
     * Метод обновляет список пользователей для страницы
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // получаем текущего пользователя
        req.setAttribute("name", req.getSession().getAttribute("currentUser"));
        try {
            req.setAttribute("person", refresh());
        } catch (MySQLException e) {
            LOGGER.error(e.getMessage());
        }
        req.getRequestDispatcher("WEB-INF/JSP/admin-main.jsp").forward(req, resp);
    }

    /**
     * @return обновлённый список пользователей
     */
    protected static List<Person> refresh() throws MySQLException {
        JdbcPersonDao personDao = (JdbcPersonDao) DaoFactory.getDao("person");
        return personDao.findAll();
    }
}
