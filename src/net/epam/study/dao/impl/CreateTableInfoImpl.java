package net.epam.study.dao.impl;

import net.epam.study.bean.Order;
import net.epam.study.dao.CreateTableInfoDAO;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.connection.ConnectionPool;
import net.epam.study.dao.connection.ConnectionPoolException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;

public class CreateTableInfoImpl implements CreateTableInfoDAO {
    public static final String COLUMN_ID_ORDER = "order_id";
    public static final String COLUMN_ID_USER = "id";
    public static final String INSERT_INTO_ORDERS = "INSERT INTO orders (fullName,address,email,phone,details,status) VALUES (?,?,?,?,?,?)";
    public static final String GET_USER_ID = "SELECT id FROM users WHERE login= ?";
    public static final String INSERT_INTO_PAYMENT = "INSERT INTO payment (paymentStatus,total,order_id,user_id) VALUES (?,?,?,?)";
    public static final String INSERT_INTO_MENU = "INSERT INTO menu (itemName,price,waitTime,category) VALUES (?,?,?,?)";

    private static final Logger LOG = Logger.getLogger(CreateTableInfoImpl.class);

    public void createOrder(String fullName, String address, String email, String phone, StringBuilder stringBuilder) throws DAOException, ConnectionPoolException {

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        String status = "processing";

        try {
            statement = connection.prepareStatement(INSERT_INTO_ORDERS, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, fullName);
            statement.setString(2, address);
            statement.setString(3, email);
            statement.setString(4, phone);
            statement.setString(5, stringBuilder.toString());
            statement.setString(6, status);

            LOG.info("SUCCESS DB: Connected.");
            statement.execute();
            LOG.info("SUCCESS DB: Order created.");
            ResultSet resultSet = statement.getGeneratedKeys();
            int orderId;
            if (resultSet.next()){
                orderId = resultSet.getInt(1);
                net.epam.study.service.impl.CreateTableInfoImpl.order = new Order();
                net.epam.study.service.impl.CreateTableInfoImpl.order.setId(String.valueOf(orderId));
            }

        } catch (SQLException exc) {

            LOG.log(Level.ERROR,"FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);
        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement);
        }
    }

    public void doPayment(int userId, BigDecimal total, String status) throws DAOException, ConnectionPoolException {

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;

        int orderId = Integer.parseInt(net.epam.study.service.impl.CreateTableInfoImpl.order.getId());
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

            LOG.log(Level.ERROR,"FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);
        }  finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement);
        }
    }


    public void createMenuItem(String itemName, String price, String waitTime, String category) throws DAOException, ConnectionPoolException {

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(INSERT_INTO_MENU);
            statement.setString(1, itemName);
            statement.setString(2, price);
            statement.setString(3, waitTime);
            statement.setString(4, category);

            LOG.info("SUCCESS DB: Connected.");
            statement.executeUpdate();
            LOG.info("SUCCESS DB: Menu item created.");

        } catch (SQLException exc) {

            LOG.log(Level.ERROR,"FAIL DB: Fail to write DB.", exc);
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
            if (resultSet.next()){
                userId = Integer.parseInt(resultSet.getString(COLUMN_ID_USER));
            }

        } catch (SQLException exc) {

            LOG.log(Level.ERROR,"FAIL DB: Fail to get user id.", exc);
            throw new DAOException(exc);
        }  finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }
        return userId;
    }
}
