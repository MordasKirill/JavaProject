package net.epam.study.dao.impl;

import net.epam.study.dao.DAOException;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.PaymentDAO;
import net.epam.study.dao.connection.ConnectionPool;
import net.epam.study.dao.connection.ConnectionPoolException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentDAOImpl implements PaymentDAO {

    public static final String SELECT_FROM_PAYMENTS = "select * from payment where user_id= ? and paymentStatus='done'";
    public static final String UPDATE_PAYMENT_STATUS = "update payment set paymentStatus = ? where order_id = ?";
    public static final String INSERT_INTO_PAYMENT = "INSERT INTO payment (paymentStatus,total,order_id,user_id) VALUES (?,?,?,?)";
    private static final Logger LOG = Logger.getLogger(PaymentDAOImpl.class);

    public void doPayment(int userId, int orderId, BigDecimal total, String status) throws DAOException, ConnectionPoolException {

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        double doubleTotal = total.doubleValue();
        try {
            statement = connection.prepareStatement(INSERT_INTO_PAYMENT);
            statement.setString(1, status);
            statement.setDouble(2, doubleTotal);
            statement.setInt(3, orderId);
            statement.setInt(4, userId);

            LOG.info("SUCCESS DB: Connected.");
            statement.executeUpdate();
            LOG.info("SUCCESS DB: Payment created.");

        } catch (SQLException exc) {

            LOG.log(Level.ERROR, "FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);
        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement);
        }
    }

    public int getDonePayments(int userId) throws DAOException {
        int result = 0;
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {

            LOG.info("SUCCESS DB: Connected.");
            statement = connection.prepareStatement(SELECT_FROM_PAYMENTS);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                result++;

            }

        } catch (SQLException exc) {

            LOG.log(Level.ERROR, "FAIL DB: Fail to get all orders.", exc);
            throw new DAOException(exc);
        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }

        return result;
    }

    public void changePaymentStatus(String status, int id) throws DAOException {
        DAOProvider.getInstance().getDBCommonCRUDOperationDAO().executeUpdate(status, id, UPDATE_PAYMENT_STATUS);
    }
}
