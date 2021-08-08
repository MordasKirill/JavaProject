package net.epam.study.dao;

import net.epam.study.bean.Order;
import net.epam.study.dao.connection.ConnectionPoolException;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentDAO {
    void doPayment(int userId, int orderId, BigDecimal total, String status) throws DAOException, ConnectionPoolException;

    int getDonePayments(int userId) throws DAOException, ConnectionPoolException;

    void changePaymentStatus(String status, int id) throws DAOException, ConnectionPoolException;

    List<Order> getAllOrders() throws DAOException, ConnectionPoolException;
}
