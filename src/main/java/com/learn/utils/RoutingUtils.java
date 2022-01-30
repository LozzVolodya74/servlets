package com.learn.utils;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Метод перенаправляет запросы на соответствующий контроллер либо JSP - страницу
 *
 * @author Volodymyr Lopachak
 * @version 1.0
 */
public final class RoutingUtils {

    /**
     *  Перенаправляет на соответствующий сервлет - контроллер
     * @param url - на какой URL - сервлета перенаправить
     * @param resp - HttpServletResponse
     * @throws IOException
     */
    public static void redirect(final String url, final HttpServletResponse resp) throws IOException {
        resp.sendRedirect(url);
    }

    /**
     *
     * @param jspPage - на какую странице перенаправить
     * @param req - HttpServletRequest
     * @param resp - HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public static void forwardToPage(final String jspPage, final HttpServletRequest req,
                                     final HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/JSP/" + jspPage).forward(req, resp);
    }

    private RoutingUtils() {
    }
}
