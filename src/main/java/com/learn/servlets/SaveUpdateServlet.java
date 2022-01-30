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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Сервлет, получающий данные с create-form.jsp и обрабатывает их
 *
 * @author Volodymyr Lopachak
 * @version 1.1
 */
@WebServlet(value = "/admin/save_update")
public class SaveUpdateServlet extends HttpServlet {
    public static final Logger LOGGER = Logger.getLogger(SaveUpdateServlet.class.getName());

    /**
     * Метод обрабатывает полученные данные с create-form.jsp и перезаписывает изменённого пользователя
     * и переходит на RefreshDataAdminServlet
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JdbcPersonDao personDao = (JdbcPersonDao) DaoFactory.getDao("person");

        // получаем обрабатываемого пользователя по id
        Person person = null;
        try {
            person = personDao.findById((long) req.getSession().getAttribute("userIdUpdate"));
        } catch (MySQLException e) {
            LOGGER.error(e.getMessage());
        }

        Map<String, String> errors = new HashMap<>();
        FormService service = new FormService();

        UserForm userForm;

        if (req.getSession().getAttribute("form") == null) {
            userForm = new UserForm();
        } else {
            userForm = (UserForm) req.getSession().getAttribute("form");
        }

        userForm = personToForm(person);
        // заполняем данными что ввёл пользователь
        fillDataUser(userForm, req);
        service.validForm(userForm, errors);

        if (!userForm.isFully()) { // когда есть ошибки

            service.filErrorAttribute(req, userForm, errors);

            req.getSession().setAttribute("form", userForm);
            req.setAttribute("ERRORS", errors);

            RoutingUtils.forwardToPage("create-form.jsp", req, resp);
        } else {
            errors.clear();
            req.getSession().setAttribute("form", null);

            formToPerson(userForm, person);

            String role = req.getParameter("isRole");
            person.setRoleId(role.equals("user") ? 1 : 2);

            try {
                personDao.update(person);
            } catch (MySQLException e) {
                LOGGER.error(e.getMessage());
            }

            LOGGER.info("UPDATE POST " + person);
            RoutingUtils.redirect("/admin/refresh", resp);
        }
    }

    private void formToPerson(UserForm form, Person person) {
        person.setPassword(form.getPassword());
        person.setEmail(form.getEmail());
        person.setFirstName(form.getFirstName());
        person.setLastName(form.getLastName());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date langDate = null;
        try {
            langDate = sdf.parse(form.getDob());
        } catch (ParseException e) {
            LOGGER.error("INCORRECT format data!");
        }
        person.setDob(new Date(langDate.getTime()));
    }

    private void fillDataUser(UserForm form, final HttpServletRequest req) {
        form.setPassword(req.getParameter("password"));
        form.setPassword_again(req.getParameter("passwordAgain"));
        form.setEmail(req.getParameter("email"));
        form.setFirstName(req.getParameter("firstName"));
        form.setLastName(req.getParameter("lastName"));
        form.setDob(req.getParameter("birthday"));
    }

    private UserForm personToForm(Person person) {
        return new UserForm(person.getFirstName(), person.getLastName(), person.getLogin(), person.getDob().toString(),
                person.getPassword(), person.getPassword(), person.getEmail());
    }
}
