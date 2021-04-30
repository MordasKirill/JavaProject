package net.epam.study.dao;

import net.epam.study.controller.commands.impl.GoToMainPage;

import java.sql.*;

public class LoginAndPasswordValidate {
    public static boolean validate (String login, String password) {
        boolean result = false;
        Connection connection = null;
        Statement statement = null;
        try {
            String userNameDB = "root";
            String passwordDB = "3158095KIRILLMordas";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String connectionUrl = "jdbc:mysql://localhost:3306/test";
            connection = DriverManager.getConnection(connectionUrl, userNameDB, passwordDB);
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
}
