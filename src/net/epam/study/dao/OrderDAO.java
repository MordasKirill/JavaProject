package net.epam.study.dao;

import net.epam.study.bean.Order;
import net.epam.study.dao.connection.ConnectionPoolException;

import java.util.List;

public interface OrderDAO {

    List<Order> getOrders(int limit) throws DAOException, ConnectionPoolException;

    List<Order> getAllOrders() throws DAOException, ConnectionPoolException;

    void deleteOrder(String id) throws DAOException, ConnectionPoolException;

    int createOrder(Order order) throws DAOException, ConnectionPoolException;

    void changeOrderStatus(String status, int id) throws DAOException;
}
