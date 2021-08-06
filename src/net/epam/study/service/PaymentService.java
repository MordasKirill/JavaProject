package net.epam.study.service;

import java.math.BigDecimal;

public interface PaymentService {
    int getDonePayments(int userId) throws ServiceException;

    void doPayment(int userId, int orderId, BigDecimal total, String status) throws ServiceException;

    void changeOrderStatus(String status, int id) throws ServiceException;
}
