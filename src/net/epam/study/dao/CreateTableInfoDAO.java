package net.epam.study.dao;

import net.epam.study.dao.connection.ConnectionPoolException;

import java.math.BigDecimal;

public interface CreateTableInfoDAO {
    void createOrder(String fullName, String address, String email, String phone, StringBuilder stringBuilder) throws DAOException, ConnectionPoolException;
    void doPayment(int userId, BigDecimal total, String status) throws DAOException, ConnectionPoolException;
    int getUserId(String login) throws DAOException, ConnectionPoolException;
    void createMenuItem(String itemName, String price, String waitTime, String category) throws DAOException, ConnectionPoolException;
}

