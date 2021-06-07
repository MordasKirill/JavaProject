package net.epam.study.dao.impl;

import net.epam.study.dao.ChangeOrderStatusDAO;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.connection.ConnectionPool;
import net.epam.study.dao.connection.ConnectionPoolException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChangeOrderStatusImpl implements ChangeOrderStatusDAO {

    public static final String UPDATE_ACCEPT = "update orders set status =";
    public static final String WHERE = "where order_id =";
    private static final Logger log = Logger.getLogger(ChangeOrderStatusImpl.class);

    @Override
    public void accept(String id, String status) throws DAOException, ConnectionPoolException {
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement(UPDATE_ACCEPT + "'" + status + "'" + WHERE + "'" + id + "'");
            log.info("SUCCESS DB: Connected.");
            statement.executeUpdate();
            log.info("SUCCESS DB: Order status changed.");

        } catch (SQLException exc) {

            log.log(Level.ERROR,"FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);
        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement);
        }
    }
}
