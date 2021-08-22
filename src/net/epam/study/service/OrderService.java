package net.epam.study.service;

import net.epam.study.bean.MenuItem;
import net.epam.study.bean.Order;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface OrderService {
    void deleteOrderItem(int userId, String itemName);

    void addToOrder(MenuItem menuItem, int userId);

    BigDecimal getTotal(int userId);

    BigDecimal applyDiscount(BigDecimal totalPrice, int userId) throws ServiceException;

    int getDiscount(int userId) throws ServiceException;

    String orderToString(Map<Integer, LinkedList<MenuItem>> order, int userId);

    void deleteOrder(int id) throws ServiceException;

    List<Order> getOrderDetailsWithLimit(int limit) throws ServiceException;

    List<Order> getOrderDetails(int userId) throws ServiceException;

    List<Order> getAllOrders() throws ServiceException;

    int createOrder(Order oder) throws ServiceException;

    void changeOrderStatus(String status, int id) throws ServiceException;
}
