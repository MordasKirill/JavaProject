package net.epam.study.dao.impl;

import net.epam.study.dao.CheckUserDAO;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.connection.ConnectionPool;
import net.epam.study.dao.connection.ConnectionPoolException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckUserImpl implements CheckUserDAO {


    public static final String SELECT_FROM = "select login, password, role from users where login =";
    public static final String COLUMN_LOGIN = "login";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ROLE = "role";
    private static final Logger log = Logger.getLogger(CheckUserImpl.class);

    public boolean isUserExists(String login, String password) throws DAOException, ConnectionPoolException {

        boolean result = false;

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            statement = connection.prepareStatement(SELECT_FROM + "'" + login + "'");
            log.info("SUCCESS DB: Connected.");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {

                if (resultSet.getString(COLUMN_LOGIN).equals(login)
                    && BCrypt.checkpw(password, resultSet.getString(COLUMN_PASSWORD))) {
                    log.info("SUCCESS: Login success.");
                    result = true;
                    break;
                }
            }
        } catch (SQLException exc) {
            log.log(Level.ERROR,"FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);
        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }

        return result;
    }
    public String getUserRole(String login) throws DAOException, ConnectionPoolException {

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(SELECT_FROM + "'" + login + "'");
            log.info("SUCCESS DB: Connected.");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {

                if (resultSet.getString(COLUMN_LOGIN).equals(login)){
                    log.info("SUCCESS: Role checked.");
                    return resultSet.getString(COLUMN_ROLE);
                }
            }

        } catch (SQLException exc) {
            log.log(Level.ERROR,"FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);

        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }
        return null;
    }
}
