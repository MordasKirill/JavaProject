package net.epam.study.dao.impl;

import net.epam.study.dao.CreateTableInfoDAO;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.connection.ConnectionPool;
import net.epam.study.dao.connection.ConnectionPoolException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateTableInfoImpl implements CreateTableInfoDAO {
    public static final String COLUMN_ID_ORDER = "order_id";
    public static final String COLUMN_ID_USER = "id";
    public static final String INSERT_INTO_ORDERS = "INSERT INTO orders (fullName,address,email,phone,details,status) VALUES";
    public static final String GET_LAST_ID = "SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1";
    public static final String GET_USER_ID = "SELECT id FROM users WHERE login=";
    public static final String INSERT_INTO_PAYMENT = "INSERT INTO payment (paymentStatus,total,order_id,user_id) VALUES";
    public static final String INSERT_INTO_MENU = "INSERT INTO menu (itemName,price,waitTime,category) VALUES";

    private static final Logger LOG = Logger.getLogger(CreateTableInfoImpl.class);

    public void createOrder(String fullName, String address, String email, String phone, StringBuilder stringBuilder) throws DAOException, ConnectionPoolException {

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        String status = "processing";

        try {
            statement = connection.prepareStatement(INSERT_INTO_ORDERS + "('" +  fullName + "','" + address + "','" + email + "','" + phone + "','" + stringBuilder + "','" + status + "')");
            LOG.info("SUCCESS DB: Connected.");
            statement.executeUpdate();
            LOG.info("SUCCESS DB: Order created.");

        } catch (SQLException exc) {

            LOG.log(Level.ERROR,"FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);
        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement);
        }
    }

    public void payment(String login, BigDecimal total, String status) throws DAOException, ConnectionPoolException {

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;

        int lastId = getLastId();
        int userId = getUserId(login);
        double doubleTotal = total.doubleValue();

        try {

            statement = connection.prepareStatement(INSERT_INTO_PAYMENT + "('" + status + "','" + doubleTotal + "','" + lastId + "','" + userId + "')");
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
            statement = connection.prepareStatement(INSERT_INTO_MENU + "('" +  itemName + "','" + price + "','" + waitTime + "','" + category + "')");
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

    public int getLastId() throws DAOException, ConnectionPoolException {

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int lastId = 0;

        try {

            statement = connection.prepareStatement(GET_LAST_ID);
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

    public int getUserId(String login) throws DAOException, ConnectionPoolException {

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int userId = 0;

        try {

            statement = connection.prepareStatement(GET_USER_ID + "'" + login +"'");
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
