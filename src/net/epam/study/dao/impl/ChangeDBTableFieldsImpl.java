package net.epam.study.dao.impl;

import net.epam.study.dao.ChangeTableInfoDAO;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.connection.ConnectionPool;
import net.epam.study.dao.connection.ConnectionPoolException;
import net.epam.study.service.impl.CreateTableInfoImpl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChangeDBTableFieldsImpl implements ChangeTableInfoDAO {

    public static final String UPDATE_ACCEPT = "update orders set status = ? where order_id = ?";
    public static final String UPDATE_ROLE = "update users set role = ? where id = ?";
    public static final String UPDATE_PAYMENT = "update payment set paymentStatus = ? where order_id = ?";
    public static final String COLUMN_ID_ORDER = "order_id";
    public static final String GET_LAST = "SELECT order_id, user_id FROM payment WHERE user_id= ? ORDER BY order_id DESC LIMIT 1";
    private static final Logger LOG = Logger.getLogger(ChangeDBTableFieldsImpl.class);

    @Override
    public void changeOrderStatus(String id, String status) throws DAOException, ConnectionPoolException {
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

            int id = Integer.parseInt(CreateTableInfoImpl.order.getId());

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

}
