package net.epam.study.dao.impl;

import net.epam.study.dao.DAOException;
import net.epam.study.dao.DeleteTableInfoDAO;
import net.epam.study.dao.connection.ConnectionPool;
import net.epam.study.dao.connection.ConnectionPoolException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteTableInfoImpl implements DeleteTableInfoDAO {
    public static final String COLUMN_NAME = "itemName";
    public static final String DELETE_FROM_ORDERS = "delete from orders where order_id =";
    public static final String DELETE_FROM_USERS = "delete from users where id =";
    public static final String DELETE_FROM_MENU = "delete from menu where itemName =";
    public static final String SELECT_FROM_MENU = "select * from menu where itemName=";

    private static final Logger LOG = Logger.getLogger(DeleteTableInfoImpl.class);

    public void deleteOrder(String id) throws DAOException, ConnectionPoolException {

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement(DELETE_FROM_ORDERS + "'" + id + "'");
            LOG.info("SUCCESS DB: Connected.");
            statement.executeUpdate();
            LOG.info("SUCCESS DB: Order deleted.");

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
    public void deleteUser(String id) throws DAOException, ConnectionPoolException {

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement(DELETE_FROM_USERS + "'" + id + "'");
            LOG.info("SUCCESS DB: Connected.");
            statement.executeUpdate();
            LOG.info("SUCCESS DB: User deleted.");

        } catch (SQLException exc) {

            LOG.log(Level.ERROR,"FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);
        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement);
        }
    }

    public boolean isMenuItemExists(String itemName, String category) throws DAOException, ConnectionPoolException {

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean result = false;

        try {

            statement = connection.prepareStatement(SELECT_FROM_MENU + "'" + itemName + "'" +(" and category=") + "'" + category + "'");
            LOG.info("SUCCESS DB: Connected.");
            resultSet = statement.executeQuery();

            if (resultSet.next()){
                if (resultSet.getString(COLUMN_NAME).equals(itemName)) {
                    result = true;
                    LOG.info("SUCCESS DB: Menu item checked.");
                }
            }


        } catch (SQLException exc) {

            LOG.log(Level.ERROR,"FAIL DB: Fail to check MenuItem.", exc);
            throw new DAOException(exc);
        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }
        return result;
    }

    public void deleteMenuItem(String itemName, String category) throws DAOException, ConnectionPoolException {

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement(DELETE_FROM_MENU + "'" + itemName + "'" +(" and category=") + "'" + category + "'");
            LOG.info("SUCCESS DB: Connected.");
            statement.executeUpdate();
            LOG.info("SUCCESS DB: Menu item deleted.");

        } catch (SQLException exc) {

            LOG.log(Level.ERROR,"FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);
        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement);
        }
    }
}

