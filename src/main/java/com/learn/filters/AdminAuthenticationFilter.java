package com.learn.filters;

import com.learn.utils.RoutingUtils;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class - фильтр для контроля входя на защищённые URL /admin
 *
 * @author Volodymyr Lopachak
 * @version 1.0 29 October 2021
 */
@WebFilter("/admin/*")
public class AdminAuthenticationFilter implements Filter {
    public static final Logger LOGGER = Logger.getLogger(AdminAuthenticationFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (req.getSession().getAttribute("role") == "admin") {
            chain.doFilter(request, response);
        } else {
            LOGGER.info("INCORRECT ROLE");
            //RoutingUtils.redirect("/main", resp);
            RoutingUtils.forwardToPage("user-error.jsp", req, resp);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
}
