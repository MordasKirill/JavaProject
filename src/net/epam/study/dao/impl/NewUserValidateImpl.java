package net.epam.study.dao.impl;

import net.epam.study.dao.DAOException;
import net.epam.study.dao.NewUserValidateDAO;
import net.epam.study.dao.connection.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NewUserValidateImpl implements NewUserValidateDAO {
    public static final String columnLogin = "login";
    public static final String selectFrom = "select login from users where login =";
    public static final String insertInto = "INSERT INTO users (login,password,role) VALUES";

    public boolean validate (String login, String hashPassword, String role) throws DAOException {

        boolean result = true;
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;

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
                statement.executeUpdate(insertInto + "('" + login + "','" + hashPassword + "','" + role + "')");
            }

        } catch (SQLException exc) {
            exc.printStackTrace();
            System.out.println("FAIL DB: Fail to write DB.");
            throw new DAOException(exc);
        } finally {
            ConnectionPool.connectionPool.putBack(connection);
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

        return result;
    }
}
