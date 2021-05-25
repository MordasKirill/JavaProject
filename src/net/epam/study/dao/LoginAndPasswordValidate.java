package net.epam.study.dao;

import net.epam.study.BCrypt.BCrypt;
import net.epam.study.controller.Listener;
import net.epam.study.controller.commands.impl.GoToMainPage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginAndPasswordValidate {
    public static String role;
    public static String error;
    public static boolean validate (String login, String password) {
        boolean result = false;

        Connection connection = Listener.connection;
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("select login, password from users where login ='" + login + "'");
            System.out.println("SUCCESS DB: Connected.");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                boolean passwordMatch = BCrypt.checkpw(password, resultSet.getString("password"));
                if (resultSet.getString("login").equals(login)
                    &&passwordMatch) {
                    System.out.println("SUCCESS: Login success.");
                    GoToMainPage.userLogin = login;
                    result = true;
                    break;
                }
            }
            //todo incorrect data action needed
//            if (!resultSet.next()){
//                System.out.println("FAIL: Incorrect pass or login.");
//                result = false;
//            }
        } catch (SQLException exc) {
            exc.printStackTrace();
            error = "Failed to check user !";
            System.out.println("FAIL DB: Fail to write DB.");
        }
        return result;
    }
    public static boolean isAdmin(String login){
        boolean result = false;
        Connection connection = Listener.connection;
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("select login, role from users where login ='" + login + "'");
            System.out.println("SUCCESS DB: Connected.");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("login").equals(login)){
                    System.out.println("SUCCESS: Role checked.");
                    role = resultSet.getString("role");
                    result = true;
                    break;
                }
//                else {
//                    System.out.println("FAIL: Fail to check role.");
//                    result = false;
//                }
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
            error = "Failed to check user role !";
            System.out.println("FAIL DB: Fail to write DB.");
        }
        return result;
    }
}
