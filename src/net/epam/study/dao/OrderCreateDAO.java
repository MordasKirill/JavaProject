package net.epam.study.dao;

import net.epam.study.dao.connection.ConnectionPoolException;

public interface OrderCreateDAO {
    void create(String fullName, String address, String email, String phone, StringBuilder stringBuilder, String login, double total) throws DAOException, ConnectionPoolException;
    void payment(String login, double total, String status) throws DAOException, ConnectionPoolException;
}

