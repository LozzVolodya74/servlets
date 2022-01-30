package com.learn.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.learn.exception.MySQLException;
import com.learn.service.impl.XssFixerImpl;
import com.learn.dao.dao_impl.DaoFactory;
import com.learn.dao.dao_impl.JdbcPersonDao;
import com.learn.dao.dao_impl.JdbcRoleDao;
import com.learn.entity.Person;
import com.learn.entity.Role;
import com.learn.utils.RoutingUtils;
import org.apache.log4j.Logger;

/**
 * Сервлет, получающий данные с login.jsp и обрабатывает их
 *
 * @author Volodymyr Lopachak
 * @version 1.0 08 September 2021
 */
@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {
    public static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

    /**
     * Метод пееходит на страницу обновления данных - RefreshDataAdminServlet
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/admin/refresh");
    }

    /**
     * Метод получает данные запроса логирования, обрабатывает их, проверяет наличие пользователя в базе данных
     * и в зависимости от роли переходит:
     * - на hello-user.jsp если роль user
     * - на admin-main.jsp если роль admin
     * - на страницу регистрации если введены неправильный логин или пароль
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        XssFixerImpl fixer = new XssFixerImpl();

        String login = fixer.fix(req.getParameter("login"));
        String password = fixer.fix(req.getParameter("password"));

        JdbcPersonDao personDao = (JdbcPersonDao) DaoFactory.getDao("person");
        JdbcRoleDao roleDao = (JdbcRoleDao) DaoFactory.getDao("role");

        Person person = null;
        try {
            person = personDao.findByLogin(login);
        } catch (MySQLException e) {
            LOGGER.error(e.getMessage());
        }

        if (person != null && password.equals(person.getPassword())) {
            Role role = null;
            try {
                role = roleDao.findById(person.getRoleId());
            } catch (MySQLException e) {
                LOGGER.error(e.getMessage());
            }

            if (role.getRole().equals("user")) {
                req.setAttribute("name", person.getFirstName());
                RoutingUtils.forwardToPage("hello-user.jsp", req, resp);
            }

            if (role.getRole().equals("admin")) {
                // устанавливаем роль для фильтра
                req.getSession().setAttribute("role", "admin");

                // устанавливаем текущего пользователя
                req.getSession().setAttribute("currentUser", person.getFirstName());

                req.setAttribute("name", person.getFirstName());
                try {
                    req.setAttribute("person", RefreshDataAdminServlet.refresh());
                } catch (MySQLException e) {
                    LOGGER.error(e.getMessage());
                }
                RoutingUtils.forwardToPage("admin-main.jsp", req, resp);
            }
        } else {
            req.setAttribute("massage", "INPUT CORRECT LOGIN OR PASSWORD");
            RoutingUtils.forwardToPage("login.jsp", req, resp);
        }
    }
}
