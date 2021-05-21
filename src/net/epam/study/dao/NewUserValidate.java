package net.epam.study.dao;

import net.epam.study.controller.Listener;
import net.epam.study.controller.commands.impl.GoToMainPage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NewUserValidate {
    public static String error;
    public static boolean validate (String sqlCommand, String login)  {
        boolean result = true;
        Connection connection = Listener.connection;
        PreparedStatement statement;
        try {
            System.out.println("SUCCESS DB: Connected.");
            statement = connection.prepareStatement("select login from users where login ='" + login + "'");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()
                    &&resultSet.getString("login").equals(login)) {
                System.out.println("FAIL DB: User already exist.");
                result = false;
            } else{
                System.out.println("SUCCESS DB: User created.");
                GoToMainPage.userLogin = login;
                statement.executeUpdate(sqlCommand);
                result = true;
            }
            error = "Failed to check if user exists!";
        } catch (SQLException exc) {
            exc.printStackTrace();
            error = "Failed to check if user exists!";
            System.out.println("FAIL DB: Fail to write DB.");
        }
        return result;
    }
}
