package net.epam.study.dao.impl;

import net.epam.study.dao.CheckUserDAO;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.connection.ConnectionPool;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckUserImpl implements CheckUserDAO {

    public static final String selectFrom = "select login, password, role from users where login =";
    public static final String columnLogin = "login";
    public static final String columnPassword = "password";
    public static final String columnRole = "role";
    private static final Logger log = Logger.getLogger(CheckUserImpl.class);

    public boolean isUserExists(String login, String password) throws DAOException {

        boolean result = false;

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(selectFrom + "'" + login + "'");
            log.debug("SUCCESS DB: Connected.");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {

                if (resultSet.getString(columnLogin).equals(login)
                    && BCrypt.checkpw(password, resultSet.getString(columnPassword))) {
                    log.debug("SUCCESS: Login success.");
                    result = true;
                    break;
                }
            }
        } catch (SQLException exc) {
            log.debug("FAIL DB: Fail to write DB.");
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
    public String getUserRole(String login) throws DAOException {

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(selectFrom + "'" + login + "'");
            log.debug("SUCCESS DB: Connected.");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {

                if (resultSet.getString(columnLogin).equals(login)){
                    log.debug("SUCCESS: Role checked.");
                    return resultSet.getString(columnRole);
                }
            }

        } catch (SQLException exc) {
            log.debug("FAIL DB: Fail to write DB.");
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

        return null;
    }
}
