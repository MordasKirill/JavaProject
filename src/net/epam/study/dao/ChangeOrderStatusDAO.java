package net.epam.study.dao;

import net.epam.study.dao.connection.ConnectionPoolException;

public interface ChangeOrderStatusDAO {
    void accept(String id, String status) throws DAOException, ConnectionPoolException;
}
