package net.epam.study.dao.impl;

import net.epam.study.dao.ChangeTableInfoDAO;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.connection.ConnectionPool;
import net.epam.study.dao.connection.ConnectionPoolException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangeTableInfoImpl implements ChangeTableInfoDAO {

    public static final String UPDATE_ACCEPT = "update orders set status =";
    public static final String WHERE_ORDER_ID = "where order_id =";
    public static final String UPDATE_ROLE = "update users set role =";
    public static final String WHERE_ID_USERS = "where id =";
    public static final String UPDATE_PAYMENT = "update payment set paymentStatus =";
    public static final String COLUMN_ID_ORDER = "order_id";
    public static final String COLUMN_LOGIN = "user_login";
    public static final String GET_LAST = "SELECT order_id, user_login FROM payment WHERE user_login=";
    public static final String ORDER = " ORDER BY order_id DESC LIMIT 1";
    public static final String WHERE_ID_PAYMENT = "where order_id =";
    private static final Logger log = Logger.getLogger(ChangeTableInfoImpl.class);

    @Override
    public void changeStatus(String id, String status) throws DAOException, ConnectionPoolException {
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement(UPDATE_ACCEPT + "'" + status + "'" + WHERE_ORDER_ID + "'" + id + "'");
            log.info("SUCCESS DB: Connected.");
            statement.executeUpdate();
            log.info("SUCCESS DB: Order status changed.");

        } catch (SQLException exc) {

            log.log(Level.ERROR,"FAIL DB: Fail to write DB.", exc);
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

            statement = connection.prepareStatement(UPDATE_ROLE + "'" + role + "'" + WHERE_ID_USERS + "'" + id + "'");
            log.info("SUCCESS DB: Connected.");
            statement.executeUpdate();
            log.info("SUCCESS DB: User role changed.");

        } catch (SQLException exc) {

            log.log(Level.ERROR,"FAIL DB: Fail to write DB.", exc);
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
            System.out.println(id);
            statement = connection.prepareStatement(UPDATE_PAYMENT + "'" + status + "'" + WHERE_ID_PAYMENT + "'" + id + "'");
            log.info("SUCCESS DB: Connected.");
            statement.executeUpdate();
            log.info("SUCCESS DB: Payment status changed.");

        } catch (SQLException exc) {

            log.log(Level.ERROR, "FAIL DB: Fail to write DB.", exc);
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

        try {

            statement = connection.prepareStatement(GET_LAST+ "'" + login + "'" + ORDER);
            log.info("SUCCESS DB: Connected.");
            resultSet = statement.executeQuery();
            log.info("SUCCESS DB: Last element success.");
            if (resultSet.next()){
                if(resultSet.getString(COLUMN_LOGIN).equals(login)) {
                    lastId = Integer.parseInt(resultSet.getString(COLUMN_ID_ORDER));
                }
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
