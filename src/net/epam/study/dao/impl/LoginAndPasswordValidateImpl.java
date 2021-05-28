package net.epam.study.dao.impl;

import net.epam.study.listener.Listener;
import net.epam.study.controller.command.impl.GoToMainPage;
import net.epam.study.dao.UserLoginValidateDAO;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginAndPasswordValidateImpl implements UserLoginValidateDAO {
    public static String role;
    public static String error;
    public static final String selectFrom = "select login, password, role from users where login =";
    public static final String columnLogin = "login";
    public static final String columnPassword = "password";
    public static final String columnRole = "role";
    public boolean validate (String login, String password) {
        boolean result = false;

        Connection connection = Listener.connection;
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(selectFrom + "'" + login + "'");
            System.out.println("SUCCESS DB: Connected.");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                boolean passwordMatch = BCrypt.checkpw(password, resultSet.getString(columnPassword));
                if (resultSet.getString(columnLogin).equals(login)
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
    public boolean isAdmin(String login){
        boolean result = false;
        Connection connection = Listener.connection;
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
