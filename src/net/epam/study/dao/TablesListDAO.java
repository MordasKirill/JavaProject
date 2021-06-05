package net.epam.study.dao;

import net.epam.study.entity.MenuItem;
import net.epam.study.entity.Order;
import net.epam.study.dao.connection.ConnectionPoolException;

import java.util.List;

public interface TablesListDAO {
    List<Order> getOrders(int limit) throws DAOException, ConnectionPoolException;
    List<MenuItem> getMenu() throws DAOException, ConnectionPoolException;
}
