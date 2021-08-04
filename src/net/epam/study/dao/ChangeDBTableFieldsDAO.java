package net.epam.study.dao;

import net.epam.study.dao.connection.ConnectionPoolException;

public interface ChangeDBTableFieldsDAO {
    void changeOrderStatus(String id, String status) throws DAOException, ConnectionPoolException;
    void changeRole(String id, String role) throws DAOException, ConnectionPoolException;
    void changePaymentStatus(String status, int userId) throws DAOException, ConnectionPoolException;
}
