package net.epam.study.dao.impl;

import net.epam.study.dao.DAOException;
import net.epam.study.dao.OrderCreateDAO;
import net.epam.study.dao.connection.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderCreateImpl implements OrderCreateDAO {
    public static final String insertInto = "INSERT INTO orders (fullName,address,email,phone,details) VALUES";
    private static final Logger log = Logger.getLogger(OrderCreateImpl.class);

    public void create(String fullName, String address, String email, String phone, StringBuilder stringBuilder) throws DAOException {
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(insertInto + "('" + fullName + "','" + address + "','" + email + "','" + phone + "','" + stringBuilder + "')");
            log.debug("SUCCESS DB: Connected.");
            statement.executeUpdate();
            log.debug("SUCCESS DB: Order created.");

        } catch (SQLException exc) {
            log.debug("FAIL DB: Fail to write DB.");
            throw new DAOException(exc);
        } finally {
            ConnectionPool.connectionPool.putBack(connection);
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DAOException(e);
            }
        }
    }
}
