package net.epam.study.dao.impl;

import net.epam.study.Constants;
import net.epam.study.bean.Order;
import net.epam.study.controller.command.Status;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.OrderDAO;
import net.epam.study.dao.connection.ConnectionPool;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * implementation of ChangeOrderDAO
 */
public class OrderDAOImpl implements OrderDAO {

    private static final String COLUMN_ID_ORDER = "order_id";
    private static final String COLUMN_FULL_NAME = "fullName";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_DETAILS = "details";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_PAYMENT_STATUS = "paymentStatus";
    private static final String DELETE_FROM_ORDERS = "delete from orders where order_id = ?";
    private static final String INSERT_INTO_ORDERS = "insert into orders (fullName,address,email,phone,details,status) values (?,?,?,?,?,?)";
    private static final String SELECT_FROM_ORDERS_PAYMENTS = "select * from orders, payment where Orders.order_id = Payment.order_id LIMIT ?,?";
    private static final String UPDATE_ORDER_STATUS = "update orders set status = ? where order_id = ?";

    private static final Logger LOG = Logger.getLogger(OrderDAOImpl.class);

    public void deleteOrder(String id) throws DAOException {
        List<Object> paramList = new LinkedList<>();
        paramList.add(id);
        DAOProvider.getInstance().getDBCommonCRUDOperationDAO().executeUpdate(DELETE_FROM_ORDERS, paramList);
    }

    public List<Order> getOrders(int limit) throws DAOException {
        List<Order> orders = new ArrayList<>();
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SELECT_FROM_ORDERS_PAYMENTS);
            statement.setInt(1, limit);
            statement.setInt(2, Constants.DEFAULT_LIMIT);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getString(COLUMN_ID_ORDER));
                order.setFullName(resultSet.getString(COLUMN_FULL_NAME));
                order.setAddress(resultSet.getString(COLUMN_ADDRESS));
                order.setEmail(resultSet.getString(COLUMN_EMAIL));
                order.setPhone(resultSet.getString(COLUMN_PHONE));
                order.setDetails(resultSet.getString(COLUMN_DETAILS));
                order.setStatus(resultSet.getString(COLUMN_STATUS));
                order.setPaymentStatus(resultSet.getString(COLUMN_PAYMENT_STATUS));
                orders.add(order);
            }
        } catch (SQLException | RuntimeException exc) {
            LOG.log(Level.ERROR, "FAIL DB: Fail to retrieve records from DB.", exc);
            throw new DAOException(exc);
        } finally {
            ConnectionPool.connectionPool.putBack(connection);
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }
        return orders;
    }

    public int createOrder(Order order) throws DAOException {
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        String status = Status.PROCESSING.toString().toLowerCase();
        try {
            statement = connection.prepareStatement(INSERT_INTO_ORDERS, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, order.getFullName());
            statement.setString(2, order.getAddress());
            statement.setString(3, order.getEmail());
            statement.setString(4, order.getPhone());
            statement.setString(5, order.getDetails());
            statement.setString(6, status);
            statement.execute();
            LOG.info("SUCCESS DB: Order created.");
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            throw new DAOException("Order not created! " + order.toString());
        } catch (SQLException exc) {

            LOG.log(Level.ERROR, "FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);
        } finally {
            ConnectionPool.connectionPool.putBack(connection);
            ConnectionPool.connectionPool.closeConnection(statement);
        }
    }

    public void changeOrderStatus(String status, int id) throws DAOException {
        List<Object> paramList = new LinkedList<>();
        paramList.add(status);
        paramList.add(id);
        DAOProvider.getInstance().getDBCommonCRUDOperationDAO().executeUpdate(UPDATE_ORDER_STATUS, paramList);
    }
}
