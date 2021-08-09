package net.epam.study.dao;

import java.util.List;

public interface DBCommonCRUDOperationDAO {

    void executeUpdate(String sqlUpdateStatement, List<Object> params) throws DAOException;
}
