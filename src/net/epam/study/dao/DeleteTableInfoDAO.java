package net.epam.study.dao;

import net.epam.study.dao.connection.ConnectionPoolException;

public interface DeleteTableInfoDAO {
    void deleteOrder(String id) throws DAOException, ConnectionPoolException;
    void deleteUser(String id) throws DAOException, ConnectionPoolException;
}
