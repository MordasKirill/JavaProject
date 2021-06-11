package net.epam.study.dao.impl;

import net.epam.study.dao.DAOException;
import net.epam.study.dao.DeleteTableInfoDAO;
import net.epam.study.dao.connection.ConnectionPool;
import net.epam.study.dao.connection.ConnectionPoolException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteTableInfoImpl implements DeleteTableInfoDAO {
    public static final String DELETE_FROM_ORDERS = "delete from orders where order_id =";
    public static final String DELETE_FROM_USERS = "delete from users where id =";

    private static final Logger log = Logger.getLogger(DeleteTableInfoImpl.class);

    public void deleteOrder(String id) throws DAOException, ConnectionPoolException {

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement(DELETE_FROM_ORDERS + "'" + id + "'");
            log.info("SUCCESS DB: Connected.");
            statement.executeUpdate();
            log.info("SUCCESS DB: Order deleted.");

        } catch (SQLException exc) {

            log.log(Level.ERROR,"FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);
        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement);
        }
    }

    @Override
    public void deleteUser(String id) throws DAOException, ConnectionPoolException {

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement(DELETE_FROM_USERS + "'" + id + "'");
            log.info("SUCCESS DB: Connected.");
            statement.executeUpdate();
            log.info("SUCCESS DB: User deleted.");

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
