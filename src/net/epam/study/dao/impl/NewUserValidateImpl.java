package net.epam.study.dao.impl;

import net.epam.study.listener.Listener;
import net.epam.study.controller.command.impl.GoToMainPage;
import net.epam.study.dao.NewUserValidateDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NewUserValidateImpl implements NewUserValidateDAO {
    public static String error;
    public static final String columnLogin = "login";
    public static final String selectFrom = "select login from users where login =";
    public static final String insertInto = "INSERT INTO users (login,password,role) VALUES";
    public boolean validate (String login, String hashPassword, String role)  {
        boolean result = true;
        Connection connection = Listener.connectionPool.retrieve();
        PreparedStatement statement;
        try {
            System.out.println("SUCCESS DB: Connected.");
            statement = connection.prepareStatement(selectFrom + "'" + login + "'");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()
                    &&resultSet.getString(columnLogin).equals(login)) {
                System.out.println("FAIL DB: User already exist.");
                result = false;
            } else{
                System.out.println("SUCCESS DB: User created.");
                GoToMainPage.userLogin = login;
                statement.executeUpdate(insertInto + "('" + login + "','" + hashPassword + "','" + role + "')");
                result = true;
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
            error = "Failed to check if user exists!";
            System.out.println("FAIL DB: Fail to write DB.");
        }
        Listener.connectionPool.putBack(connection);
        return result;
    }
}
