package com.learn.servlets;

import com.learn.utils.RoutingUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет, получающий данные с  admin-main.jsp для создания нового полтзователя
 *
 * @author Volodymyr Lopachak
 * @version 1.0 08 September 2021
 */
@WebServlet(value = "/admin/create")
public class CreateUserServlet extends HttpServlet {
    public static final Logger LOGGER = Logger.getLogger(CreateUserServlet.class.getName());

    /**
     * При выборе создания нового полтзователя метод переходит на страницу его создания
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // получаем текущего пользователя
        req.setAttribute("name", req.getSession().getAttribute("currentUser"));
        req.setAttribute("massage", "CREATE NEW USER");
        req.getSession().setAttribute("patch", "admin/save");
        LOGGER.info("Create new user ");
        RoutingUtils.forwardToPage("create-form.jsp", req, resp);
    }
}
