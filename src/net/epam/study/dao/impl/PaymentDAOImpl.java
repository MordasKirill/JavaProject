package net.epam.study.dao.impl;

import net.epam.study.bean.Order;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.PaymentDAO;
import net.epam.study.dao.connection.ConnectionPool;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    private static final String COLUMN_ID_ORDER = "order_id";
    private static final String SELECT_FROM_PAYMENTS = "select * from payment where user_id= ? and paymentStatus='done'";
    private static final String UPDATE_PAYMENT_STATUS = "update payment set paymentStatus = ? where order_id = ?";
    private static final String INSERT_INTO_PAYMENT = "INSERT INTO payment (paymentStatus,total,order_id,user_id) VALUES (?,?,?,?)";
    private static final String SELECT_ALL_ORDERS = "select order_id from payment where order_id>0";
    private static final Logger LOG = Logger.getLogger(PaymentDAOImpl.class);

    public void doPayment(int userId, int orderId, BigDecimal total, String status) throws DAOException {
        double doubleTotal = total.doubleValue();
        List<Object> paramList = new LinkedList<>();
        paramList.add(status);
        paramList.add(doubleTotal);
        paramList.add(orderId);
        paramList.add(userId);
        DAOProvider.getInstance().getDBCommonCRUDOperationDAO().executeUpdate(INSERT_INTO_PAYMENT, paramList);
    }

    public int getDonePayments(int userId) throws DAOException {
        int result = 0;
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
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
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }
        return result;
    }

    public List<Order> getAllOrders() throws DAOException {
        List<Order> orders = new ArrayList<>();
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SELECT_ALL_ORDERS);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getString(COLUMN_ID_ORDER));
                orders.add(order);
            }
        } catch (SQLException | RuntimeException exc) {
            LOG.log(Level.ERROR, "FAIL DB: Fail to get all orders.", exc);
            throw new DAOException(exc);
        } finally {
            ConnectionPool.connectionPool.putBack(connection);
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }
        return orders;
    }

    public void changePaymentStatus(String status, int id) throws DAOException {
        List<Object> paramList = new LinkedList<>();
        paramList.add(status);
        paramList.add(id);
        DAOProvider.getInstance().getDBCommonCRUDOperationDAO().executeUpdate(UPDATE_PAYMENT_STATUS, paramList);
    }
}
