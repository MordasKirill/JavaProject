package net.epam.study.dao;

import net.epam.study.bean.Order;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentDAO {
    void doPayment(int userId, int orderId, BigDecimal total, String status) throws DAOException;

    int getDonePayments(int userId) throws DAOException;

    void changePaymentStatus(String status, int id) throws DAOException;

    List<Order> getAllPayments(int userId, int limit) throws DAOException;
}
