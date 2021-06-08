package net.epam.study.dao.impl;

import net.epam.study.dao.DAOException;
import net.epam.study.dao.OrderCreateDAO;
import net.epam.study.dao.connection.ConnectionPool;
import net.epam.study.dao.connection.ConnectionPoolException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderCreateImpl implements OrderCreateDAO {
    public static final String INSERT_INTO = "INSERT INTO orders (fullName,address,email,phone,details,status) VALUES";
    private static final Logger log = Logger.getLogger(OrderCreateImpl.class);

    public void create(String fullName, String address, String email, String phone, StringBuilder stringBuilder) throws DAOException, ConnectionPoolException {

        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;
        String status = "processing";

        try {
            statement = connection.prepareStatement(INSERT_INTO + "('" + fullName + "','" + address + "','" + email + "','" + phone + "','" + stringBuilder + "','" + status + "')");
            log.info("SUCCESS DB: Connected.");
            statement.executeUpdate();
            log.info("SUCCESS DB: Order created.");

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
