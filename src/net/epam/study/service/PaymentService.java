package net.epam.study.service;

import net.epam.study.bean.Order;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {
    int getDonePayments(int userId) throws ServiceException;

    void doPayment(int userId, int orderId, BigDecimal total, String status) throws ServiceException;

    void changeOrderStatus(String status, int id) throws ServiceException;

    List<Order> getAllOrders() throws ServiceException;
}
