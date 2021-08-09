package net.epam.study.service;

import net.epam.study.bean.MenuItem;
import net.epam.study.bean.Order;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface OrderService {
    void deleteOrderItem(String item, int userId, String itemName, String itemPrice) throws ServiceException;

    void addToOrder(MenuItem menuItem, int userId);

    BigDecimal getTotal(int userId) throws ServiceException;

    int getDiscount(int userId) throws ServiceException;

    String orderToString(Map<Integer, LinkedList<MenuItem>> order, int userId);

    void deleteOrder(String id) throws ServiceException;

    List<Order> getOrders(int limit) throws ServiceException;

    int createOrder(String fullName, String address, String email, String phone, String details) throws ServiceException;

    void changeOrderStatus(String status, int id) throws ServiceException;
}
