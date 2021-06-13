package net.epam.study.dao.impl;

import net.epam.study.dao.DAOException;
import net.epam.study.dao.OrderCreateDAO;
import net.epam.study.dao.connection.ConnectionPool;
import net.epam.study.dao.connection.ConnectionPoolException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderCreateImpl implements OrderCreateDAO {
    public static final String COLUMN_ID_ORDER = "order_id";
    public static final String INSERT_INTO_ORDERS = "INSERT INTO orders (fullName,address,email,phone,details,status) VALUES";
    public static final String GET_LAST = "SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1";
    public static final String INSERT_INTO_PAYMENT = "INSERT INTO payment (user_login,paymentStatus,total,order_id) VALUES";

    private static final Logger log = Logger.getLogger(OrderCreateImpl.class);

    public void create(String fullName, String address, String email, String phone, StringBuilder stringBuilder, String login, double total) throws DAOException, ConnectionPoolException {

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        String status = "processing";

        try {
            statement = connection.prepareStatement(INSERT_INTO_ORDERS + "('" +  fullName + "','" + address + "','" + email + "','" + phone + "','" + stringBuilder + "','" + status + "')");
            log.info("SUCCESS DB: Connected.");
            statement.executeUpdate();
            log.info("SUCCESS DB: Order created.");

        } catch (SQLException exc) {

            log.log(Level.ERROR,"FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);
        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement);
        }
    }

    public void payment(String login, double total, String status) throws DAOException, ConnectionPoolException {

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        int lastId = getLastId();

        try {

            statement = connection.prepareStatement(INSERT_INTO_PAYMENT + "('" + login + "','" + status + "','" + total + "','" + lastId + "')");
            log.info("SUCCESS DB: Connected.");
            statement.executeUpdate();
            log.info("SUCCESS DB: Payment created.");

        } catch (SQLException exc) {

            log.log(Level.ERROR,"FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);
        }  finally {

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

            statement = connection.prepareStatement(GET_LAST);
            log.info("SUCCESS DB: Connected.");
            resultSet = statement.executeQuery();
            log.info("SUCCESS DB: Last element success.");
            if (resultSet.next()){
                lastId = Integer.parseInt(resultSet.getString(COLUMN_ID_ORDER));
            }

        } catch (SQLException exc) {

            log.log(Level.ERROR,"FAIL DB: Fail to get last element DB.", exc);
            throw new DAOException(exc);
        }  finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }
        return lastId;
    }
}
