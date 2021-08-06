package net.epam.study.dao;

import net.epam.study.dao.connection.ConnectionPoolException;

public interface DBCommonCRUDOperationDAO {

    void executeUpdate(String changeValue, int id, String tableName) throws DAOException, ConnectionPoolException;
}
