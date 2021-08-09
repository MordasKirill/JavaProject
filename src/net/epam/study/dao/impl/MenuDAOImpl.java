package net.epam.study.dao.impl;

import net.epam.study.bean.MenuItem;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.MenuDAO;
import net.epam.study.dao.connection.ConnectionPool;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuDAOImpl implements MenuDAO {
    private static final String COLUMN_ITEM_NAME = "itemName";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_WAIT_TIME = "waitTime";
    private static final String COLUMN_CATEGORY = "category";
    private static final String SELECT_ALL_FROM_MENU = "select * from menu where itemName= ? and category= ?";
    private static final String SELECT_FROM_MENU = "select itemName, price, waitTime, category from menu";
    private static final String DELETE_FROM_MENU = "delete from menu where itemName= ? and category= ?";
    private static final String INSERT_INTO_MENU = "INSERT INTO menu (itemName,price,waitTime,category) VALUES (?,?,?,?)";

    private static final Logger LOG = Logger.getLogger(MenuDAOImpl.class);

    public void createMenuItem(String itemName, String price, String waitTime, String category) throws DAOException {
        List<Object> paramList = new ArrayList<>();
        paramList.add(itemName);
        paramList.add(price);
        paramList.add(waitTime);
        paramList.add(category);
        DAOProvider.getInstance().getDBCommonCRUDOperationDAO().executeUpdate(INSERT_INTO_MENU, paramList);
    }

    public boolean isMenuItemExists(String itemName, String category) throws DAOException {
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean result = false;
        try {
            statement = connection.prepareStatement(SELECT_ALL_FROM_MENU);
            statement.setString(1, itemName);
            statement.setString(2, category);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = true;
                LOG.info("SUCCESS DB: Menu item checked.");
            }
        } catch (SQLException exc) {
            LOG.log(Level.ERROR, "FAIL DB: Fail to check MenuItem.", exc);
            throw new DAOException(exc);
        }  finally{
            ConnectionPool.connectionPool.putBack(connection);
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }
        return result;
    }

    public void deleteMenuItem(String itemName, String category) throws DAOException {
        List<Object> paramList = new ArrayList<>();
        paramList.add(itemName);
        paramList.add(category);
        DAOProvider.getInstance().getDBCommonCRUDOperationDAO().executeUpdate(DELETE_FROM_MENU, paramList);
    }

    public List<MenuItem> getMenu() throws DAOException {
        List<MenuItem> menuItems = new ArrayList<>();
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
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
        } catch (SQLException | RuntimeException exc) {
            LOG.log(Level.ERROR, "FAIL DB: Fail to show menu.", exc);
            throw new DAOException(exc);
        } finally {
            ConnectionPool.connectionPool.putBack(connection);
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }
        return menuItems;
    }
}
