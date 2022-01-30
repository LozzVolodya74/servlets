package com.learn.servlets;

import com.learn.exception.MySQLException;
import com.learn.dao.dao_impl.DaoFactory;
import com.learn.dao.dao_impl.JdbcPersonDao;
import com.learn.entity.Person;
import com.learn.utils.RoutingUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет, получающий данные с admin-main.jsp и обрабатывает их
 *
 * @author Volodymyr Lopachak
 * @version 1.0 08 September 2021
 */
@WebServlet(value = "/admin/update")
public class UpdateServlet extends HttpServlet {
    public static final Logger LOGGER = Logger.getLogger(UpdateServlet.class.getName());

    /**
     * Метод получает id пользователя, которого необходимо обновить и переходить на страницу
     * обновления пользователя create-form.jsp
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("up_id"));
        LOGGER.info("UPDATE GET " + id);
        JdbcPersonDao personDao = (JdbcPersonDao) DaoFactory.getDao("person");
        Person updatePerson = null;
        try {
            updatePerson = personDao.findById(id);
        } catch (MySQLException e) {
            LOGGER.error(e.getMessage());
        }
        // устанавливаем id обрабатываемого пользователя
        req.getSession().setAttribute("userIdUpdate", id);
        req.setAttribute("massage", "UPDATE USER");
        req.getSession().setAttribute("patch", "admin/save_update");
        req.setAttribute("update", "uu");

        req.setAttribute("log_ph", updatePerson.getLogin());
        req.setAttribute("pas_ph", updatePerson.getPassword());
        req.setAttribute("email_ph", updatePerson.getEmail());
        req.setAttribute("first_ph", updatePerson.getFirstName());
        req.setAttribute("last_ph", updatePerson.getLastName());
        req.setAttribute("dob_ph", updatePerson.getDob());

        RoutingUtils.forwardToPage("create-form.jsp", req, resp);
    }
}
