package net.epam.study.dao.impl;

import net.epam.study.dao.DAOException;
import net.epam.study.dao.TablesListDAO;
import net.epam.study.dao.connection.ConnectionPool;
import net.epam.study.entity.MenuItem;
import net.epam.study.entity.Order;
import net.epam.study.dao.connection.ConnectionPoolException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TablesListImpl implements TablesListDAO {

    public static final String COLUMN_ID = "order_id";
    public static final String COLUMN_FULL_NAME = "fullName";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_DETAILS = "details";
    public static final String SELECT_FROM_ORDERS = "select order_id, fullName, address, email, phone, details from orders where order_id>0 LIMIT ";
    public static final String COLUMN_ITEM_NAME = "itemName";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_WAIT_TIME = "waitTime";
    public static final String COLUMN_CATEGORY = "category";
    public static final String SELECT_FROM_MENU = "select itemName, price, waitTime, category from menu";
    private static final Logger log = Logger.getLogger(TablesListImpl.class);

    public List<Order> getOrders(int limit) throws DAOException, ConnectionPoolException {

        List<Order> orders = new ArrayList<>();
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            log.info("SUCCESS DB: Connected.");
            statement = connection.prepareStatement(SELECT_FROM_ORDERS + limit);
            resultSet = statement.executeQuery();

            while (resultSet.next()){

                Order order = new Order();
                order.setId(resultSet.getString(COLUMN_ID));
                order.setFullName(resultSet.getString(COLUMN_FULL_NAME));
                order.setAddress(resultSet.getString(COLUMN_ADDRESS));
                order.setEmail(resultSet.getString(COLUMN_EMAIL));
                order.setPhone(resultSet.getString(COLUMN_PHONE));
                order.setDetails(resultSet.getString(COLUMN_DETAILS));
                orders.add(order);
            }

        } catch (SQLException exc) {

            log.log(Level.ERROR,"FAIL DB: Fail to show orders.", exc);
            throw new DAOException(exc);
        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }

        return orders;
    }

    public List<MenuItem> getMenu() throws DAOException, ConnectionPoolException {

        List<MenuItem> menuItems = new ArrayList<>();
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            log.info("SUCCESS DB: Connected.");
            statement = connection.prepareStatement(SELECT_FROM_MENU);
            resultSet = statement.executeQuery();

            while (resultSet.next()){

                MenuItem menuItem = new MenuItem();
                menuItem.setName(resultSet.getString(COLUMN_ITEM_NAME));
                menuItem.setPrice(resultSet.getString(COLUMN_PRICE));
                menuItem.setFilingTime(resultSet.getString(COLUMN_WAIT_TIME));
                menuItem.setCategory(resultSet.getString(COLUMN_CATEGORY));
                menuItems.add(menuItem);
            }

        } catch (SQLException exc) {

            log.log(Level.ERROR,"FAIL DB: Fail to show menu.", exc);
            throw new DAOException(exc);
        }finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }

        return menuItems;
    }
}
