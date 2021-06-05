package net.epam.study.dao;

import net.epam.study.dao.connection.ConnectionPoolException;

public interface OrderCreateDAO {
    void create(String fullName, String address, String email, String phone, StringBuilder stringBuilder) throws DAOException, ConnectionPoolException;
}
