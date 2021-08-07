package net.epam.study.dao.impl;

import net.epam.study.dao.DAOException;
import net.epam.study.dao.DBCommonCRUDOperationDAO;
import net.epam.study.dao.connection.ConnectionPool;
import net.epam.study.dao.connection.ConnectionPoolException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBCommonCRUDOperationImpl implements DBCommonCRUDOperationDAO {

    private static final Logger LOG = Logger.getLogger(DBCommonCRUDOperationImpl.class);

    public void executeUpdate(String firstStatementString, int id, String sqlUpdateStatement) throws DAOException, ConnectionPoolException {
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sqlUpdateStatement);
            statement.setString(1, firstStatementString);
            statement.setInt(2, id);
            LOG.info("SUCCESS DB: Connected.");
            statement.executeUpdate();
            LOG.info("SUCCESS DB: Payment status changed.");
        } catch (SQLException exc) {
            LOG.log(Level.ERROR, "FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);
        } finally {
            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement);
        }
    }

}
