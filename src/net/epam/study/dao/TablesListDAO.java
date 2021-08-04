package net.epam.study.dao;

import net.epam.study.bean.MenuItem;
import net.epam.study.bean.Order;
import net.epam.study.dao.connection.ConnectionPoolException;
import net.epam.study.bean.User;

import java.util.List;

public interface TablesListDAO {
    List<Order> getOrders(int limit) throws DAOException, ConnectionPoolException;
    List<Order> getAllOrders() throws DAOException, ConnectionPoolException;
    List<MenuItem> getMenu() throws DAOException, ConnectionPoolException;
    List<User> getUsers(int limit) throws DAOException, ConnectionPoolException;
    List<User> getAllUsers() throws DAOException, ConnectionPoolException;
    int getDonePayments(int userId) throws DAOException, ConnectionPoolException;
    int getActualLimit(int limit);
    int getPreviousLimit(int limit);
}
