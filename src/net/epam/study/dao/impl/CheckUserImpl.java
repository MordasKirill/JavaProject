package net.epam.study.dao.impl;

import net.epam.study.controller.command.impl.GoToMainPage;
import net.epam.study.dao.CheckUserDAO;
import net.epam.study.listener.Listener;
import net.epam.study.service.HashPasswordService;
import net.epam.study.service.ServiceProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckUserImpl implements CheckUserDAO {
    ServiceProvider serviceProvider = ServiceProvider.getInstance();
    HashPasswordService hashPasswordService = serviceProvider.getHashPasswordService();
    //ConnectionPool connectionPool = new ConnectionPool(urlDB, passwordDB, userNameDB, driver, 5);
    public static String role;
    public static String error;
    public static final String selectFrom = "select login, password, role from users where login =";
    public static final String columnLogin = "login";
    public static final String columnPassword = "password";
    public static final String columnRole = "role";
    public boolean check(String login, String password) {
        boolean result = false;
        Connection connection = Listener.connectionPool.retrieve();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(selectFrom + "'" + login + "'");
            System.out.println("SUCCESS DB: Connected.");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString(columnLogin).equals(login)
                    &&hashPasswordService.checkHashPassword(password, resultSet.getString(columnPassword))) {
                    System.out.println("SUCCESS: Login success.");
                    GoToMainPage.userLogin = login;
                    result = true;
                    break;
                }
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
            error = "Failed to check user !";
            System.out.println("FAIL DB: Fail to write DB.");
        }
        Listener.connectionPool.putBack(connection);
        return result;
    }
    public boolean isAdmin(String login){
        boolean result = false;
        Connection connection = Listener.connectionPool.retrieve();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(selectFrom + "'" + login + "'");
            System.out.println("SUCCESS DB: Connected.");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString(columnLogin).equals(login)){
                    System.out.println("SUCCESS: Role checked.");
                    role = resultSet.getString(columnRole);
                    result = true;
                    break;
                }
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
            error = "Failed to check user role !";
            System.out.println("FAIL DB: Fail to write DB.");
        }
        Listener.connectionPool.putBack(connection);
        return result;
    }
}