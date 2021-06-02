package net.epam.study.dao.impl;

import net.epam.study.controller.command.impl.GoToMainPage;
import net.epam.study.dao.CheckUserDAO;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.connection.ConnectionPool;
import net.epam.study.service.HashPasswordService;
import net.epam.study.service.ServiceProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckUserImpl implements CheckUserDAO {
    ServiceProvider serviceProvider = ServiceProvider.getInstance();
    HashPasswordService hashPasswordService = serviceProvider.getHashPasswordService();
    //todo remove static
    public static String role;
    public static final String selectFrom = "select login, password, role from users where login =";
    public static final String columnLogin = "login";
    public static final String columnPassword = "password";
    public static final String columnRole = "role";
    public boolean check(String login, String password) throws DAOException {
        boolean result = false;
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(selectFrom + "'" + login + "'");
            System.out.println("SUCCESS DB: Connected.");
            resultSet = statement.executeQuery();
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
            System.out.println("FAIL DB: Fail to write DB.");
            throw new DAOException(exc);
        } finally {
            ConnectionPool.connectionPool.putBack(connection);
            try {
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

        return result;
    }
    public boolean isAdmin(String login) throws DAOException {
        boolean result = false;
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(selectFrom + "'" + login + "'");
            System.out.println("SUCCESS DB: Connected.");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString(columnLogin).equals(login)){
                    System.out.println("SUCCESS: Role checked.");
                    role = resultSet.getString(columnRole);
                    result = true;
                    break;
                }
            }
        } catch (SQLException exc) {
            System.out.println("FAIL DB: Fail to write DB.");
            throw new DAOException(exc);
        } finally {
            ConnectionPool.connectionPool.putBack(connection);
            try {
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

        return result;
    }
}
