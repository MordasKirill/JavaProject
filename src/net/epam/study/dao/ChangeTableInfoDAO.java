package net.epam.study.dao;

import net.epam.study.dao.connection.ConnectionPoolException;

public interface ChangeTableInfoDAO {
    void changeStatus(String id, String status) throws DAOException, ConnectionPoolException;
    void changeRole(String id, String role) throws DAOException, ConnectionPoolException;
    void changePaymentStatus(String status) throws DAOException, ConnectionPoolException;
}
