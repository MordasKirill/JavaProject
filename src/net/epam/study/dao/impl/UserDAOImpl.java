package net.epam.study.dao.impl;

import net.epam.study.Constants;
import net.epam.study.bean.User;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.UserDAO;
import net.epam.study.dao.connection.ConnectionPool;
import net.epam.study.dao.connection.ConnectionPoolException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static final String COLUMN_LOGIN = "login";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ROLE = "role";
    private static final String COLUMN_USER_LOGIN = "login";
    private static final String COLUMN_ID_USER = "id";

    private static final String SELECT_LOGIN_PASSWORD_ROLE_FROM_USERS_WHERE_LOGIN = "select login, password, role from users where login = ?";
    private static final String SELECT_LOGIN_PASSWORD_ROLE_FROM_USERS_WHERE_ID = "select id, password, role from users where id = ?";
    private static final String SELECT_LOGIN_FROM_USERS_WHERE_LOGIN = "select login from users where login =?";
    private static final String INSERT_INTO = "INSERT INTO users (login,password,role) VALUES (?,?,?)";
    private static final String SELECT_ALL_USERS = "select id from users where id>0";
    private static final String SELECT_FROM_USERS = "select id, login, role from users where id>0 LIMIT ?,?";
    private static final String DELETE_FROM_USERS = "delete from users where id = ?";
    private static final String GET_USER_ID = "SELECT id, login FROM users WHERE login= ?";
    private static final String UPDATE_USER_ROLE = "update users set role = ? where id = ?";

    private static final Logger LOG = Logger.getLogger(UserDAOImpl.class);

    public int createNewUser(User user) throws DAOException, ConnectionPoolException {
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_INTO, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            statement.executeUpdate();
            LOG.info("SUCCESS DB: User created.");
            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            throw new DAOException("Order not created! " + user.toString());
        } catch (SQLException exc) {
            LOG.log(Level.ERROR, "FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);
        } finally {
            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement);
        }
    }

    public boolean isUserDataCorrect(User user) throws DAOException, ConnectionPoolException {
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SELECT_LOGIN_PASSWORD_ROLE_FROM_USERS_WHERE_LOGIN);
            statement.setString(1, user.getLogin());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString(COLUMN_LOGIN).equals(user.getLogin()) &&
                        BCrypt.checkpw(user.getPassword(), resultSet.getString(COLUMN_PASSWORD))) {
                    LOG.info("SUCCESS: Login success.");
                    return true;
                }
            }
            return false;
        } catch (SQLException exc) {
            LOG.log(Level.ERROR, "FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);
        } finally {
            ConnectionPool.connectionPool.putBack(connection);
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }
    }


    public String getUserRole(int userId) throws DAOException {
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SELECT_LOGIN_PASSWORD_ROLE_FROM_USERS_WHERE_ID);
            statement.setInt(1, userId);
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
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }
        throw new DAOException("");
    }


    public boolean isUserUnique(String login) throws DAOException {
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SELECT_LOGIN_FROM_USERS_WHERE_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString(COLUMN_LOGIN).equals(login)) {
                    LOG.info("FAIL DB: User already exist.");
                    return false;
                }
            }
            return true;
        } catch (SQLException exc) {
            LOG.log(Level.ERROR, "FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);
        } finally {
            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement);
        }
    }

    @Override
    public List<User> getUsers(int limit) throws DAOException {
        List<User> users = new ArrayList<>();
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
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
        } catch (SQLException | RuntimeException exc) {
            LOG.log(Level.ERROR, "FAIL DB: Fail to show users.", exc);
            throw new DAOException(exc);
        } finally {
            ConnectionPool.connectionPool.putBack(connection);
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }
        return users;
    }

    public List<User> getAllUsers() throws DAOException {
        List<User> users = new ArrayList<>();
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SELECT_ALL_USERS);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getString(COLUMN_ID_USER));
                users.add(user);
            }
        } catch (SQLException | RuntimeException exc) {
            LOG.log(Level.ERROR, "FAIL DB: Fail to get all orders.", exc);
            throw new DAOException(exc);
        } finally {
            ConnectionPool.connectionPool.putBack(connection);
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }
        return users;
    }

    public void deleteUser(String id) throws DAOException {
        List<Object> paramList = new ArrayList<>();
        paramList.add(id);
        DAOProvider.getInstance().getDBCommonCRUDOperationDAO().executeUpdate(DELETE_FROM_USERS, paramList);
    }

    public int getUserId(String login) throws DAOException {
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(GET_USER_ID);
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            LOG.info("SUCCESS DB: User id success.");
            while (resultSet.next()) {
                if (login.equals(resultSet.getString(COLUMN_USER_LOGIN))) {
                    return Integer.parseInt(resultSet.getString(COLUMN_ID_USER));
                }
            }
            throw new DAOException("FAIL DB: Fail to get user id.");
        } catch (SQLException exc) {
            LOG.log(Level.ERROR, "FAIL DB: Fail to get user id.", exc);
            throw new DAOException(exc);
        } finally {
            ConnectionPool.connectionPool.putBack(connection);
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }
    }

    public void changeUserRole(String status, int id) throws DAOException {
        List<Object> paramList = new ArrayList<>();
        paramList.add(status);
        paramList.add(id);
        DAOProvider.getInstance().getDBCommonCRUDOperationDAO().executeUpdate(UPDATE_USER_ROLE, paramList);
    }
}
