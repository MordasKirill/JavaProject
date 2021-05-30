package net.epam.study.listener;

import net.epam.study.connection.ConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Listener implements ServletContextListener {
    public static final String userNameDB = "root";
    public static final String passwordDB = "3158095KIRILLMordas";
    public static final String urlDB = "jdbc:mysql://localhost:3306/test";
    public static final String driver = "com.mysql.jdbc.Driver";
    public static ConnectionPool connectionPool;
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("ServletContextListener was created!");
        connectionPool = new ConnectionPool(urlDB, passwordDB, userNameDB, driver, 5);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("ServletContextListener was destroyed!");
    }
}
