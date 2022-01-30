package com.learn.listener;

import com.learn.jdbc.ConnectionManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author Volodymyr Lopachak
 * @version 1.0 08 September 2021
 */
@WebListener
public class ApplicationListener implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(ApplicationListener.class);

    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        ConnectionManager.initDataSource();
        LOGGER.info("Application started");
    }

    @Override
    public void contextDestroyed(final ServletContextEvent sce) {
        ConnectionManager.releaseDataSource();
        LOGGER.info("Application stopped");
    }
}
