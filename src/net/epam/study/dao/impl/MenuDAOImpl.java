package net.epam.study.dao.impl;

import net.epam.study.bean.MenuItem;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.MenuDAO;
import net.epam.study.dao.connection.ConnectionPool;
import net.epam.study.dao.connection.ConnectionPoolException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuDAOImpl implements MenuDAO {

    public static final String COLUMN_NAME = "itemName";
    public static final String COLUMN_ITEM_NAME = "itemName";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_WAIT_TIME = "waitTime";
    public static final String COLUMN_CATEGORY = "category";
    public static final String SELECT_ALL_FROM_MENU = "select * from menu where itemName= ? and category= ?";
    public static final String SELECT_FROM_MENU = "select itemName, price, waitTime, category from menu";
    public static final String DELETE_FROM_MENU = "delete from menu where itemName= ? and category= ?";
    public static final String INSERT_INTO_MENU = "INSERT INTO menu (itemName,price,waitTime,category) VALUES (?,?,?,?)";

    private static final Logger LOG = Logger.getLogger(MenuDAOImpl.class);

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

            LOG.log(Level.ERROR, "FAIL DB: Fail to write DB.", exc);
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

            statement = connection.prepareStatement(SELECT_ALL_FROM_MENU);
            statement.setString(1, itemName);
            statement.setString(2, category);

            LOG.info("SUCCESS DB: Connected.");
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getString(COLUMN_NAME).equals(itemName)) {
                    result = true;
                    LOG.info("SUCCESS DB: Menu item checked.");
                }
            }


        } catch (SQLException exc) {

            LOG.log(Level.ERROR, "FAIL DB: Fail to check MenuItem.", exc);
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

            statement = connection.prepareStatement(DELETE_FROM_MENU);
            statement.setString(1, itemName);
            statement.setString(2, category);

            LOG.info("SUCCESS DB: Connected.");
            statement.executeUpdate();
            LOG.info("SUCCESS DB: Menu item deleted.");

        } catch (SQLException exc) {

            LOG.log(Level.ERROR, "FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);
        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement);
        }
    }

    public List<MenuItem> getMenu() throws DAOException, ConnectionPoolException {

        List<MenuItem> menuItems = new ArrayList<>();
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            LOG.info("SUCCESS DB: Connected.");
            statement = connection.prepareStatement(SELECT_FROM_MENU);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                MenuItem menuItem = new MenuItem();
                menuItem.setName(resultSet.getString(COLUMN_ITEM_NAME));
                menuItem.setPrice(resultSet.getString(COLUMN_PRICE));
                menuItem.setFilingTime(resultSet.getString(COLUMN_WAIT_TIME));
                menuItem.setCategory(resultSet.getString(COLUMN_CATEGORY));
                menuItems.add(menuItem);
            }

        } catch (SQLException exc) {

            LOG.log(Level.ERROR, "FAIL DB: Fail to show menu.", exc);
            throw new DAOException(exc);
        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }

        return menuItems;
    }
}
