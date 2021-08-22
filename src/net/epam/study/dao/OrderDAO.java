package net.epam.study.dao;

import net.epam.study.bean.Order;

import java.util.List;

public interface OrderDAO {

    List<Order> getOrderDetailsWithLimit(int limit) throws DAOException;

    List<Order> getOrderDetails(int userId) throws DAOException;

    List<Order> getAllOrders() throws DAOException;

    void deleteOrder(int id) throws DAOException;

    int createOrder(Order order) throws DAOException;

    void changeOrderStatus(String status, int id) throws DAOException;
}
