package net.epam.study.service;

import net.epam.study.bean.MenuItem;
import net.epam.study.bean.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    void deleteOrderItem(String item) throws ServiceException;

    void addToOrder(MenuItem menuItem);

    BigDecimal getTotal(int userId) throws ServiceException;

    int getDiscount(int userId) throws ServiceException;

    StringBuilder getOrder();

    void deleteOrder(String id) throws ServiceException;

    List<Order> getOrders(int limit) throws ServiceException;

    int createOrder(String fullName, String address, String email, String phone, String details) throws ServiceException;

    void changeOrderStatus(String status, int id) throws ServiceException;
}
