package net.epam.study.dao.impl;

import net.epam.study.dao.DAOException;
import net.epam.study.dao.TablesListDAO;
import net.epam.study.dao.connection.ConnectionPool;
import net.epam.study.entity.MenuItem;
import net.epam.study.entity.Order;
import net.epam.study.dao.connection.ConnectionPoolException;
import net.epam.study.entity.User;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TablesListImpl implements TablesListDAO {

    public static final String COLUMN_ID_ORDER = "order_id";
    public static final String COLUMN_FULL_NAME = "fullName";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_DETAILS = "details";
    public static final String SELECT_ALL_ORDERS = "select order_id from payment where order_id>0";
    public static final String SELECT_FROM_ORDERS_PAYMENTS = "select * from orders, payment where Orders.order_id = Payment.order_id LIMIT ";
    public static final String COLUMN_ITEM_NAME = "itemName";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_WAIT_TIME = "waitTime";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_PAYMENT_STATUS = "paymentStatus";
    public static final String SELECT_FROM_MENU = "select itemName, price, waitTime, category from menu";
    public static final String COLUMN_LOGIN = "login";
    public static final String COLUMN_ROLE = "role";
    public static final String COLUMN_ID_USER = "id";
    public static final String SELECT_ALL_USERS = "select id from users where id>0";
    public static final String SELECT_FROM_USERS = "select id, login, role from users where id>0 LIMIT ";
    public static final String SELECT_FROM_PAYMENTS = "select * from payment where user_login=";
    public static final String COLUMN_LOGIN_PAYMENT = "user_login";
    private static final Logger log = Logger.getLogger(TablesListImpl.class);

    public List<Order> getOrders(int limit) throws DAOException, ConnectionPoolException {

        List<Order> orders = new ArrayList<>();
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            log.info("SUCCESS DB: Connected.");
            statement = connection.prepareStatement(SELECT_FROM_ORDERS_PAYMENTS + limit+"," + net.epam.study.service.impl.TablesListImpl.DEFAULT_LIMIT);
            resultSet = statement.executeQuery();

            while (resultSet.next()){

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

    public List<Order> getAllOrders() throws DAOException, ConnectionPoolException {

        List<Order> orders = new ArrayList<>();
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            log.info("SUCCESS DB: Connected.");
            statement = connection.prepareStatement(SELECT_ALL_ORDERS);
            resultSet = statement.executeQuery();

            while (resultSet.next()){

                Order order = new Order();
                order.setId(resultSet.getString(COLUMN_ID_ORDER));
                orders.add(order);
            }

        } catch (SQLException exc) {

            log.log(Level.ERROR,"FAIL DB: Fail to get all orders.", exc);
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


    @Override
    public List<User> getUsers(int limit) throws DAOException, ConnectionPoolException {

        List<User> users = new ArrayList<>();
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            log.info("SUCCESS DB: Connected.");
            statement = connection.prepareStatement(SELECT_FROM_USERS + limit+"," + net.epam.study.service.impl.TablesListImpl.DEFAULT_LIMIT);
            resultSet = statement.executeQuery();

            while (resultSet.next()){

                User user = new User();
                user.setId(resultSet.getString(COLUMN_ID_USER));
                user.setLogin(resultSet.getString(COLUMN_LOGIN));
                user.setRole(resultSet.getString(COLUMN_ROLE));
                users.add(user);
            }

        } catch (SQLException exc) {

            log.log(Level.ERROR,"FAIL DB: Fail to show users.", exc);
            throw new DAOException(exc);
        }finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }

        return users;
    }

    public List<User> getAllUsers() throws DAOException, ConnectionPoolException {

        List<User> users = new ArrayList<>();
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            log.info("SUCCESS DB: Connected.");
            statement = connection.prepareStatement(SELECT_ALL_USERS);
            resultSet = statement.executeQuery();

            while (resultSet.next()){

                User user = new User();
                user.setId(resultSet.getString(COLUMN_ID_USER));
                users.add(user);
            }

        } catch (SQLException exc) {

            log.log(Level.ERROR,"FAIL DB: Fail to get all orders.", exc);
            throw new DAOException(exc);
        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }

        return users;
    }

    public int getDonePayments(String login) throws DAOException, ConnectionPoolException {

        int result = 0;
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            log.info("SUCCESS DB: Connected.");
            statement = connection.prepareStatement(SELECT_FROM_PAYMENTS +  "'" + login + "'" +("and paymentStatus='done'"));
            resultSet = statement.executeQuery();

            while (resultSet.next()){

                if (resultSet.getString(COLUMN_LOGIN_PAYMENT).equals(login)){
                    result ++;
                }
            }

        } catch (SQLException exc) {

            log.log(Level.ERROR,"FAIL DB: Fail to get all orders.", exc);
            throw new DAOException(exc);
        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement, resultSet);
        }

        return result;
    }


    @Override
    public int getActualLimit(int limit){

        return limit + net.epam.study.service.impl.TablesListImpl.DEFAULT_LIMIT;
    }


    @Override
    public int getPreviousLimit(int limit){

        return limit - net.epam.study.service.impl.TablesListImpl.DEFAULT_LIMIT;
    }
}
