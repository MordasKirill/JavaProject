package net.epam.study.dao;

import net.epam.study.connection.ConnectionToDB;
import net.epam.study.controller.commands.impl.GoToMainPage;

import java.sql.*;

public class LoginAndPasswordValidate {
    public static String role;
    public static boolean validate (String login, String password) {
        boolean result = false;
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(ConnectionToDB.connectionUrl, ConnectionToDB.userNameDB, ConnectionToDB.passwordDB);
            System.out.println("SUCCESS DB: Connected.");
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select login, password from users where login ='" + login + "' and password ='" + password + "'");
            if(resultSet.next()) {
                System.out.println("SUCCESS: Login success.");
                GoToMainPage.userLogin = login;
                result = true;
            } else {
                System.out.println("FAIL: Incorrect pass or login.");
                result = false;
            }
        } catch (ClassNotFoundException | SQLException exc) {
            exc.printStackTrace();
            System.out.println("FAIL DB: Fail to write DB.");
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
                if (statement != null) statement.close();
            } catch (SQLException exc) {
            }
        }
        return result;
    }
    public static boolean isAdmin(String login){
        boolean result = false;
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(ConnectionToDB.connectionUrl, ConnectionToDB.userNameDB, ConnectionToDB.passwordDB);
            System.out.println("SUCCESS DB: Connected.");
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select role from users where login ='" + login + "'");
            if(resultSet.next()) {
                System.out.println("SUCCESS: Role checked.");
                role = resultSet.getString("role");
                result = true;
            } else {
                System.out.println("FAIL: Fail to check role.");
                result = false;
            }
        } catch (ClassNotFoundException | SQLException exc) {
            exc.printStackTrace();
            System.out.println("FAIL DB: Fail to write DB.");
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
                if (statement != null) statement.close();
            } catch (SQLException exc) {
            }
        }
        return result;
    }
}
