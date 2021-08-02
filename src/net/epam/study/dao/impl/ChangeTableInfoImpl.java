package net.epam.study.dao.impl;

import net.epam.study.dao.ChangeTableInfoDAO;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.connection.ConnectionPool;
import net.epam.study.dao.connection.ConnectionPoolException;
import net.epam.study.service.CreateTableInfoService;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangeTableInfoImpl implements ChangeTableInfoDAO {

    public static final String UPDATE_ACCEPT = "update orders set status = ? where order_id = ?";
    public static final String UPDATE_ROLE = "update users set role = ? where id = ?";
    public static final String UPDATE_PAYMENT = "update payment set paymentStatus = ? where order_id = ?";
    public static final String COLUMN_ID_ORDER = "order_id";
    public static final String GET_LAST = "SELECT order_id, user_id FROM payment WHERE user_id= ? ORDER BY order_id DESC LIMIT 1";
    private static final Logger LOG = Logger.getLogger(ChangeTableInfoImpl.class);

    @Override
    public void changeStatus(String id, String status) throws DAOException, ConnectionPoolException {
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement(UPDATE_ACCEPT);
            statement.setString(1, status);
            statement.setString(2, id);

            LOG.info("SUCCESS DB: Connected.");
            statement.executeUpdate();
            LOG.info("SUCCESS DB: Order status changed.");

        } catch (SQLException exc) {

            LOG.log(Level.ERROR,"FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);
        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement);
        }
    }


    @Override
    public void changeRole(String id, String role) throws DAOException, ConnectionPoolException {

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement(UPDATE_ROLE);
            statement.setString(1, role);
            statement.setString(2, id);

            LOG.info("SUCCESS DB: Connected.");
            statement.executeUpdate();
            LOG.info("SUCCESS DB: User role changed.");

        } catch (SQLException exc) {

            LOG.log(Level.ERROR,"FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);
        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement);
        }
    }

    @Override
    public void changePaymentStatus(String status, String login) throws DAOException, ConnectionPoolException {

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;

        try {

            int id = getLastId(login);

            statement = connection.prepareStatement(UPDATE_PAYMENT);
            statement.setString(1, status);
            statement.setInt(2, id);

            LOG.info("SUCCESS DB: Connected.");
            statement.executeUpdate();
            LOG.info("SUCCESS DB: Payment status changed.");

        } catch (SQLException exc) {

            LOG.log(Level.ERROR, "FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);
        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement);
        }
    }

    public int getLastId(String login) throws DAOException, ConnectionPoolException {

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int lastId = 0;
        int userId = 0;

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        CreateTableInfoService createTableInfoService = serviceProvider.getCreateTableInfoService();

        try {
            userId = createTableInfoService.getUserId(login);
        } catch (ServiceException e) {
            LOG.log(Level.ERROR,"FAIL DB: Fail to get user id.", e);
            throw new DAOException(e);
        }

        try {

            statement = connection.prepareStatement(GET_LAST);
            statement.setInt(1, userId);

            LOG.info("SUCCESS DB: Connected.");
            resultSet = statement.executeQuery();
            LOG.info("SUCCESS DB: Last element success.");

            if (resultSet.next()){
                lastId = Integer.parseInt(resultSet.getString(COLUMN_ID_ORDER));
            }

        } catch (SQLException exc) {

            LOG.log(Level.ERROR,"FAIL DB: Fail to get last element DB.", exc);
            throw new DAOException(exc);
        }  finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }
        return lastId;
    }
}
