package net.epam.study.controller.listener;

import net.epam.study.dao.connection.ConnectionPool;
import net.epam.study.dao.email.SendEmail;
import net.epam.study.service.validation.impl.ValidationImpl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Locale;

public class Listener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(Listener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOG.log(Level.INFO, "ServletContextListener was created!");
        ValidationImpl.userLocale = String.valueOf(Locale.getDefault().getCountry()).toLowerCase();
        ConnectionPool.connectionPool = new ConnectionPool();
        SendEmail.sendEmail = new SendEmail();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOG.log(Level.INFO, "ServletContextListener was destroyed!");
        ConnectionPool.connectionPool.dispose();
    }
}
