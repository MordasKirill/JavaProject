package net.epam.study.dao;

import net.epam.study.dao.connection.ConnectionPoolException;

import java.math.BigDecimal;

public interface OrderCreateDAO {
    void create(String fullName, String address, String email, String phone, StringBuilder stringBuilder) throws DAOException, ConnectionPoolException;
    void payment(String login, BigDecimal total, String status) throws DAOException, ConnectionPoolException;
}

