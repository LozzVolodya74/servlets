package com.learn.servlets;

import com.learn.service.impl.FormService;
import com.learn.dao.dao_impl.DaoFactory;
import com.learn.dao.dao_impl.JdbcPersonDao;
import com.learn.entity.Person;
import com.learn.exception.MySQLException;
import com.learn.form.UserForm;
import com.learn.utils.RoutingUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Сервлет, получающий данные с  create-form.jsp и обрабатывает их
 *
 * @author Volodymyr Lopachak
 */
@WebServlet(value = "/admin/save")
public class SaveServlet extends HttpServlet {
    public static final Logger LOGGER = Logger.getLogger(SaveServlet.class.getName());

    /**
     * Метод обрабатывает полученные данные с формы, на основании их создаёт нового пользователи
     * и записывает его в базу данных и передаёт управление на RefreshDataAdminServlet
     */
    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        JdbcPersonDao personDao = (JdbcPersonDao) DaoFactory.getDao("person");
        Map<String, String> errors = new HashMap<>();
        FormService service = new FormService();
        UserForm form;

        if (req.getSession().getAttribute("form") == null) {
            form = new UserForm();
        } else {
            form = (UserForm) req.getSession().getAttribute("form");
        }

        errors.clear();
        service.fillForm(form, req);
        service.validForm(form, errors);

        if (!form.isFully()) { // когда форма не полная

            service.filErrorAttribute(req, form, errors);

            req.getSession().setAttribute("form", form);
            req.setAttribute("ERRORS", errors);

            RoutingUtils.forwardToPage("create-form.jsp", req, resp);
        } else {
            String role = req.getParameter("isRole");

            long roleId = role.equals("user") ? 1 : 2;
            Date date = Date.valueOf(form.getDob());

            Person person = null;
            try {
                person = new Person(personDao.findMaxPersonId(), roleId, form.getFirstName(), form.getLastName(),
                        form.getLogin(), date, form.getPassword(), form.getEmail());
            } catch (MySQLException e) {
                LOGGER.error(e.getMessage());
            }
            try {
                personDao.create(person);
            } catch (MySQLException e) {
                LOGGER.error(e.getMessage());
            }
            errors.clear();
            req.getSession().setAttribute("form", null);

            LOGGER.info("CREATE NEW USER== " + person);
            RoutingUtils.redirect("/admin/refresh", resp);
        }
    }
}
