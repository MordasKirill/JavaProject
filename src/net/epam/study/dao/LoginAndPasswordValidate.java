package net.epam.study.dao;

import net.epam.study.controller.Listener;
import net.epam.study.controller.commands.impl.GoToMainPage;

import java.sql.*;

public class LoginAndPasswordValidate {
    public static String role;
    public static boolean validate (String login, String password) {
        boolean result = false;
        Connection connection = Listener.connection;
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("select login, password from users where login ='" + login + "' and password ='" + password + "'");
            System.out.println("SUCCESS DB: Connected.");
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                if (resultSet.getString("login").equals(login)
                    &&resultSet.getString("password").equals(password)) {
                    System.out.println("SUCCESS: Login success.");
                    GoToMainPage.userLogin = login;
                    result = true;
                }
            } else {
                System.out.println("FAIL: Incorrect pass or login.");
                result = false;
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
            System.out.println("FAIL DB: Fail to write DB.");
        }
        return result;
    }
    public static boolean isAdmin(String login){
        boolean result = false;
        Connection connection = Listener.connection;
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("select role from users where login ='" + login + "'");
            System.out.println("SUCCESS DB: Connected.");
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                System.out.println("SUCCESS: Role checked.");
                role = resultSet.getString("role");
                result = true;
            } else {
                System.out.println("FAIL: Fail to check role.");
                result = false;
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
            System.out.println("FAIL DB: Fail to write DB.");
        }
        return result;
    }
}
