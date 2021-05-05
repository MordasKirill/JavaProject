package net.epam.study.dao;

import net.epam.study.connection.ConnectionToDB;
import net.epam.study.controller.commands.impl.GoToMainPage;

import java.sql.*;

public class NewUserValidate {
    public static boolean validate (String sqlCommand, String login)  {
        boolean result = true;
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(ConnectionToDB.connectionUrl, ConnectionToDB.userNameDB, ConnectionToDB.passwordDB);
            System.out.println("SUCCESS DB: Connected.");
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select login from users where login ='" + login + "'");
            if (resultSet.next()) {
                System.out.println("FAIL DB: User already exist.");
                result = false;
            } else{
                System.out.println("SUCCESS DB: User created.");
                GoToMainPage.userLogin = login;
                statement.executeUpdate(sqlCommand);
                result = true;
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
