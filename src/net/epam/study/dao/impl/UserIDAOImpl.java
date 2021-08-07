package net.epam.study.dao.impl;

import net.epam.study.Constants;
import net.epam.study.bean.User;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.UsersDAO;
import net.epam.study.dao.connection.ConnectionPool;
import net.epam.study.dao.connection.ConnectionPoolException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserIDAOImpl implements UsersDAO {

    private static final String COLUMN_LOGIN = "login";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ROLE = "role";
    private static final String COLUMN_USER_LOGIN = "login";
    private static final String COLUMN_ID_USER = "id";

    private static final String SELECT_LOGIN_PASSWORD_ROLE_FROM_USERS_WHERE_LOGIN = "select id, password, role from users where id = ?";
    private static final String SELECT_LOGIN_FROM_USERS_WHERE_LOGIN = "select login from users where login =?";
    private static final String INSERT_INTO = "INSERT INTO users (login,password,role) VALUES (?,?,?)";
    private static final String SELECT_ALL_USERS = "select id from users where id>0";
    private static final String SELECT_FROM_USERS = "select id, login, role from users where id>0 LIMIT ?,?";
    private static final String DELETE_FROM_USERS = "delete from users where id = ?";
    private static final String GET_USER_ID = "SELECT id, login FROM users WHERE login= ?";
    private static final String UPDATE_USER_ROLE = "update users set role = ? where id = ?";

    private static final Logger LOG = Logger.getLogger(UserIDAOImpl.class);

    public int createNewUser(String login, String hashPassword, String role) throws DAOException, ConnectionPoolException {
        int userId = 0;
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_INTO, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, login);
            statement.setString(2, hashPassword);
            statement.setString(3, role);
            LOG.info("SUCCESS DB: Connected.");
            statement.executeUpdate();
            LOG.info("SUCCESS DB: User created.");
            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                userId = resultSet.getInt(1);
            }
        } catch (SQLException exc) {
            LOG.log(Level.ERROR, "FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);
        } finally {
            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement);
        }

        return userId;
    }

    public boolean isUserDataCorrect(int userId, String password) throws DAOException, ConnectionPoolException {

        boolean result = false;

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            statement = connection.prepareStatement(SELECT_LOGIN_PASSWORD_ROLE_FROM_USERS_WHERE_LOGIN);
            statement.setInt(1, userId);

            LOG.info("SUCCESS DB: Connected.");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {

                if (BCrypt.checkpw(password, resultSet.getString(COLUMN_PASSWORD))) {
                    LOG.info("SUCCESS: Login success.");
                    result = true;
                    break;
                }
            }
        } catch (SQLException exc) {
            LOG.log(Level.ERROR, "FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);
        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }

        return result;
    }


    public String getUserRole(int userId) throws DAOException, ConnectionPoolException {

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(SELECT_LOGIN_PASSWORD_ROLE_FROM_USERS_WHERE_LOGIN);
            statement.setInt(1, userId);

            LOG.info("SUCCESS DB: Connected.");
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                LOG.info("SUCCESS: Role checked.");
                return resultSet.getString(COLUMN_ROLE);

            }

        } catch (SQLException exc) {
            LOG.log(Level.ERROR, "FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);

        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }
        return null;
    }


    public boolean isUserUnique(String login) throws DAOException, ConnectionPoolException {
        boolean result = true;
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        try {
            LOG.info("SUCCESS DB: Connected.");
            statement = connection.prepareStatement(SELECT_LOGIN_FROM_USERS_WHERE_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString(COLUMN_LOGIN).equals(login)) {
                    LOG.info("FAIL DB: User already exist.");
                    result = false;
                }
            }
        } catch (SQLException exc) {
            LOG.log(Level.ERROR, "FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);
        } finally {
            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement);
        }
        return result;
    }

    @Override
    public List<User> getUsers(int limit) throws DAOException, ConnectionPoolException {

        List<User> users = new ArrayList<>();
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            LOG.info("SUCCESS DB: Connected.");

            statement = connection.prepareStatement(SELECT_FROM_USERS);
            statement.setInt(1, limit);
            statement.setInt(2, Constants.DEFAULT_LIMIT);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                User user = new User();
                user.setId(resultSet.getString(COLUMN_ID_USER));
                user.setLogin(resultSet.getString(COLUMN_LOGIN));
                user.setRole(resultSet.getString(COLUMN_ROLE));
                users.add(user);
            }

        } catch (SQLException exc) {

            LOG.log(Level.ERROR, "FAIL DB: Fail to show users.", exc);
            throw new DAOException(exc);
        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }

        return users;
    }

    public List<User> getAllUsers() throws DAOException, ConnectionPoolException {

        List<User> users = new ArrayList<>();
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            LOG.info("SUCCESS DB: Connected.");
            statement = connection.prepareStatement(SELECT_ALL_USERS);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                User user = new User();
                user.setId(resultSet.getString(COLUMN_ID_USER));
                users.add(user);
            }

        } catch (SQLException exc) {

            LOG.log(Level.ERROR, "FAIL DB: Fail to get all orders.", exc);
            throw new DAOException(exc);
        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }

        return users;
    }

    @Override
    public void deleteUser(String id) throws DAOException, ConnectionPoolException {

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement(DELETE_FROM_USERS);
            statement.setString(1, id);

            LOG.info("SUCCESS DB: Connected.");
            statement.executeUpdate();
            LOG.info("SUCCESS DB: User deleted.");

        } catch (SQLException exc) {

            LOG.log(Level.ERROR, "FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);
        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement);
        }
    }

    public int getUserId(String login) throws DAOException, ConnectionPoolException {

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int userId = 0;

        try {

            statement = connection.prepareStatement(GET_USER_ID);
            statement.setString(1, login);
            LOG.info("SUCCESS DB: Connected.");
            resultSet = statement.executeQuery();
            LOG.info("SUCCESS DB: User id success.");
            while (resultSet.next()) {
                if (login.equals(resultSet.getString(COLUMN_USER_LOGIN))) {
                    userId = Integer.parseInt(resultSet.getString(COLUMN_ID_USER));
                }
            }

        } catch (SQLException exc) {

            LOG.log(Level.ERROR, "FAIL DB: Fail to get user id.", exc);
            throw new DAOException(exc);
        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }
        return userId;
    }

    public void changeUserRole(String status, int id) throws DAOException {
        DAOProvider.getInstance().getDBCommonCRUDOperationDAO().executeUpdate(status, id, UPDATE_USER_ROLE);
    }
}
