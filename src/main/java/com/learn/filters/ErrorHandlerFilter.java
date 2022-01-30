package com.learn.filters;

import com.learn.utils.RoutingUtils;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class содержит методы для обработки всех ошибок, которые возникают в приложении
 *
 * @author Volodymyr Lopachak
 * @version 1.0 08 September 2021
 */
@WebFilter("/*")
public class ErrorHandlerFilter implements Filter {
    public static final Logger LOGGER = Logger.getLogger(ErrorHandlerFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        try {
            chain.doFilter(req, resp);
        } catch (Throwable th) { // может с фронта прилетают другие ошибки
            String requestUrl = ((HttpServletRequest)req).getRequestURI();
            LOGGER.error("Request " + requestUrl + " failed: " + th.getMessage(), th);
            req.setAttribute("message", "this resource not found, try again");
            RoutingUtils.forwardToPage("error.jsp", (HttpServletRequest) req, (HttpServletResponse) resp);
        }
    }

    @Override
    public void destroy() {
    }
}
