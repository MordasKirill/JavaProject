package net.epam.study.dao;

import net.epam.study.dao.connection.ConnectionPoolException;

public interface ChangeTableInfoDAO {
    void changeOrderStatus(String id, String status) throws DAOException, ConnectionPoolException;
    void changeRole(String id, String role) throws DAOException, ConnectionPoolException;
    void changePaymentStatus(String status, String login) throws DAOException, ConnectionPoolException;
}
