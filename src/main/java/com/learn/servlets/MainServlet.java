package com.learn.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * Сервлет, получающий данные с index.jsp и перенаправляющий их на страницу регистрации
 *
 * @author Volodymyr Lopachak
 * @version 1.0 08 September 2021
 */
@WebServlet(value = "/main")
public class MainServlet extends HttpServlet {
    public static final Logger LOGGER = Logger.getLogger(MainServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // очистить сессию
        HttpSession session = req.getSession();
        session.invalidate();

        LOGGER.info("MainServlet started ");
        req.getRequestDispatcher("WEB-INF/JSP/login.jsp").forward(req, resp);
    }
}
