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


    public static final String SELECT_LOGIN_PASSWORD_ROLE_FROM_USERS_WHERE_LOGIN = "select login, password, role from users where login = ?";
    public static final String COLUMN_LOGIN = "login";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ROLE = "role";
    public static final String SELECT_LOGIN_FROM_USERS_WHERE_LOGIN = "select login from users where login =?";
    public static final String INSERT_INTO = "INSERT INTO users (login,password,role) VALUES (?,?,?)";
    private static final Logger LOG = Logger.getLogger(CheckUserImpl.class);

    public boolean isUserExists(String login, String password) throws DAOException, ConnectionPoolException {

        boolean result = false;

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            statement = connection.prepareStatement(SELECT_LOGIN_PASSWORD_ROLE_FROM_USERS_WHERE_LOGIN);
            statement.setString(1, login);

            LOG.info("SUCCESS DB: Connected.");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {

                if (resultSet.getString(COLUMN_LOGIN).equals(login)
                    && BCrypt.checkpw(password, resultSet.getString(COLUMN_PASSWORD))) {
                    LOG.info("SUCCESS: Login success.");
                    result = true;
                    break;
                }
            }
        } catch (SQLException exc) {
            LOG.log(Level.ERROR,"FAIL DB: Fail to write DB.", exc);
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
            statement = connection.prepareStatement(SELECT_LOGIN_PASSWORD_ROLE_FROM_USERS_WHERE_LOGIN);
            statement.setString(1, login);

            LOG.info("SUCCESS DB: Connected.");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {

                if (resultSet.getString(COLUMN_LOGIN).equals(login)){
                    LOG.info("SUCCESS: Role checked.");
                    return resultSet.getString(COLUMN_ROLE);
                }
            }

        } catch (SQLException exc) {
            LOG.log(Level.ERROR,"FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);

        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }
        return null;
    }


    public boolean isUserNew (String login, String hashPassword, String role) throws DAOException, ConnectionPoolException {

        boolean result = true;
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;

        try {
            LOG.info("SUCCESS DB: Connected.");

            statement = connection.prepareStatement(SELECT_LOGIN_FROM_USERS_WHERE_LOGIN);
            statement.setString(1, login);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()
                    &&resultSet.getString(COLUMN_LOGIN).equals(login)) {
                LOG.info("FAIL DB: User already exist.");
                result = false;

            } else{
                statement.executeUpdate(INSERT_INTO);
                statement.setString(1, login);
                statement.setString(2, hashPassword);
                statement.setString(3, role);
                LOG.info("SUCCESS DB: User created.");
            }

        } catch (SQLException exc) {

            LOG.log(Level.ERROR,"FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);
        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement);
        }

        return result;
    }
}
