package net.epam.study.controller.listener;

import net.epam.study.dao.connection.ConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Listener implements ServletContextListener {
    public static final String userNameDB = "root";
    public static final String passwordDB = "3158095KIRILLMordas";
    public static final String urlDB = "jdbc:mysql://localhost:3306/test";
    public static final String driver = "com.mysql.jdbc.Driver";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("ServletContextListener was created!");
        ConnectionPool.connectionPool = new ConnectionPool();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("ServletContextListener was destroyed!");
    }
}