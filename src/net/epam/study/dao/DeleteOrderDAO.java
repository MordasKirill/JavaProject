package net.epam.study.dao;

import net.epam.study.dao.connection.ConnectionPoolException;

public interface DeleteOrderDAO {
    void delete(String id) throws DAOException, ConnectionPoolException;
}
