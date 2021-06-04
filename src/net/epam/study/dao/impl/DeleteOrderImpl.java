package net.epam.study.dao.impl;

import net.epam.study.dao.DAOException;
import net.epam.study.dao.DeleteOrderDAO;
import net.epam.study.dao.connection.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteOrderImpl implements DeleteOrderDAO {
    public static final String deleteFrom = "delete from orders where order_id =";
    private static final Logger log = Logger.getLogger(DeleteOrderImpl.class);

    public void delete(String id) throws DAOException {
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement(deleteFrom + "'" + id + "'");
            log.debug("SUCCESS DB: Connected.");
            statement.executeUpdate();
            log.debug("SUCCESS DB: Order deleted.");

        } catch (SQLException exc) {
            exc.printStackTrace();
            log.debug("FAIL DB: Fail to write DB.");
            throw new DAOException(exc);
        } finally {
            ConnectionPool.connectionPool.putBack(connection);
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }
}

